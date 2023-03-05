/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import factura.numtoLetter;
import controlador.VentaDAO;
import modelo.Detalle;
import controlador.DetalleDAO;
import factura.numtoLetter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import controlador.SerieDAO;
import factura.Impresor;
import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import otros_recursos.Utilidades;
import modelo.Venta;

/**
 *
 * @author admin
 */
public class frmDetalleFactura extends javax.swing.JFrame {

    String correlativoOld, serieOld;
    VentaDAO objVentaDAO = new VentaDAO();
    DetalleDAO objDetalleDAO = new DetalleDAO();
    DefaultTableModel modelo;
    Double total = 0.00;
    String montoEnLetra = "";
    Venta objVenta = new Venta();

    /**
     * Creates new form frmDetalleFactura
     */
    public frmDetalleFactura(String serie, String correlativo) {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);// modo pantalla Completa 
        serieOld = serie;
        correlativoOld = correlativo;
        verEncavezadoVenta();
        verDetalle();

    }

    public void verEncavezadoVenta() {
        // consulta los datos de la factura y los asigna componente correspondiente
        String encabezado[];
        encabezado = objVentaDAO.verEncabezado(serieOld, correlativoOld);  // conexio BD

        txtCliente.setText(encabezado[0]);
        txtDireccionCF.setText(encabezado[1]);
        txtDuiCF.setText(encabezado[2]);
        lblFechaRegistrada.setText(encabezado[3]);
        txtSerie.setText(encabezado[4]);
        txtCorrelativo.setText(encabezado[5]);
        lblEstado.setText(encabezado[6]);
    }

    public void verDetalle() {

        // consulta el dertalle segun serie y correlativo y con esos datos llena la tabla 
        List<Detalle> ListarDetalle = objDetalleDAO.verDetalle(serieOld, Integer.valueOf(correlativoOld));  // conexio BD
        modelo = (DefaultTableModel) tableVentaCF.getModel();// definimos el nmodelo  
        Object[] obj = new Object[7];

        for (int i = 0; i < ListarDetalle.size(); i++) {

            obj[0] = ListarDetalle.get(i).getNlinea();
            obj[1] = ListarDetalle.get(i).getIdproducto();
            obj[2] = ListarDetalle.get(i).getNombreProd();
            obj[3] = ListarDetalle.get(i).getCantidadimpresa();
            obj[4] = ListarDetalle.get(i).getUnidadmedida();
            obj[5] = ListarDetalle.get(i).getPrecioU();
            obj[6] = ListarDetalle.get(i).getTotalProd();

            modelo.addRow(obj);
            total += ListarDetalle.get(i).getTotalProd();
        }
        lblTotalCF.setText(total.toString());
        tableVentaCF.setModel(modelo); // le asignamos el modelo  al a tabla 

    }

    private void montoLetra(String dato) {
        // agarrarr el monto de la totalPagarCF y pasarlo a letras 
        try {
            // nuevo metodo de conversion de numero a letra 
            numtoLetter objnumtoLetter = new numtoLetter();
            Double num = Double.valueOf(dato);
            montoEnLetra = objnumtoLetter.ConvertirNum(num);
            montoEnLetra = montoEnLetra.toUpperCase();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL FACTURAR ", "ERROR !", JOptionPane.ERROR_MESSAGE);
        }

    }

    public boolean ModificarVenta() {

        try {

            String serie, dui = "";
            int noDocumeto;
            serie = txtSerie.getText().trim();
            noDocumeto = Integer.parseInt(txtCorrelativo.getText().trim());
            dui = txtDuiCF.getText();
            String nombre_no_reg = txtCliente.getText();
            String dui_no_reg = txtDuiCF.getText();

            total = Double.valueOf(lblTotalCF.getText());

            objVenta.setSerie(serie);
            objVenta.setNoDocumento(noDocumeto);
            objVenta.setDui(" ");
            objVenta.setFechamodificar(jDateChooser1.getDate().toString());
            objVenta.setNombre_no_reg(nombre_no_reg);
            objVenta.setDui_no_reg(dui_no_reg);

            return objVentaDAO.RegistrarVenta(objVenta);//conexion a base de datos 

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar" + e, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    private void modificarFactura() {
        // brinda LA POSIBILIDAD DE MDIFICAR FECHA
        // SERIE Y CORRELATIVO 

        // validar si el registro esta anulado 
        String estado_anulado = lblEstado.getText();
        if (estado_anulado.equals("ANULADO")) {
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA ESTA ANULADO Y ES IMPOSIBLE MODIFICARLO", "NO SE PUEDE MODIFICAR ", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String estado = btnActualizar.getText();
        if (estado.equals("ACTUALIZAR")) {
//            txtCliente.setEditable(true);
//            txtDuiCF.setEditable(true);
            txtSerie.setEditable(true);
            txtCorrelativo.setEditable(true);
            lblFecha_eqiqueta.setVisible(true);
            jDateChooser1.setVisible(true);
            btnActualizar.setText("GUARDAR");
            btnImprimir.setEnabled(false);
            btnAnular.setEnabled(false);
            btnEliminar.setEnabled(false);
        } else {
//            txtCliente.setEditable(true);
//            txtDuiCF.setEditable(false);
            txtSerie.setEditable(false);
            txtCorrelativo.setEditable(false);
            lblFecha_eqiqueta.setVisible(false);
            jDateChooser1.setVisible(false);
            btnActualizar.setText("ACTUALIZAR");
            btnImprimir.setEnabled(true);
            btnAnular.setEnabled(true);
            btnEliminar.setEnabled(true);

            String fecha = "null";
            if (null != jDateChooser1.getDate()) {
                int year = jDateChooser1.getCalendar().get(Calendar.YEAR);
                int month = jDateChooser1.getCalendar().get(Calendar.MARCH);
                int day = jDateChooser1.getCalendar().get(Calendar.DAY_OF_MONTH);
                fecha = "" + year + "-" + (month + 1) + "-" + day;
            } else {
                fecha = lblFechaRegistrada.getText();
            }

            objVenta.setFechamodificar(fecha);
            objVenta.setSerie(txtSerie.getText());
            objVenta.setNoDocumento(Integer.valueOf(txtCorrelativo.getText()));

            Boolean modificacion = objVentaDAO.modificarFactura(objVenta, serieOld, Integer.valueOf(correlativoOld));
            if (modificacion) {
                JOptionPane.showMessageDialog(null, "MODIFICACION EXITOSA!");
            } else {
                JOptionPane.showMessageDialog(null, "NO SE PUDO MODIFICAR EL REGISTRO", "ERROR AL MODIFICAR", JOptionPane.ERROR_MESSAGE);
                verEncavezadoVenta();
                verDetalle();

            }

            // consulto nuevamente el detalle 
            Utilidades.limpiartabla(modelo);
            serieOld = txtSerie.getText();
            correlativoOld = txtCorrelativo.getText();
            total = 0.00;
            verEncavezadoVenta();
            verDetalle();

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDuiCF = new javax.swing.JTextField();
        txtDireccionCF = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        lblFecha_eqiqueta = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableVentaCF = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtCliente = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        lblFechaRegistrada = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblTotalCF = new javax.swing.JLabel();
        txtCorrelativo = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DETALLE FACTURA COMERCIAL");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("CLIENTE");

        jLabel9.setText("DIRECCION");

        jLabel12.setText("DUI");

        txtDuiCF.setEditable(false);

        txtDireccionCF.setEditable(false);

        jLabel51.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 0, 0));
        jLabel51.setText("DETALLE FACTURA DE CONSUMIDOR FINAL ");

        lblFecha_eqiqueta.setText("FECHA ");
        lblFecha_eqiqueta.setVisible(false);

        jLabel7.setText("SERIE");

        jLabel11.setText("CORRELATIVO");

        tableVentaCF.setForeground(new java.awt.Color(51, 102, 255));
        tableVentaCF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEM", "CODIGO", "DESCRIPCION", "CANTIDAD", "UNIDAD DE MEDIDA", "PRECIO UNITARIO", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableVentaCF.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableVentaCF.setEnabled(false);
        tableVentaCF.setGridColor(new java.awt.Color(255, 255, 255));
        tableVentaCF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tableVentaCFPropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(tableVentaCF);

        jLabel1.setText("ESTADO");

        lblEstado.setText("------");

        txtSerie.setEditable(false);

        txtCliente.setEditable(false);

        jDateChooser1.setVisible(false);

        jLabel3.setText("FECHA REGISTRADA");

        lblFechaRegistrada.setText("_____");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setForeground(new java.awt.Color(153, 0, 0));
        btnEliminar.setText("ANULAR Y ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnAnular.setForeground(new java.awt.Color(153, 0, 0));
        btnAnular.setText("ANULAR");
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });

        btnImprimir.setText("IMPRIMIR");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jLabel14.setText("TOTAL");

        lblTotalCF.setText("0.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnImprimir)
                .addGap(65, 65, 65)
                .addComponent(btnAnular)
                .addGap(66, 66, 66)
                .addComponent(btnEliminar)
                .addGap(60, 60, 60)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(lblTotalCF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnAnular)
                    .addComponent(btnImprimir)
                    .addComponent(jLabel14)
                    .addComponent(lblTotalCF))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        txtCorrelativo.setEditable(false);
        txtCorrelativo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("######"))));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addGap(84, 84, 84))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEstado)
                    .addComponent(lblFechaRegistrada))
                .addGap(333, 333, 333))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDireccionCF, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDuiCF, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblFecha_eqiqueta)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblEstado))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblFechaRegistrada))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel12)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDireccionCF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel11))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDuiCF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblFecha_eqiqueta))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 559, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableVentaCFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tableVentaCFPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_tableVentaCFPropertyChange

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        try {
            // TODO add your handling code here:
            montoLetra(lblTotalCF.getText()); // obtiene el monto total en letras
            generarFacturaPdf(); // genera la facturta en pdf
            Impresor objImpresor = new Impresor();
            objImpresor.imprimir(); // imprimira la factura en fisico
        } catch (PrinterException | IOException ex) {
            Logger.getLogger(frmDetalleFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        modificarFactura();

    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        // TODO add your handling code here:
        String estado = lblEstado.getText();
        if (estado.equals("ANULADO")) {
            JOptionPane.showMessageDialog(null, "ESTE REGISTRO YA ESTA ANULADO!!", "ERROR AL ANULAR", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int reply = JOptionPane.showConfirmDialog(null, "ESTA SEGURO DE ANULAR ESTE REGISTRO?", "ANULAR", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            objVentaDAO.anularVenta(serieOld, Integer.valueOf(correlativoOld));
        }

        // volver a consultar el detalle de la factura 
        total = 0.00;
        Utilidades.limpiartabla(modelo);
        verEncavezadoVenta();
        verDetalle();
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int reply = JOptionPane.showConfirmDialog(null, "ESTA SEGURO DE ELIMINAR ESTE REGISTRO?", "ELIMINAR", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            eliminarVenta();
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
  
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(frmDetalleFactura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDetalleFactura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDetalleFactura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDetalleFactura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDetalleFactura("", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnImprimir;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaRegistrada;
    private javax.swing.JLabel lblFecha_eqiqueta;
    private javax.swing.JLabel lblTotalCF;
    private javax.swing.JTable tableVentaCF;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JFormattedTextField txtCorrelativo;
    private javax.swing.JTextField txtDireccionCF;
    private javax.swing.JTextField txtDuiCF;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables

    public String obtenerAncho(int tipo) {
        //obtener ancho para factura de consumidor final si el tipo es 1 y si es otro valor para ccf
        SerieDAO objSerieDAO = new SerieDAO();

        String anchocf, anchocc;
        ArrayList<String> listaAncho = new ArrayList<>();
        listaAncho = objSerieDAO.verancho();
        if (tipo == 1) {
            anchocf = listaAncho.get(0);
            return anchocf;
        } else {
            anchocc = listaAncho.get(1);
            return anchocc;
        }

    }

    private void generarFacturaPdf() {

        //genera una factuira en formato pdf 
        // String correlativo = txtCorrelativoCF.getText();
        try {

            int anchocf = Integer.parseInt(obtenerAncho(1)); // obtiene el ancho de la factura comercial 

            FileOutputStream archivo;

            File salida = new File("src/factura/venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, archivo);
            doc.open();

            doc.open();

            //fuente 
            Font fuente = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
            Font fuente2 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
            Font fuente3 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);

            // imprimir espacio 
            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            espacio.add("\n");
            espacio.setAlignment(Element.ALIGN_CENTER);
            doc.add(espacio);

            // imprimir correlativo 
            String espaciocorr = "                                                                               ";//este espacio es para alinea el correlativo 
            Paragraph noDocumento = new Paragraph(new Phrase(txtCorrelativo.getText() + espaciocorr,
                    fuente2));
            noDocumento.add(Chunk.NEWLINE);
            noDocumento.setAlignment(Element.ALIGN_CENTER);
            doc.add(noDocumento);
            // cliente y fecha 
            PdfPTable tablaCli = new PdfPTable(3);
            tablaCli.setWidthPercentage(anchocf);
            tablaCli.getDefaultCell().setBorder(0);
            float[] columnaCli = new float[]{5f, 21f, 14f};
            tablaCli.setWidths(columnaCli);
            tablaCli.setHorizontalAlignment(Element.ALIGN_LEFT);
            String nombrelimite = txtCliente.getText();
            //limitar impresion nombre 

            // fIn limitar impresion nombre   
            PdfPCell cliCliente = new PdfPCell(new Phrase(nombrelimite + "\n"
                    + txtDuiCF.getText() + "\n\n"
                    + txtDireccionCF.getText(), fuente));
            PdfPCell cliFecha = new PdfPCell(new Phrase(lblFechaRegistrada.getText(), fuente));

            cliCliente.setBorder(0);
            cliFecha.setBorder(0);
            tablaCli.addCell("");
            tablaCli.addCell(cliCliente);
            tablaCli.addCell(cliFecha);
            doc.add(tablaCli);

            // imprimir espacio 
            Paragraph espacio1 = new Paragraph();
            espacio1.add(Chunk.NEWLINE);
            espacio1.add("");
            espacio1.add("");
            espacio1.setAlignment(Element.ALIGN_CENTER);
            doc.add(espacio);

            //productos 
            PdfPTable tablapro = new PdfPTable(5);
            tablapro.setWidthPercentage(anchocf);
            tablapro.getDefaultCell().setBorder(0);
            float[] columnapro = new float[]{5f, 11f, 25f, 20f, 15f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            for (int i = 0; i < tableVentaCF.getRowCount(); i++) {
                String cantidad = tableVentaCF.getValueAt(i, 3).toString();
                String unidadMerdida = tableVentaCF.getValueAt(i, 4).toString();
                // limitar impresion unidad de medida 

                //  fin limitar impresuin unidad de medida 
                String descripcion = tableVentaCF.getValueAt(i, 2).toString();
                //limitar impresion descripcion 

                // fin limitar impresion descripcion  
                String precio = tableVentaCF.getValueAt(i, 5).toString();
                String total = tableVentaCF.getValueAt(i, 6).toString();
                PdfPCell pcant = new PdfPCell(new Phrase(cantidad, fuente2));
                pcant.setBorder(0);
                tablapro.addCell(pcant);
                PdfPCell punidadMedida = new PdfPCell(new Phrase(unidadMerdida, fuente2));
                punidadMedida.setBorder(0);
                tablapro.addCell(punidadMedida);
                PdfPCell pdescripcion = new PdfPCell(new Phrase(descripcion, fuente2));
                pdescripcion.setBorder(0);
                tablapro.addCell(pdescripcion);
                PdfPCell pprecio = new PdfPCell(new Phrase(precio, fuente2));
                pprecio.setBorder(0);
                tablapro.addCell(pprecio);
                PdfPCell ptotal = new PdfPCell(new Phrase(total, fuente3));
                ptotal.setBorder(0);
                tablapro.addCell(ptotal);

            }
            doc.add(tablapro);

            int montoPosicion = 8 - (tableVentaCF.getRowCount());
            for (int i = 0; i < montoPosicion; i++) {

                // imprimir espacio 
                espacio1.add(Chunk.NEWLINE);
                espacio1.add("");
                espacio1.setAlignment(Element.ALIGN_CENTER);
                doc.add(espacio);

            }

            // totales
            if (lblTotalCF.getText().equals("0.00")) {
                // si el total es 0.00 no imprimo los totales 
                doc.close();
                archivo.close();
                // Desktop.getDesktop().open(salida);
            } else {
                //limitar impresion montoLetras 
                String grande = "";
                try {
                    char[] aCaracteres2 = montoEnLetra.toCharArray();
                    int length2 = montoEnLetra.length();
                    if (length2 > (anchocf / 2.4)) {
                        montoEnLetra = "";
                        for (int j = 0; j < length2; j++) {

                            if (j < (anchocf / 2.4)) {
                                montoEnLetra += String.valueOf(aCaracteres2[j]);
                            } else {
                                if (j < ((anchocf / 2.4) * 2)) {
                                    grande += String.valueOf(aCaracteres2[j]);
                                }

                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error " + e);
                }

                // fin limitar impresion montoLetras 
                Rectangle rect = new Rectangle(65, 405, 550, 800);
                writer.setBoxSize("art", rect);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(new Phrase(montoEnLetra, fuente2)), rect.getLeft(), rect.getBottom(), 0);
                if (!(grande.isBlank())) {
                    // por si el onto es demasiado largo 
                    Rectangle rect1 = new Rectangle(65, 395, 550, 800);
                    writer.setBoxSize("art", rect1);
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                            new Phrase(new Phrase(grande, fuente2)), rect1.getLeft(), rect1.getBottom(), 0);
                }
                // sumas 
                int posicionx = (int) (anchocf * 4.7);
                Rectangle rect2 = new Rectangle(posicionx, 405, 550, 800);
                writer.setBoxSize("art", rect2);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(
                                (new Phrase(lblTotalCF.getText(), fuente3))
                        ), rect2.getLeft(), rect2.getBottom(), 0);

                Rectangle rect3 = new Rectangle(posicionx, 345, 550, 800);
                writer.setBoxSize("art", rect3);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(
                                (new Phrase(lblTotalCF.getText(), fuente3))
                        ), rect3.getLeft(), rect3.getBottom(), 0);

                doc.close();
                archivo.close();
                //Desktop.getDesktop().open(salida);

            }
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al imprimir" + e.toString());
        }
    }

    private void eliminarVenta() {

        String estado = lblEstado.getText();
        if (!(estado.equals("ANULADO"))) {
            objVentaDAO.anularVenta(serieOld, Integer.valueOf(correlativoOld));
        }
        objVentaDAO.eliminarVenta(serieOld, Integer.valueOf(correlativoOld));
        this.dispose();

    }

}
