/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class Conexion {
    
     Connection con;

    public Connection getConnection() {
        try {
            String myBD = "jdbc:mysql://localhost:3306/sistemaacomsal?serverTimezone=UTC";
            con = DriverManager.getConnection(myBD, "root", "");
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "Error en la conexion a la base de datos" + e);
        }
        return null;
    }
    
    
}
