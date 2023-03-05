/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaacomsal;

import vista.frmLogin;

/**
 *
 * @author admin
 */
public class SistemaAcomsal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
        
        frmLogin objfrmLogin = new frmLogin(); 
        objfrmLogin.setVisible(true);
        }catch(Exception e){
        System.exit(0); 
        }
    }
    
}
