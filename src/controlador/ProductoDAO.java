/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.Producto;

/**
 *
 * @author admin
 */
public class ProductoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public void llenarcomboProductoCF(JComboBox comboProductoCF) {
        //sirve para llenar el combobox de productos en factura de consumidor final 

        try {
            String sql = "call consultarProductosCF(); ";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            comboProductoCF.addItem(" ");
            while (rs.next()) {
                comboProductoCF.addItem(rs.getString("nombre"));
            }
  con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "se ha producido un error ", e.toString(), JOptionPane.ERROR);
        }

    }

    public String[] verElProducto(String nombre) {
        //busca el producto segun el item seleccionado en el combobox 
        String producto[] = new String[1];
        String sql = "SELECT * FROM `producto` WHERE nombre = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {

                producto[0] = rs.getString("idproducto");

            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
    public String[] todoElproductoporCodigo(String idproducto) {
        //busca el producto segun el item seleccionado en el combobox 
        String producto[] = new String[10];
        String sql = "SELECT * FROM `producto` WHERE idproducto = ? and papelera = false ; ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idproducto);
            rs = ps.executeQuery();
            while (rs.next()) {

                producto[0] = rs.getString("idproducto");
                producto[1] = rs.getString("nombre");
                producto[2] = rs.getString("precioCaja");
                producto[3] = rs.getString("precioUnidad");
                producto[4] = rs.getString("precioDocena");
                producto[5] = rs.getString("precioProm");
                producto[6] = rs.getString("cantCaja");
                producto[7] = rs.getString("cantUnidad");
                producto[8] = rs.getString("cantDocena");
                producto[9] = rs.getString("cantProm");
             

            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
       public String[] verElProductoCodigo(String codigo) {
        //busca el producto segun el codigo en el txtbucar 
           
        String producto[] = new String[1];
        String sql = "SELECT nombre FROM `producto` WHERE idproducto = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {

                producto[0] = rs.getString("nombre");

            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }

    public List buscarProductosSimilares(String nombre) {
        //busca el producto por nombre segun el texto en el campo de texto de seleccionar producto 
        List<Producto> listaProd = new ArrayList();

        String sql = "call buscarProductoSimilar(?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto objProducto = new Producto();
                objProducto.setIdproducto(rs.getString("idproducto"));
                objProducto.setNombre(rs.getString("nombre"));
                objProducto.setPrecioCaja(rs.getDouble("precioCaja"));
                objProducto.setPrecioUnidad(rs.getDouble("PrecioUnidad"));
                objProducto.setExistencia(rs.getDouble("existencia"));
                listaProd.add(objProducto);

            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, " " + e);

        }
        return listaProd;

    }
    
    
    public Double[] cosultaPrecio(String idproducto) {
        //busca el precio del producto segun los parametros unidad de medida 
        Double precioCantidad[] = new Double[9];
        String sql = "call buscaPrecio(?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idproducto);
            rs = ps.executeQuery();
            while (rs.next()) {
                precioCantidad[0] = rs.getDouble("precioCaja");
                precioCantidad[1] = rs.getDouble("precioUnidad");
                precioCantidad[2] = rs.getDouble("precioDocena");
                precioCantidad[3] = rs.getDouble("precioProm");
                precioCantidad[4] = rs.getDouble("cantCaja");
                precioCantidad[5] = rs.getDouble("cantUnidad");
                precioCantidad[6] = rs.getDouble("cantDocena");
                precioCantidad[7] = rs.getDouble("cantProm");
                precioCantidad[8] = rs.getDouble("existencia");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return precioCantidad ;
 
    }

}
