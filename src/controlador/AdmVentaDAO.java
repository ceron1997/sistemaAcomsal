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
import modelo.AdmVenta;

/**
 *
 * @author admin
 */
public class AdmVentaDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public AdmVentaDAO() {
    }

    public List listarVentas(int year, int tipoDoc) {
        //busca el producto por nombre segun el texto en el campo de texto de seleccionar producto 
        List<AdmVenta> listaVenta = new ArrayList();

        String sql = "call sp_filtroAdmVenta(?,?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, tipoDoc);
            rs = ps.executeQuery();

            while (rs.next()) {
                AdmVenta objAdmVenta = new AdmVenta();
                objAdmVenta.setFecha((rs.getString("fecha")));
                objAdmVenta.setTipoDoc((rs.getString("tipo documento")));
                objAdmVenta.setSerie((rs.getString("serie")));
                objAdmVenta.setCorr((rs.getInt("noDcoumento")));
                objAdmVenta.setCliente((rs.getString("Cliente")));
                objAdmVenta.setTotal(rs.getDouble("total"));
                listaVenta.add(objAdmVenta);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "error al consultar ventas lvl: Controlador " + e);

        }
        return listaVenta;

    }

    public List listarVentas_avanzado(int p_noDocumento, String p_Cliente, String p_fecha) {
        //busca el producto por nombre segun el texto en el campo de texto de seleccionar producto 
        List<AdmVenta> listaVenta = new ArrayList();

        String sql = "call sp_filtroAvanzado_admVenta(?,?,?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, p_noDocumento);
            ps.setString(2, p_Cliente);
            ps.setString(3, p_fecha);

            rs = ps.executeQuery();

            while (rs.next()) {
                AdmVenta objAdmVenta = new AdmVenta();
                objAdmVenta.setFecha((rs.getString("fecha")));
                objAdmVenta.setTipoDoc((rs.getString("tipo documento")));
                objAdmVenta.setSerie((rs.getString("serie")));
                objAdmVenta.setCorr((rs.getInt("noDcoumento")));
                objAdmVenta.setCliente((rs.getString("Cliente")));
                objAdmVenta.setTotal(rs.getDouble("total"));
                listaVenta.add(objAdmVenta);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "error al consultar ventas lvl: Controlador " + e);

        }
        return listaVenta;

    }

    public List<AdmVenta> totalCajaResumido(String p_fecha1, String p_fecha2) {

        List<AdmVenta> listaVenta = new ArrayList();
        // me sale mas facil dejar esto de esta manera 
        String sql = "select * from v_amdventa where fecha BETWEEN '" + p_fecha1 + "' AND '" + p_fecha2 + "' ORDER BY fecha, noDcoumento ASC ";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AdmVenta objAdmVenta = new AdmVenta();
                objAdmVenta.setFecha((rs.getString("fecha")));
                objAdmVenta.setTipoDoc((rs.getString("tipo documento")));
                objAdmVenta.setSerie((rs.getString("serie")));
                objAdmVenta.setCorr((rs.getInt("noDcoumento")));
                objAdmVenta.setFormaPago(rs.getString("condicionPago"));
                objAdmVenta.setCliente((rs.getString("Cliente")));
                objAdmVenta.setUsuario(rs.getString("usuario"));
                objAdmVenta.setTotal(rs.getDouble("total"));
                listaVenta.add(objAdmVenta);
            }
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al consultar ventas lvl: Controlador " + e);
        }
        return listaVenta;

    }

    public List<AdmVenta> listarVentasCosto(String p_fecha1, String p_fecha2) {
        costoPromedioAll();
        List<AdmVenta> ListaVentaCosto = new ArrayList();
        // me sale mas facil dejar esto de esta manera 
        String sql = "select * from v_detalleventacosto where fecha BETWEEN '" + p_fecha1 + "' AND '" + p_fecha2 + "' ORDER BY fecha, noDcoumento ASC ";
        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AdmVenta objAdmVenta = new AdmVenta();
                objAdmVenta.setFecha((rs.getString("fecha")));
                objAdmVenta.setSerie((rs.getString("serie")));
                objAdmVenta.setCorr((rs.getInt("noDcoumento")));
                objAdmVenta.setNombreProducto(rs.getString("nombre"));
                objAdmVenta.setCantidad(rs.getInt("cantidad"));
                objAdmVenta.setPrecioPorUnidad(rs.getDouble("precioPorUnidad"));
                objAdmVenta.setTotalProducto(rs.getDouble("totalProd"));
                objAdmVenta.setCostoprom(rs.getDouble("costoprom"));
                objAdmVenta.setTotalCosto(rs.getDouble("totalCosto"));
                ListaVentaCosto.add(objAdmVenta);
            }
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al consultar ventas lvl: Controlador " + e);
        }
        return ListaVentaCosto;
    }

    private void costoPromedioAll() {

        List<AdmVenta> listaVenta = new ArrayList();

        String sql = "call sp_costopromedioAll();";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "error al consultar ventas lvl: Controlador " + e);

        }

    }

}
