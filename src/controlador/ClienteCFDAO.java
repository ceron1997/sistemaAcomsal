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
import modelo.ClienteCF;
import modelo.Conexion;

/**
 *
 * @author admin
 */
public class ClienteCFDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // para factura 
    public void consultarNombresClienteCF(JComboBox clienteF) {
        //sirve para llenar el combobox de nombres de clientes en factura de consumidor final 

        try {
            String sql = "select * from v_todocliente ";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                clienteF.addItem(rs.getString("nombre")); 
            }
  con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "se ha producido un error ", e.toString(), JOptionPane.ERROR);
        }

    }

    public String[] VerElcliente(String nombre) {
        //busca el cliente segun el item seleccionado en el combobox 
        String cliente[] = new String[3];
        String sql = "call vertodoCliente(?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {

                cliente[0] = rs.getString("dui");
                cliente[1] = rs.getString("direccion");
                cliente[2] = rs.getString("nit");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cliente;
        
    }
      

    public List buscarNombresSimilares(String nombre) {
        //busca el cliente por nombre segun el texto en el campo de texto de seleccionar cliente
        List<ClienteCF> ListaCl = new ArrayList();

        String sql = "call buscarclientesNombresimilar(?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            while (rs.next()) {
                ClienteCF objClienteCF = new ClienteCF();
                objClienteCF.setDui(rs.getString("dui"));
                objClienteCF.setNombre(rs.getString("nombre"));
                objClienteCF.setNombreNegocio(rs.getString("nombreNegocio"));
                objClienteCF.setDireccion(rs.getString("direccion"));
                objClienteCF.setNit(rs.getString("nit"));
                ListaCl.add(objClienteCF);

            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, " " + e);

        }
        return ListaCl;

    }

}
