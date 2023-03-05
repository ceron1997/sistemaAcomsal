/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Conexion;

/**
 *
 * @author admin
 */
public class CondicionPagoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public void consultarNombresCondicionP(JComboBox condicinesPago) {
        //sirve para llenar el combobox de nombres de clientes en factura de consumidor final 

        try {
            String sql = "call sp_vercondiciondepago() ";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                condicinesPago.addItem(rs.getString("nombre"));
            }
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "se ha producido un error ", e.toString(), JOptionPane.ERROR);
        }
    }
 
    
        public String Veridcondp (String nombre) {
        //busca el cliente segun el item seleccionado en el combobox 
        String idcondp=""; 
        String sql = "call sp_veridcondicionpago(?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                idcondp = rs.getString("idcondicionpago");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return idcondp;
        
    }
    
    

}
