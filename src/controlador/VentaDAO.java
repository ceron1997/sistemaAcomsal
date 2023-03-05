/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.Detalle;
import modelo.Venta;

/**
 *
 * @author admin
 */
public class VentaDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarVenta(Venta objVenta) {
        // registra la venta para factura y ccf 

        String sql = "call insertarVenta(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, objVenta.getSerie());
            ps.setInt(2, objVenta.getNoDocumento());
            ps.setString(3, objVenta.getDui());
            ps.setString(4, objVenta.getNoRegistro());
            ps.setInt(5, objVenta.getIdcondicionPago());
            ps.setInt(6, objVenta.getIdusuario());
            ps.setDouble(7, objVenta.getSuma());
            ps.setDouble(8, objVenta.getIva());
            ps.setDouble(9, objVenta.getTotal());
            ps.setInt(10, objVenta.getIdtipodoc());
            ps.setString(11, String.valueOf(LocalDateTime.now()));
            ps.setInt(12, 0);
            ps.setInt(13, 0);
            ps.setString(14, objVenta.getDui_no_reg());
            ps.setString(15, objVenta.getNombre_no_reg());

            ps.execute();
            con.close();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "\nEs posible que el registro con esa serie y correlativo "
                    + "\nya este guardado " + e, "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println("" + e);
            return false;

        }
        //quito la ventana de notificacion por cuestiones de productividad 
        //  JOptionPane.showMessageDialog(null, "Venta Registrada");

    }

    public int obtenerMaxCorrelativo(String p_serie) {
        String sql = "call sp_ultimoRegistroVenta(?);";
        int Correlativo = 0;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p_serie);
            rs = ps.executeQuery();
            while (rs.next()) {
                Correlativo = rs.getInt("ultimoRegistro");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Correlativo;
    }

    public String[] verEncabezado(String serieOld, String correlativoOld) {

        int noDocumento = Integer.valueOf(correlativoOld);
        String elementosEncabezado[] = new String[7];

        String sql = "call sp_verEncabezadoVenta(?,?); ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, serieOld);
            ps.setInt(2, noDocumento);
            rs = ps.executeQuery();
            while (rs.next()) {

                elementosEncabezado[0] = rs.getString("nombre");
                elementosEncabezado[1] = rs.getString("direccion");
                elementosEncabezado[2] = rs.getString("dui");
                elementosEncabezado[3] = rs.getString("fecha");
                elementosEncabezado[4] = rs.getString("serie");
                elementosEncabezado[5] = rs.getString("noDcoumento");
                elementosEncabezado[6] = rs.getString("estado");
                // para el ccf consultar los demas datos: suma, iva, total
            }
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL CONSULTAR ", JOptionPane.ERROR_MESSAGE);
        }
        return elementosEncabezado;
    }

    public boolean modificarFactura(Venta objVenta, String p_serieOld, int p_noDocOld) {
        // registra la venta para factura y ccf 

        String sql = "call sp_modificarFactura(?,?,?,?,?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p_serieOld);
            ps.setInt(2, p_noDocOld);
            ps.setString(3, objVenta.getSerie());
            ps.setInt(4, objVenta.getNoDocumento());
            ps.setString(5, objVenta.getFechamodificar());

            ps.execute();
            con.close();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "\nEs posible que el registro con esa serie y correlativo "
                    + "\nya este guardado " + e, "Error ", JOptionPane.ERROR_MESSAGE);
            System.out.println("" + e);
            return false;

        }
    }

    public void anularVenta(String serie, int noDocumento) {
        String sql = "call sp_AnularVenta(?,?); ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, serie);
            ps.setInt(2, noDocumento);
            ps.execute();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL ANULAR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void eliminarVenta(String serie, int noDocumento) {

        String sql = "call sp_EliminarVenta(?,?); ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, serie);
            ps.setInt(2, noDocumento);
            ps.execute();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL eliminar", JOptionPane.ERROR_MESSAGE);
        }
    }
}
