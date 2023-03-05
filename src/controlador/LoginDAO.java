/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.Login;

/**
 *
 * @author PC
 */
public class LoginDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Login log(String nombre, String clave) {
        Login l = new Login();
        String sql = "SELECT * FROM usuario WHERE  nombre = ? AND clave = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if (rs.next()) {
                l.setIdusuario(rs.getString("idusuario"));
                l.setNombre(rs.getString("nombre"));
                l.setClave(rs.getString("clave"));
            }
              con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());

        }
        return l;
    }

    public void ConsultarUsuarios(JComboBox cbxNombreUsuario) {

        try {
            String sql = "select nombre from usuario";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                cbxNombreUsuario.addItem(rs.getString("nombre"));
            }
              con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "se ha producido un error ",
                    e.toString(), JOptionPane.ERROR);

        }

    }
}
