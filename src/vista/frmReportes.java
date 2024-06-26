/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.AdmVenta;
import controlador.AdmVentaDAO;
import java.io.IOException;
import table.ExportadorExcel;

/**
 *
 * @author admin
 */
public class frmReportes extends javax.swing.JFrame {

    AdmVentaDAO objAdmVentaDAO = new AdmVentaDAO();
    DefaultTableModel modelo_reporte1; // modelo para el reporte totañl de caja resumindo 
    Object[] datos = new String[]{};
    String fecha1, fecha2, reporte;
    Double totalVenta = 0.00, totalCosto = 0.00;

    /**
     * Creates new form frmReportes
     */
    public frmReportes() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH); // maximiza la ventana cuando se abre 
    }

    frmReportes(String reporte, String fecha1, String fecha2) {

        initComponents();
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        this.reporte = reporte;
        this.setExtendedState(MAXIMIZED_BOTH); // maximiza la ventana cuando se abre 
        lblTitulo.setText(reporte);
        lblRango.setText("DEL  " + fecha1 + "  HASTA EL  " + fecha2);

        if (reporte == "TOTAL DE CAJA RESUMIDO") {

            ListarTotalCajaResumido();
        }
        if (reporte == "DETALLE DE VENTAS CON COSTO") {
            jLabel2.setVisible(true);
            lblTotalCosto.setVisible(true);
            listarDetalleVentaCosto();

        }

    }

    public void ListarTotalCajaResumido() {
        
        List<AdmVenta> listarVentas = objAdmVentaDAO.totalCajaResumido(fecha1, fecha2);

        //modelo personalizado 
        boolean[] editable = {false, false, false, false, false, false, false};
        String[] Column = new String[]{
            "FECHA", "TIPO DOC ", "SERIE ", "NO. DOC", "CLIENTE ", "USUARIO", "TOTAL"
        };

        modelo_reporte1 = new DefaultTableModel(Column, 0) {

        };
        // fin modelo personalizado 

        Object[] ob = new Object[7];

        for (int i = 0; i < listarVentas.size(); i++) {

            ob[0] = listarVentas.get(i).getFecha();
            ob[1] = listarVentas.get(i).getTipoDoc();
            ob[2] = listarVentas.get(i).getSerie();
            ob[3] = listarVentas.get(i).getCorr();
            ob[4] = listarVentas.get(i).getCliente();
            ob[5] = listarVentas.get(i).getUsuario();
            ob[6] = listarVentas.get(i).getTotal();

            totalVenta += Double.valueOf(listarVentas.get(i).getTotal());
            modelo_reporte1.addRow(ob);// 
        }

        tableReporte.setModel(modelo_reporte1);
        lblTotal.setText(totalVenta.toString());
    }

    private void listarDetalleVentaCosto() {

       
        List<AdmVenta> listarVentasCosto = objAdmVentaDAO.listarVentasCosto(fecha1, fecha2);
        //modelo personalizado 
        boolean[] editable = {false, false, false, false, false, false, false, false, false};
        String[] Column = new String[]{
            "FECHA", "SERIE ", "NO. DOC", "PRODUCTO ", "CANTIDAD", "PRECIO POR UNIDAD", "TOTAL PRODUCTO",
            "COSTO UNITARIO PROMEDIO", "TOTAL COSTO"
        };
        modelo_reporte1 = new DefaultTableModel(Column, 0) {
        };
          Object[] ob = new Object[9];

        for (int i = 0; i < listarVentasCosto.size(); i++) {

            ob[0] = listarVentasCosto.get(i).getFecha();
            ob[1] = listarVentasCosto.get(i).getSerie();
            ob[2] = listarVentasCosto.get(i).getCorr();
            ob[3] = listarVentasCosto.get(i).getNombreProducto();
            ob[4] = listarVentasCosto.get(i).getCantidad();
            ob[5] = listarVentasCosto.get(i).getPrecioPorUnidad();
            ob[6] = listarVentasCosto.get(i).getTotalProducto();
            ob[7] = listarVentasCosto.get(i).getCostoprom();
            ob[8] = listarVentasCosto.get(i).getTotalCosto();

            totalVenta += Double.valueOf(listarVentasCosto.get(i).getTotalProducto());
            totalCosto += Double.valueOf(listarVentasCosto.get(i).getTotalCosto()); 
            modelo_reporte1.addRow(ob);// 
        }

        tableReporte.setModel(modelo_reporte1);
        lblTotal.setText(totalVenta.toString());
        lblTotalCosto.setText(totalCosto.toString());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReporte = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblRango = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTotalCosto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REPORTES");
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        tableReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableReporte);

        jButton1.setBackground(new java.awt.Color(0, 102, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ENVIAR A EXCEL ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("ESPECIFICA REPORTES");

        lblRango.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRango.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRango.setText("DEFINIR RANGO");

        jLabel1.setText("TOTAL VENTAS ");

        lblTotal.setText("0.00");

        jLabel2.setText("TOTAL COSTOS");

        lblTotalCosto.setText("0.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jButton1)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                            .addComponent(lblRango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRango)
                .addGap(9, 9, 9)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCosto)
                    .addComponent(jLabel2))
                .addGap(38, 38, 38))
        );

        jLabel2.setVisible(false);
        lblTotalCosto.setVisible(false);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
           try {
            ExportadorExcel objExportadorExcel = new ExportadorExcel();

            objExportadorExcel.exportarExcel(tableReporte);

        } catch (IOException ex) {
            System.out.println("Error al exportar: " + ex);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmReportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRango;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalCosto;
    private javax.swing.JTable tableReporte;
    // End of variables declaration//GEN-END:variables

}
