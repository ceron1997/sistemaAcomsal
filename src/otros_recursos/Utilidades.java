/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package otros_recursos;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class Utilidades {
   
    public static int idusuario = 1 ; // variable que se inicia cuando se inica sesion guarda el id del usuario 
    public static boolean clienteRegistrado = true ; // variable indica si el cliente esta o no registrado se usa en factura Comsumidor final 0 
   public static boolean clienteRegistradoC = true ; // cuando se quiera hacer una factura comercial para un cliente contribuyente
    public static String prueba = ""; // solo para prueba 
    
    
       public static void limpiartabla(DefaultTableModel p_modelo) {
        //  limpia el modelo que recibe como parametro 
        for (int i = 0; i < p_modelo.getRowCount(); i++) {
            p_modelo.removeRow(i);
            i--;
        }
    }
    

}
