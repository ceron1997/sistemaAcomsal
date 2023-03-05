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
import modelo.ClienteCC;
import modelo.Conexion;

/**
 *
 * @author admin
 */
public class ClienteCCDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List buscarNombresSimilares(String nombre) {
        //busca el cliente por nombre segun el texto en el campo de texto de seleccionar cliente
        List<ClienteCC> ListaCl = new ArrayList();

        String sql = "call buscarclientesNombresimilarCC(?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            while (rs.next()) {
                ClienteCC objClienteCC = new ClienteCC();
                objClienteCC.setDui(rs.getString("noRegistro"));
                objClienteCC.setNombre(rs.getString("nombre"));
                objClienteCC.setDireccion(rs.getString("direccion"));
                objClienteCC.setNit(rs.getString("nit"));
                ListaCl.add(objClienteCC);

            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, " " + e);

        }
        return ListaCl;

    }

     public String[] VerElclienteCC(String noRegistro) {
        //busca el cliente poe numero de registro   
        String cliente[] = new String[7];
        String sql = "call sp_vertodoClienteCC(?);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, noRegistro);
            rs = ps.executeQuery();
            while (rs.next()) {

                cliente[0] = rs.getString("noRegistro");
                cliente[1] = rs.getString("nombre");
                cliente[2] = rs.getString("dui");
                cliente[3] = rs.getString("nit");
                cliente[4] = rs.getString("nombreNegocio");
                cliente[5] = rs.getString("direccion");
                cliente[6] = rs.getString("giro");
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cliente;
        
    }

}
