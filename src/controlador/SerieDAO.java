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
import modelo.Conexion;
import modelo.Serie;

/**
 *
 * @author admin
 */
public class SerieDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public ArrayList<String> obtenerseries() {
        {
            ArrayList<String> listaSerie = new ArrayList<>();
            try {
                String sql = "SELECT * FROM `serie` ";
                con = cn.getConnection();
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    listaSerie.add(rs.getString("numerodeserie"));
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("" + e);
            }

            return listaSerie;

        }
    } public ArrayList<String> verancho() {
        {
            ArrayList<String> listaAncho = new ArrayList<>();
            try {
                String sql = "call sp_verancho(); ";
                con = cn.getConnection();
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    listaAncho.add(rs.getString("ancho"));
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("" + e);
            }

            return listaAncho;

        }
    }

    public void modificarSerie(Serie serie, int idserie) {
        String sql = "UPDATE `serie` SET `numerodeserie`= ? WHERE idserie = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            if (idserie == 1) {
                ps.setString(1, serie.getNumeroSeriefact());
            } else {
                ps.setString(1, serie.getNumeroSerieccf());
            }
            ps.setInt(2, idserie);
            ps.execute();
            // return true;

        } catch (SQLException e) {
            System.out.println(e.toString());
            //  return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

   
    }
       public void modificarAncho(Serie serie, int idserie) {
        String sql = "call sp_updateAnchoOnSerie(?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            if (idserie == 1) {
                ps.setInt(1, serie.getAnchocf());
            } else {
                ps.setInt(1, serie.getAnchocc());
            }
            ps.setInt(2, idserie);
            ps.execute();
            // return true;

        } catch (SQLException e) {
            System.out.println(e.toString());
            //  return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

   
    }
    
}
