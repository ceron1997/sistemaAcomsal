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
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.Detalle;
import modelo.Venta;

/**
 *
 * @author admin
 */
public class DetalleDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarDetalle(Detalle Dv) {
        // inserta el detalle de venta para factura y ccf 
        String sql;
        sql = "call insertarDetalleVenta (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, Dv.getSerie());
            ps.setInt(2, Dv.getNoDocumento());
            ps.setInt(3, Dv.getNlinea());
            ps.setString(4, Dv.getIdproducto());
            ps.setDouble(5, Dv.getCantidad());
            ps.setDouble(6, Dv.getCantidadimpresa());
            ps.setString(7, Dv.getUnidadmedida());
            ps.setDouble(8, Dv.getPrecioU());
            ps.setDouble(9, Dv.getTotalProd());
            ps.setInt(10, 0);
            ps.setInt(11, 0);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString() + " detalle");
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

    }

    public List<Detalle> verDetalle(String serieOld, int correlativoOld) {
        // este metodo hace la consulta a la bd en la tabla detalle filtrando por 
        // serie y correlativo para mostrar en el detalle de la factura 
        List<Detalle> listarDetalle = new ArrayList();
       
        String sql = "call sp_verDetalle(?,?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, serieOld);
            ps.setInt(2, correlativoOld);
            rs = ps.executeQuery();
        
            while (rs.next()) {
                Detalle objDetalle = new Detalle();
                objDetalle.setNlinea(rs.getInt("noLinea"));
                objDetalle.setIdproducto(rs.getString("idproducto"));
                objDetalle.setNombreProd(rs.getString("nombre"));
                objDetalle.setCantidadimpresa(rs.getInt("cantidadimpresa"));
                objDetalle.setUnidadmedida(rs.getString("unidadmedida"));
                objDetalle.setPrecioU(rs.getDouble("precioUnitario"));
                objDetalle.setTotalProd(rs.getDouble("totalProd"));
                listarDetalle.add(objDetalle);
            }
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "LVL CONTROLADOR: " + e, "ERROR AL CONSULTAR", JOptionPane.ERROR_MESSAGE);

        }

        return listarDetalle;
    }

}
