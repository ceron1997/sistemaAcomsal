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
import controlador.ClienteCCDAO;
import controlador.ClienteCFDAO;
import controlador.CondicionPagoDAO;
import controlador.DetalleDAO;
import controlador.ProductoDAO;
import controlador.SerieDAO;
import controlador.VentaDAO;
import factura.Impresor;
import factura.numtoLetter;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modelo.AdmVenta;
import modelo.ClienteCC;
import modelo.ClienteCF;
import modelo.Detalle;
import modelo.Producto;
import modelo.Venta;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import otros_recursos.Utilidades;
import table.Tabla;
import table.TablaCC;
import controlador.AdmVentaDAO;
import java.util.Calendar;
import table.ExportadorExcel;

/**
 *
 * @author admin
 */
public class frmVenta extends javax.swing.JFrame {

    //Getting the current date value of the system
    LocalDate current_date = LocalDate.now();

    // item para facturar y modelo 
    int item, cc_item;

    // fin item para facturar y modelo 
    // objeto para lka ventana de seleccionar arr_producto 
    frmSeleccionarProducto objfrmSeleccionarProducto = new frmSeleccionarProducto();
    // ventana seleccionar cliente 
    frmSeleccionarClienteCF objfrmSeleccionarClienteCF = new frmSeleccionarClienteCF();
    frmSeleccionarClienteCC objfrmSeleccionarClienteCC = new frmSeleccionarClienteCC();

    // objetos para clienteCF
    ClienteCF objClienetCF = new ClienteCF();
    ClienteCFDAO objClienteCFDAO = new ClienteCFDAO();
    //objetos para clienteCC
    ClienteCC objClienteCC = new ClienteCC();
    ClienteCCDAO objClienteCCDAO = new ClienteCCDAO();
    // objetos del arr_producto 
    Producto objProducto = new Producto();
    ProductoDAO objProductoDAO = new ProductoDAO();
    //objeto para guardar venta
    VentaDAO objVentaDAO = new VentaDAO();
    Venta objVenta = new Venta();
    //objeto para guardar venta fin 
    //objeto para guardar venta
    DetalleDAO objDetalleDAO = new DetalleDAO();
    Detalle objDetalle = new Detalle();
    //objeto para guardar venta fin 

    // creacion de un vector para restringir la facturacion 
    Double restringirFact[] = new Double[2];
    //para controlar cantidad a descontar en inventario 
    ArrayList<Double> CantidadCajaFactura = new ArrayList<>(); // sirve para saber cuanto se debe descontar de existencia 
    ArrayList<Double> cc_CantidadCajaFactura = new ArrayList<>(); // sirve para saber cuanto se debe descontar de existencia 
    Double cantUnidad;
    //para controlar cantidad a descontar en inventario  fin **

    // variable para el monto en letras 
    String montoEnLetra = "";
  
    // solo para llenar la tabla 
    Tabla objtabla = new Tabla();
    Object[] datos = new String[]{};
    String[] arr_producto;
    // para ccf 
    String[] arr_cliente_cc; // para agregar los datso al ccf 
    ArrayList lis_totalesProd = new ArrayList(); // para almacenar los valores exantos 
    // ArrayList lis_preciosProd = new ArrayList(); // para almacenar los valores sin iva para impresion  
    TablaCC objTablaCC = new TablaCC();
    // solo para llenar la tabla  fin 
    CondicionPagoDAO objCondicionPagoDAO = new CondicionPagoDAO();

    // crear objeto para el admVenta 
    AdmVenta objAdmVenta = new AdmVenta();
    AdmVentaDAO objAdmVentaDAO = new AdmVentaDAO();
    DefaultTableModel modeloventa; // modelo para la tabla de adm ventas

    /**
     * Creates new form frmVentas Creates new form frmVentas Creates new form
     * frmVentas Creates new form frmVentas Creates new form frmVentas Creates
     * new form frmVentas Creates new form frmVentas Creates new form frmVentas
     * Creates new form frmVentas Creates new form frmVentas Creates new form
     * frmVentas Creates new form frmVentas Creates new form frmVentas Creates
     * new form frmVentas
     */
    public frmVenta() {
        // este es el constructor 
        initComponents();

        // objtabla.visualizar(latabla, datos);//asigna el modelo 
        this.setExtendedState(MAXIMIZED_BOTH);// modo pantalla Completa 
        //obtener fecha actual 

        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dateObj.format(formatter);
        lblFechaCF.setText(date);//se asigna la fecha fact 
        cc_lblFechaCC.setText(date); // asigna la fecha ccf
        txtSerieCF.setText(obtenerserie(1));//asigna el numero de serie para fact 
        cc_txtSerieCC.setText(obtenerserie(2));//asigna el numero de serie para ccf
        AutoCompleteDecorator.decorate(cbxNombreCF);  // llama la libreria para autocompletar 
        objClienteCFDAO.consultarNombresClienteCF(cbxNombreCF);//llena el cbxNombreCF con los nombres de los clientes  
        AutoCompleteDecorator.decorate(cbxCondicionPago);  // llama la libreria para autocompletar 
        objCondicionPagoDAO.consultarNombresCondicionP(cbxCondicionPago);//llena el cbxNombreCF con las condiciones de pago   

        txtDuiCF.setText(" "); // pongo por defecto un caracter en blanco, se trata del dui del cliente sin registrar 
        //Sample 05: Handle Item State Change Event
        cbxNombreCF.addItemListener(new ItemListener() {
            //este listener se activa cuando el item del cbxNombreCF cambia 
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    buscarElCliente();
                }
            }
        });

        txtbuscaProdCF.addActionListener(
                // cambios en txtbuscaProdCF 
                //do whatever you want here the user has entered some text
                //and pressed the enter button on the keyboard.

                (ActionEvent e) -> {
                    try {
                        if (!(txtbuscaProdCF.getText().isBlank())) {
                            // llamar un metodo para buscar arr_producto por codigo 
                            SeleccionarProductoporCodigo(txtbuscaProdCF.getText());

                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "CODIGO NO REGISTRADO");
                    }

                }
        );

        objtabla.cbxUnidadMedida.addItemListener(
                (ItemEvent e) -> {
                    //este listener se activa cuando el item del cbxUnidadMedidaCF cambia
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        try {

                            int indiceFilaSeleccionada = tableVentaCF.getSelectedRow();
                            if (indiceFilaSeleccionada == -1) {
                                return;
                            }
                            String cantText = (String) tableVentaCF.getValueAt(indiceFilaSeleccionada, 3);
                            Double cantText_double = Double.valueOf(cantText);
                            restringirFacturacion(cantText_double, restringirFact[0], restringirFact[1]);

                        } catch (NumberFormatException error) {

                        }
                    }
                }
        );

        cc_tableVentaCC.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            //El método valueChange se debe sobreescribir obligatoriamente. 
            //Se ejecuta automáticamente cada vez que se hace una nueva selección. 
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Obtener el número de filas seleccionadas 
                int cuentaFilasSeleccionadas = cc_tableVentaCC.getSelectedRowCount();
                if (cuentaFilasSeleccionadas == 1) {
                    //Sólo hay una fila seleccionada 
                    try {

                        buscarPrecioSinModificarTabla(cc_tableVentaCC);
                        int indiceFilaSeleccionada = cc_tableVentaCC.getSelectedRow();
                        String cantText = (String) cc_tableVentaCC.getValueAt(indiceFilaSeleccionada, 3);
                        Double cantText_double = Double.valueOf(cantText);
                        restringirFacturacion(cantText_double, restringirFact[0], restringirFact[1]);
                    } catch (NumberFormatException error) {
                        //  la excepcion es capturada en buscarPrecioSinModificarTabla(); 
                    }

                } else {
                    cc_lblInventarioSacar.setText("0.00");
                    cc_lblNuevoInventario.setText("0.00");
                }
            }

        });

        objTablaCC.cbxUnidadMedida.addItemListener(
                (ItemEvent e) -> {
                    //este listener se activa cuando el item del cbxUnidadMedidaCC cambia
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        try {

                            int indiceFilaSeleccionada = cc_tableVentaCC.getSelectedRow();
                            if (indiceFilaSeleccionada == -1) { // cuando elo usuario seleciona de una vez el combo genera un error
                                return; // por eso esta este if para evitar ese error 
                            }
                            String cantText = (String) cc_tableVentaCC.getValueAt(indiceFilaSeleccionada, 3);
                            Double cantText_double = Double.valueOf(cantText);
                            restringirFacturacion(cantText_double, restringirFact[0], restringirFact[1]);

                        } catch (NumberFormatException error) {

                        }
                    }
                }
        );

        //Asignar al modelo de selección del JTable (jTable1 es este caso),
        //  un objeto de una clase heredada de ListSelectionListener. 
        tableVentaCF.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            //El método valueChange se debe sobreescribir obligatoriamente. 
            //Se ejecuta automáticamente cada vez que se hace una nueva selección. 
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Obtener el número de filas seleccionadas 
                int cuentaFilasSeleccionadas = tableVentaCF.getSelectedRowCount();
                if (cuentaFilasSeleccionadas == 1) {
                    //Sólo hay una fila seleccionada 
                    try {

                        buscarPrecioSinModificarTabla(tableVentaCF);
                        int indiceFilaSeleccionada = tableVentaCF.getSelectedRow();
                        String cantText = (String) tableVentaCF.getValueAt(indiceFilaSeleccionada, 3);
                        Double cantText_double = Double.valueOf(cantText);
                        restringirFacturacion(cantText_double, restringirFact[0], restringirFact[1]);
                    } catch (NumberFormatException error) {
                        //  la excepcion es capturada en buscarPrecioSinModificarTabla(); 
                    }

                } else {
                    lblcantUnidadMedida.setText("0.00");
                    lblInventarioActual.setText("0.00");
                }
            }

        });
        tabbedVentaCF.addChangeListener(new ChangeListener() {
            // This method is called whenever the selected tab changes
            public void stateChanged(ChangeEvent evt) {
                JTabbedPane pane = (JTabbedPane) evt.getSource();

                // este listener sirve para poner el focus en las cajas de texto de correlativo
                switch (tabbedVentaCF.getSelectedIndex()) {
                    case 0:
                        txtCorrelativoCF.requestFocus();
                        break;
                    case 1:
                        cc_txtCorrelativoCC.requestFocus();
                        break;
                    default:
                        adm_ycanio.setYear(current_date.getYear());
                        adm_cbxTipoDoc.setSelectedIndex(0);
                        Utilidades.limpiartabla(modeloventa);
                        listarVentas();
                        break;
                }
                onbteneMaxrCorrelativo();

            }
        });

        // lleno el modelo de la tablaventas para que no me de error 
        listarVentas();
        /*
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        **********************************************
        
         */
    }

    public void buscaPrecio(JTable latabla) {
        // el objetivo de este metodo es asignar el precio segun la unidad de medida 
        // seleccionada para el producto en cuestion 

        try {
            int linea = latabla.getSelectedRow();
            if (linea == -1) { // cuando elo usuario seleciona de una vez el combo genera un error
                return; // por eso esta este if para evitar ese error 
            }
            String idproducto = String.valueOf(latabla.getValueAt(linea, 1));// ERROR 
            Double preios_y_cantidad[] = objProductoDAO.cosultaPrecio(idproducto); // conexion al controlador 
            String UnidadMedida = (String) latabla.getValueAt(linea, 4);
            switch (UnidadMedida) {

                case "MASTER":
                    latabla.setValueAt(preios_y_cantidad[0], linea, 5);
                    break;
                case "UNIDAD":
                    latabla.setValueAt(preios_y_cantidad[1], linea, 5);
                    break;
                case "DOCENA":
                    latabla.setValueAt(preios_y_cantidad[2], linea, 5);
                    break;
                default:
                    latabla.setValueAt(preios_y_cantidad[3], linea, 5);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ha ocurrido un problema: Asignacion de precio  " + e, "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarPrecioSinModificarTabla(JTable latabla) {

        // EL OBJETIVO DE ESTE METODO ES OBTENER LOS DATOS NECESARIOS PARA INFORMAR AL USUARIO SOBRE EL INVENTARIO 
        // SIN CAMBIAR LOS VALORES DE LA TABLA 
        try {
            int linea = latabla.getSelectedRow();
            String idproducto = String.valueOf(latabla.getValueAt(linea, 1));
            Double preios_y_cantidad[] = objProductoDAO.cosultaPrecio(idproducto); // conexion al controlador 
            String UnidadMedida = String.valueOf(latabla.getValueAt(linea, 4));
            String cant = String.valueOf(latabla.getValueAt(linea, 3));
            Double cantText = Double.valueOf(cant);

            Double existencia = preios_y_cantidad[8];

            switch (UnidadMedida) {

                case "MASTER":
                    lblcantUnidadMedida.setText(preios_y_cantidad[4].toString());
                    cantUnidad = preios_y_cantidad[4];
                    break;
                case "UNIDAD":
                    lblcantUnidadMedida.setText(preios_y_cantidad[6].toString());
                    cantUnidad = preios_y_cantidad[5];
                    break;
                case "DOCENA":

                    lblcantUnidadMedida.setText(preios_y_cantidad[6].toString());
                    cantUnidad = preios_y_cantidad[6];
                    break;
                default:

                    lblcantUnidadMedida.setText(preios_y_cantidad[7].toString());
                    cantUnidad = preios_y_cantidad[7];
            }

            restringirFact[0] = cantUnidad;

            restringirFact[1] = existencia;

            // actualizar control de inventario 
            int row = latabla.getSelectedRow();

            if (tabbedVentaCF.getSelectedIndex() == 0) {
                CantidadCajaFactura.set(row, cantUnidad);
            } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                cc_CantidadCajaFactura.set(row, cantUnidad);
            }

        } catch (NumberFormatException e) {
            // JOptionPane.showMessageDialog(null, "ELIJA UN PRODUCTO REGISTRADO", "ADVERTENCIA !", JOptionPane.WARNING_MESSAGE);

        }

    }

    public String obtenerserie(int tipo) {
        //obtener numero de serie para factura de consumidor final si el tipo es 1 y si es otro valor para ccf
        SerieDAO objSerieDAO = new SerieDAO();

        String serieCF, serieCC;
        ArrayList<String> listaSerie = new ArrayList<>();
        listaSerie = objSerieDAO.obtenerseries();
        if (tipo == 1) {
            serieCF = listaSerie.get(0);
            return serieCF;
        } else {
            serieCC = listaSerie.get(1);
            return serieCC;
        }

    }

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

    public void buscarElCliente() {
        // es llamado desde un listener, la cantidad de veces que se repirte es alta 
        //llena la informacion del ususario seleccionado en el combo del cliente en ventaCF
        String nombre = (String) cbxNombreCF.getSelectedItem();
        String[] cliente = objClienteCFDAO.VerElcliente(nombre);//conexion al controlador 
        if (null == cliente[0]) {
            Utilidades.clienteRegistrado = false; // SINO ENCUENTRA REGISTROS EL CLIENTE NO ESTA REGISTRADO 
        }
        txtDireccionCF.setText(cliente[1]);
        txtDuiCF.setText(cliente[0]);
        if (txtDuiCF.getText().isBlank()) {
            txtDuiCF.setText(cliente[2]); // si no hay dui intentra agregar el nit (esto sirve para las empreseas)
        }
        if (cliente[0] != null) {
            // como este metodo se ejecuta cada vez que se detecta un caracter en el text field si no hay registros que coincidad
            // los datos seran null al igual que si no se encuentra registrado un nombre completo 
            if (cliente[0].isBlank()) {
                Utilidades.clienteRegistradoC = false; // indicamos que se ha hecho una asignacion del nit 
            } else {
                Utilidades.clienteRegistradoC = true;
            }
        }
    }

    public void seleccionarCliente(String nombre) {
        //establece el valor del parametro como item seleccionado 
        cbxNombreCF.setSelectedItem(nombre);
    }

    public void restringirFacturacion(Double cantText, Double cantUnidad, Double existencia) {
//calcula la cantidad a sacar y el nuevo inventario 

        try {
            Double cantidadSacar;
            Double nuevoInventario;

            cantidadSacar = cantText * cantUnidad;
            nuevoInventario = existencia - cantidadSacar;

            if (tabbedVentaCF.getSelectedIndex() == 0) {
                lblcantUnidadMedida.setText(cantidadSacar.toString());
                lblInventarioActual.setText(nuevoInventario.toString());
            } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                cc_lblInventarioSacar.setText(cantidadSacar.toString());
                cc_lblNuevoInventario.setText(nuevoInventario.toString());
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "TENEMOS UN PROBLEMA  " + e, "ERROR", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void TotalPagar() {
        // calcula el total a pagar en venta final 
        double Totalpagar = 0.00;
        int numFila = tableVentaCF.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tableVentaCF.getModel().getValueAt(i, 6)));
            Totalpagar += cal;
        }
        lblTotalCF.setText(String.format("%.2f", Totalpagar));
    }

    private void TotalPagarCC() {
        // calcula el total a pagar en venta cc 
        double sumas = 0.00;
        double iva = 0.00;
        double Totalpagar = 0.00;

        if (!(lis_totalesProd.isEmpty())) {

            for (Object object : lis_totalesProd) {
                sumas += Double.parseDouble(String.valueOf(object));
                iva = sumas * 0.13;
                Totalpagar = iva + sumas;
            }
            cc_lblSumas.setText(String.format("%.2f", sumas));
            cc_lblIva.setText(String.format("%.2f", iva));
            cc_lbltotal.setText(String.format("%.2f", Totalpagar));

        } else {
            cc_lblSumas.setText("0.00");
            cc_lblIva.setText("0.00");
            cc_lbltotal.setText("0.00");

        }

    }

    public void agregarProdCF() {
        Double precioC;
        Double totalProd;

        try {
            precioC = Double.valueOf(arr_producto[2]);

            totalProd = precioC;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "LOS DATOS DE PRECIO Y/O CANTIDAD NO SON NUMEROS   "
                    + "\n ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

            return;
        }
        String cantCajas[];
        cantCajas = objProductoDAO.todoElproductoporCodigo(arr_producto[0]);

        ArrayList lista = new ArrayList();
        lista.add(item);// añade el item
        lista.add(arr_producto[0]); // añade el codigo 
        lista.add(arr_producto[1]); // añade la descripcion 
        lista.add("1.00"); // añade la cantidad 
        lista.add("MASTER"); // establece CAJA como unidad de medida  
        CantidadCajaFactura.add(item, Double.parseDouble(cantCajas[6]));
        lista.add(String.format("%.2f", precioC));
        lista.add(String.format("%.2f", totalProd));
        Object[] Obj = new Object[7];
        Obj[0] = lista.get(0);
        Obj[1] = lista.get(1);
        Obj[2] = lista.get(2);
        Obj[3] = lista.get(3);
        Obj[4] = lista.get(4);
        Obj[5] = lista.get(5);
        Obj[6] = lista.get(6);
        datos = Obj;
        objtabla.visualizar(tableVentaCF, datos);
        TotalPagar();
        btnSeleccionarproductoCF.requestFocus();
        item++; //aumenta el contador de item 
    }

    public void agregarProdCC() {
        // para facturacion ccf 

        Double precioC;
        Double totalProd;

        try {
            precioC = Double.valueOf(arr_producto[2]);

            totalProd = precioC / 1.13;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "LOS DATOS DE PRECIO Y/O CANTIDAD NO SON NUMEROS   "
                    + "\n ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

            return;
        }
        String cantCajas[];
        cantCajas = objProductoDAO.todoElproductoporCodigo(arr_producto[0]);

        ArrayList lista = new ArrayList();
        lista.add(cc_item);// añade el item
        lista.add(arr_producto[0]); // añade el codigo 
        lista.add(arr_producto[1]); // añade la descripcion 
        lista.add("1.00"); // añade la cantidad 
        lista.add("MASTER"); // establece CAJA como unidad de medida  
        cc_CantidadCajaFactura.add(cc_item, Double.parseDouble(cantCajas[6]));
        // agrego el precio al array 
        //  lis_preciosProd.add(precioC / 1.13);
        lista.add(String.format("%.2f", precioC));
        // agrego al array el totalprod 
        lis_totalesProd.add(totalProd);
        lista.add(String.format("%.2f", totalProd));
        Object[] Obj = new Object[7];
        Obj[0] = lista.get(0);
        Obj[1] = lista.get(1);
        Obj[2] = lista.get(2);
        Obj[3] = lista.get(3);
        Obj[4] = lista.get(4);
        Obj[5] = lista.get(5);
        Obj[6] = lista.get(6);
        datos = Obj;
        objTablaCC.visualizar(cc_tableVentaCC, datos);

        TotalPagarCC();
        btnSeleccionarproductoCF.requestFocus();
        cc_item++; //aumenta el contador de item 

    }

    public void eliminarItem(JTable laTabla) {
        // elimina un item de la tabla que recibe como parametro
        try {
            int selectedRow = laTabla.getSelectedRow();
            // ELIMINA LA LINEA 
            ((DefaultTableModel) laTabla.getModel()).removeRow(selectedRow);

            TotalPagar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO LA LINEA SELECCIONADA ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void eliminarItemCC(JTable laTabla) {
        // elimina un item de la tabla que recibe como parametro
        try {
            int selectedRow = laTabla.getSelectedRow();
            lis_totalesProd.remove(selectedRow);
            //   lis_preciosProd.remove(selectedRow);
            // ELIMINA LA LINEA 
            ((DefaultTableModel) laTabla.getModel()).removeRow(selectedRow);
            TotalPagarCC();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO LA LINEA SELECCIONADA ", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }

    }

//    public void limpiartabla(DefaultTableModel p_modelo) {
//        //  limpia el modelo que recibe como parametro 
//        for (int i = 0; i < p_modelo.getRowCount(); i++) {
//            p_modelo.removeRow(i);
//            i--;
//        }
//    }

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

    public void limpiarFactura() {

        Utilidades.limpiartabla(objtabla.dt1);
        lblcantUnidadMedida.setText("0.00");
        lblInventarioActual.setText("0.00");
        objfrmSeleccionarProducto.nombre = "";
        txtbuscaProdCF.setText("");
        lblTotalCF.setText("0.00");
        // txtCorrelativoCF.setText("");
        cbxNombreCF.setSelectedItem(" ");

        item = 0;

    }

    public void limpiarFacturaCC() {
        // limpia o deja en un estado predeterminado los componentes de facturacion ccf 
        objfrmSeleccionarClienteCC.noRegistro = "";
        Utilidades.limpiartabla(objTablaCC.dt1);
        cc_txtNombre.setText("");
        cc_txtDui.setText("");
        cc_txtNombreNegocio.setText("");
        cc_txtDireccion.setText("");
        cc_txtNoRegistro.setText("");
        cc_txtNit.setText("");
        cc_txtGiro.setText("");
        cc_txtbuscaProd.setText("");
        cc_lblInventarioSacar.setText("0.00");
        cc_lblNuevoInventario.setText("0.00");
        //  cc_txtCorrelativoCC.setText("");
        cc_lblSumas.setText("0.00");
        cc_lblIva.setText("0.00");
        cc_lbltotal.setText("0.00");
        cc_item = 0;
        cbxCondicionPago.setSelectedIndex(0);
        // limpiar los arrayList 
        // lis_preciosProd.clear();
        lis_totalesProd.clear();

    }

    public void SeleccionarProductoporCodigo(String idproducto) {
        //Consulta la infop del arr_producto 
        arr_producto = objProductoDAO.todoElproductoporCodigo(idproducto);
        if (tabbedVentaCF.getSelectedIndex() == 0) {
            agregarProdCF();
        } else if (tabbedVentaCF.getSelectedIndex() == 1) {
            agregarProdCC(); // agrega los productos a la facturacion de ccf
        }
        objfrmSeleccionarProducto.idproducto = " "; // borro el codigo para que no este agregando la misma info cada vez que este activo el form  
    }

    public void SeleccionarCliente_noRegisro(String noRegistro) {
        //Consulta la infop del cliente 
        arr_cliente_cc = objClienteCCDAO.VerElclienteCC(noRegistro);

        cc_txtNombre.setText(arr_cliente_cc[1]);
        cc_txtDui.setText(arr_cliente_cc[2]);
        cc_txtNombreNegocio.setText(arr_cliente_cc[4]);
        cc_txtNoRegistro.setText(arr_cliente_cc[0]);
        cc_txtDireccion.setText(arr_cliente_cc[5]);
        cc_txtNit.setText(arr_cliente_cc[3]);
        cc_txtGiro.setText(arr_cliente_cc[6]);

        objfrmSeleccionarClienteCC.noRegistro = " "; // borro el codigo para que no este agregando la misma info cada vez que este activo el form  
    }

    public boolean esNumerico(String cadena) {
        boolean isNum = cadena.matches("[0-9]+[\\.]?[0-9]*");
        return isNum;
    }

    public boolean validacampos() {
        String nombre, dui;
        dui = txtDuiCF.getText().trim();
        nombre = (String) cbxNombreCF.getSelectedItem();

        if (dui.isBlank() || nombre.isBlank()) {

            JOptionPane.showMessageDialog(null, "Para facturas mayores a 220.00!!"
                    + "\nSolicite al cliente nombre completo y Numero de DUI", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean RegistrarVenta() {

        try {

            String serie, fechaActual, dui = "", registro;
            int idtipoDoc;
            int noDocumeto;
            serie = txtSerieCF.getText().trim();
            noDocumeto = Integer.parseInt(txtCorrelativoCF.getText().trim());
            idtipoDoc = 2; // esto nos indica que es factura de consumidor final segun la base de datos 
            if (Utilidades.clienteRegistrado) {
                dui = txtDuiCF.getText();
                if (!Utilidades.clienteRegistradoC) {
                    Utilidades.clienteRegistrado = false; // lo mando al no registrado para que capture los datos sin que de error 
                    dui = " ";// si el cliente esta registrado con el nit
                }
            } else {

                dui = txtDuiCF.getText();
            }
            registro = " ";
            Double total = 0.00;
            Double iva = 0.00;

            String nombre_no_reg = (String) cbxNombreCF.getSelectedItem();
            String dui_no_reg = txtDuiCF.getText();

            total = Double.valueOf(lblTotalCF.getText());

            if (Utilidades.clienteRegistrado) {
                // si el cliente esta registrado 
                objVenta.setSerie(serie);
                objVenta.setNoDocumento(noDocumeto);
                objVenta.setDui(dui);
                objVenta.setNoRegistro(registro);
                objVenta.setIdusuario(Utilidades.idusuario);
                objVenta.setSuma(total);
                objVenta.setIva(iva);
                objVenta.setTotal(total);
                objVenta.setIdtipodoc(idtipoDoc);
            } else {
                // si el cliente no esta registrado 

                objVenta.setSerie(serie);
                objVenta.setNoDocumento(noDocumeto);
                objVenta.setDui(" ");
                objVenta.setNoRegistro(registro);
                objVenta.setIdusuario(Utilidades.idusuario);
                objVenta.setSuma(total);
                objVenta.setIva(iva);
                objVenta.setTotal(total);
                objVenta.setIdtipodoc(idtipoDoc);
                objVenta.setNombre_no_reg(nombre_no_reg);
                objVenta.setDui_no_reg(dui_no_reg);

                Utilidades.clienteRegistrado = true; // cambio la variable despues de usarla 
                Utilidades.clienteRegistradoC = true; // cambio la variable despues de usarla 
            }
            return objVentaDAO.RegistrarVenta(objVenta);//conexion a base de datos 

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar" + e, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public boolean validacamposCC() {
        String serie, correlativo, fechaActual, dui, registro;
        int nlinea;
        String el_id_prod, cant, precioU;
        double totalProd;
        serie = cc_txtSerieCC.getText().trim();
        correlativo = cc_txtCorrelativoCC.getText().trim();
        registro = cc_txtNoRegistro.getText().trim();

        if (serie.isEmpty() || correlativo.isEmpty() || registro.isEmpty()) {

            JOptionPane.showMessageDialog(null, "por favor, rellene todos los campos!!"
                    + "\nSeleccione un cliente y verifique que hayan datos en los campos serie y correlativo", "Hay campos vacios", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean RegistrarVentaCC() {
        // Registra la venta de un ccf
        String serie, fechaActual, dui, registro;
        serie = cc_txtSerieCC.getText().trim();
        int tipoDoc = 1; // 1 es el id de comprobamnte de credito fiscal segun la base de datos 
        int noDocumento = Integer.parseInt(cc_txtCorrelativoCC.getText().trim());
        Double total = Double.parseDouble(cc_lbltotal.getText());
        Double iva = Double.parseDouble(cc_lblIva.getText());
        Double suma = Double.parseDouble(cc_lblSumas.getText());
        registro = cc_txtNoRegistro.getText();
        int idcondicion = cbxCondicionPago.getSelectedIndex() + 1;
        int idusuario = Utilidades.idusuario;
        objVenta.setSerie(serie);
        objVenta.setDui(" ");
        objVenta.setNoRegistro(registro);
        objVenta.setIdcondicionPago(idcondicion);
        objVenta.setIdusuario(idusuario);
        objVenta.setNoDocumento(noDocumento);
        objVenta.setTotal(total);
        objVenta.setIdtipodoc(tipoDoc);
        objVenta.setIva(iva);
        objVenta.setSuma(suma);

        return objVentaDAO.RegistrarVenta(objVenta);
    }

    private boolean RegistrarDetalle(JTable ptabla) {
        // inserta el detalle para factura y ccf 
        boolean insertando = true;
        try {
            int rowCant = ptabla.getRowCount();
            if (0 == rowCant) {
                return true;
                // si la tabla detalle esta vacia no inserta pero devuelve true 
            }
            for (int i = 0; i < rowCant; i++) {

                String serie = "", correlativo = "";

                if (tabbedVentaCF.getSelectedIndex() == 0) {
                    serie = txtSerieCF.getText().trim();
                    correlativo = txtCorrelativoCF.getText();
                } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                    serie = cc_txtSerieCC.getText().trim();
                    correlativo = cc_txtCorrelativoCC.getText();
                }

                int nlinea = i;
                String idproducto = (ptabla.getValueAt(i, 1).toString());
                Double cant = Double.parseDouble(ptabla.getValueAt(i, 3).toString());
                double precioU = 0.00;
                if (tabbedVentaCF.getSelectedIndex() == 0) {
                    precioU = Double.parseDouble(ptabla.getValueAt(i, 5).toString());
                } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                    // precioU = Double.parseDouble(String.valueOf(lis_preciosProd.get(i)));
                    precioU = Double.parseDouble(ptabla.getValueAt(i, 5).toString());
                    precioU /= 1.13;
                }
                String unidadmedida = (ptabla.getValueAt(i, 4).toString().trim());
                unidadmedida = unidadmedida.length() < 5 ? unidadmedida : unidadmedida.substring(0, 5);

                double totalProd = Double.valueOf(ptabla.getValueAt(i, 6).toString());

                //llenamos el objeto 
                objDetalle.setSerie(serie);
                objDetalle.setNoDocumento(Integer.parseInt(correlativo));
                objDetalle.setNlinea(nlinea);
                objDetalle.setIdproducto(idproducto);
                if (tabbedVentaCF.getSelectedIndex() == 0) {
                    objDetalle.setCantidad(cant * CantidadCajaFactura.get(i));//GUARDA LO QUE SE DESCONTA DE EXISTENCIA
                } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                    objDetalle.setCantidad(cant * cc_CantidadCajaFactura.get(i));
                }
                objDetalle.setCantidadimpresa(cant);
                objDetalle.setUnidadmedida(unidadmedida);
                objDetalle.setPrecioU(precioU);
                objDetalle.setTotalProd(totalProd);
                // conexion con el controlador 
                insertando = objDetalleDAO.RegistrarDetalle(objDetalle);
            }
            return insertando;
        } catch (NumberFormatException e) {
            System.out.println(" " + e);
            return false;
        }
    }

    private void cambioPropiedad(JTable p_tabla) {
        // este metodo se ejecuta desde el evento propperty change de la tabla venta FACT
        // ACA SE MULTIPLICAN CANTIDAD Y PRECIO UNITARIO PARA OBTENER EL TOTAL POR ITEM 
        // TABLAFACTURA 
        try {

            int filaEditada = p_tabla.getEditingRow();
            int column = p_tabla.getSelectedColumn();
            if (column == 4) {
                buscaPrecio(p_tabla);
            }

            if ((p_tabla.isCellSelected(filaEditada, 3) || (p_tabla.isCellSelected(filaEditada, 5)))) {

                int columnaEditada = p_tabla.getEditingColumn();
                String codigo = (String) p_tabla.getValueAt(filaEditada, 1);
                String cant = p_tabla.getValueAt(filaEditada, 3).toString();
                if (!(esNumerico(cant))) {
                    //validar si es numero sino interrumpir la ejecucion y resetear la cantidad 
                    p_tabla.setValueAt("1.00", filaEditada, 3);
                    JOptionPane.showMessageDialog(null, "INGRESE NUMEROS SOLAMENTE", "DATOS INVALIDOS", JOptionPane.WARNING_MESSAGE);

                    return;
                }
                String precioU = p_tabla.getValueAt(filaEditada, 5).toString();
                if (!(esNumerico(precioU))) {
                    //validar si es numero sino interrumpir la ejecucion y resetear el precio
                    buscaPrecio(p_tabla);
                    JOptionPane.showMessageDialog(null, "INGRESE NUMEROS SOLAMENTE", "DATOS INVALIDOS", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (tabbedVentaCF.getSelectedIndex() == 0) {

                    Double total = (Double.valueOf(cant) * Double.valueOf(precioU));
                    String total_string = String.format("%.2f", total);
                    p_tabla.setValueAt(total_string, filaEditada, 6);
                    TotalPagar();
                } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                    Double total = (Double.valueOf(cant) * Double.valueOf(precioU)) / 1.13;
                    String total_string = String.format("%.2f", total);
                    p_tabla.setValueAt(total_string, filaEditada, 6);
                    lis_totalesProd.set(filaEditada, total);
                    TotalPagarCC();
                }

                int cuentaFilasSeleccionadas = p_tabla.getSelectedRowCount();
                if (cuentaFilasSeleccionadas == 1) {
                    //Sólo hay una fila seleccionada 
                    try {

                        buscarPrecioSinModificarTabla(p_tabla);
                        int indiceFilaSeleccionada = p_tabla.getSelectedRow();
                        String cantText = (String) p_tabla.getValueAt(indiceFilaSeleccionada, 3);
                        Double cantText_double = Double.valueOf(cantText);
                        restringirFacturacion(cantText_double, restringirFact[0], restringirFact[1]);
                        Double nuevoInventario = 0.00;
                        if (tabbedVentaCF.getSelectedIndex() == 0) {
                            nuevoInventario = Double.parseDouble(lblInventarioActual.getText());
                        } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                            nuevoInventario = Double.parseDouble(cc_lblNuevoInventario.getText());
                        }
                        if (nuevoInventario < 0) {
                            //mensaje de advertencia 
                            JOptionPane.showMessageDialog(null, "NO DISPONE DE EXISTENCIAS", "ERROR", JOptionPane.WARNING_MESSAGE);

                            if (tabbedVentaCF.getSelectedIndex() == 0) {
                                tableVentaCF.setValueAt("1", filaEditada, 3);
                            } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                                cc_tableVentaCC.setValueAt("1", filaEditada, 3);
                            }

                            // asigna uno a la cantidad a facturar para que si se va a quedar en negativo por lo menos no sea por mucho
                            restringirFacturacion(1.00, restringirFact[0], restringirFact[1]);

                        }
                    } catch (NumberFormatException error) {
                        JOptionPane.showMessageDialog(null, " " + error);
                    }

                } else {
                    if (tabbedVentaCF.getSelectedIndex() == 0) {
                        lblcantUnidadMedida.setText("0.00");
                        lblInventarioActual.setText("0.00");
                    } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                        cc_lblInventarioSacar.setText("0.00");
                        cc_lblNuevoInventario.setText("0.00");
                    }

                }

            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR INESPERADO: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void onbteneMaxrCorrelativo() {
        //actualiza el correlativo para la siguiente factura y/o ccf 

        try {
            int correlativo = 0;
            if (tabbedVentaCF.getSelectedIndex() == 0) {
                correlativo = objVentaDAO.obtenerMaxCorrelativo(txtSerieCF.getText().trim());
                lblUltimoRegistro.setText("" + correlativo);
                correlativo += 1;
                txtCorrelativoCF.setText("" + correlativo);
            } else if (tabbedVentaCF.getSelectedIndex() == 1) {
                correlativo = objVentaDAO.obtenerMaxCorrelativo(cc_txtSerieCC.getText().trim());
                lblUltimoRegistroCC.setText("" + correlativo);
                correlativo += 1;
                cc_txtCorrelativoCC.setText("" + correlativo);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Prpblemas con el correlativo: " + e, "HA OCURRIDO UN ERROR ", JOptionPane.ERROR_MESSAGE);
        }

    }

    // metodos para administrar las ventas 
    public void listarVentas() {

        int anio = adm_ycanio.getYear();
        int tipoDoc = adm_cbxTipoDoc.getSelectedIndex();

        List<AdmVenta> listarVentas = objAdmVentaDAO.listarVentas(anio, tipoDoc);
        modeloventa = (DefaultTableModel) adm_TableVenta.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarVentas.size(); i++) {

            ob[0] = listarVentas.get(i).getFecha();
            ob[1] = listarVentas.get(i).getTipoDoc();
            ob[2] = listarVentas.get(i).getSerie();
            ob[3] = listarVentas.get(i).getCorr();
            ob[4] = listarVentas.get(i).getCliente();
            ob[5] = listarVentas.get(i).getTotal();
            modeloventa.addRow(ob);
        }
        adm_TableVenta.setModel(modeloventa);
    }

    public void listarVentas_avanzado() {

        int noDocumento;
        String fecha = "null";
        if (!esNumerico(adm_txtCorr.getText())) {
            noDocumento = 0;
        } else {
            noDocumento = Integer.valueOf(adm_txtCorr.getText());
        }

        String Cliente = adm_txtCliente.getText();
        if (Cliente.isBlank()) {
            Cliente = "null";
        }
        if (null != adm_dateChooser.getDate()) {
            int year = adm_dateChooser.getCalendar().get(Calendar.YEAR);
            int month = adm_dateChooser.getCalendar().get(Calendar.MARCH);
            int day = adm_dateChooser.getCalendar().get(Calendar.DAY_OF_MONTH);
            fecha = "" + year + "-" + (month + 1) + "-" + day;

        }
        if ((noDocumento == 0) && (Cliente == "null") && (fecha == "null")) {
            JOptionPane.showMessageDialog(null, "LLENE LOS PARAMETROS ANTES DE FILTRAR ",
                    "LA BUSQUEDA NO ES VALIDA ", JOptionPane.WARNING_MESSAGE);
            // si el usuario no peoporciona ningun parametro entonces se llena la tabla con
            //valores del filtro no avanzsado por default 
            adm_ycanio.setYear(current_date.getYear());
            adm_cbxTipoDoc.setSelectedIndex(0);
            listarVentas();
            return;

        }

        List<AdmVenta> listarVentas = objAdmVentaDAO.listarVentas_avanzado(noDocumento, Cliente, fecha);
        modeloventa = (DefaultTableModel) adm_TableVenta.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarVentas.size(); i++) {

            ob[0] = listarVentas.get(i).getFecha();
            ob[1] = listarVentas.get(i).getTipoDoc();
            ob[2] = listarVentas.get(i).getSerie();
            ob[3] = listarVentas.get(i).getCorr();
            ob[4] = listarVentas.get(i).getCliente();
            ob[5] = listarVentas.get(i).getTotal();
            modeloventa.addRow(ob);
        }
        adm_TableVenta.setModel(modeloventa);
    }

    public String firstNChars(String str, int n) {
        if (str == null) {
            return null;
        }
        return str.substring(0, Math.min(str.length(), n));
    }
    public static void recargar(){
     // hacer la busqueda de nuevo 
    }

    /*
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
    *****************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedVentaCF = new javax.swing.JTabbedPane();
        ventaCF = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDireccionCF = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtCorrelativoCF = new javax.swing.JTextField();
        txtSerieCF = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbxNombreCF = new javax.swing.JComboBox<>();
        lblFechaCF = new javax.swing.JLabel();
        txtDuiCF = new javax.swing.JTextField();
        btnSeleccionarClienteCF = new javax.swing.JButton();
        btnSeleccionarproductoCF = new javax.swing.JButton();
        lblcantUnidadMedida = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblInventarioActual = new javax.swing.JLabel();
        btnQuitarLinea = new javax.swing.JButton();
        btnGuardarCF = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblTotalCF = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtbuscaProdCF = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableVentaCF = new javax.swing.JTable();
        btnDeshacer = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblUltimoRegistro = new javax.swing.JLabel();
        ventaCC = new javax.swing.JPanel();
        cc_txtNombre = new javax.swing.JTextField();
        cc_txtDui = new javax.swing.JTextField();
        cc_txtNombreNegocio = new javax.swing.JTextField();
        cc_txtDireccion = new javax.swing.JTextField();
        cc_txtNoRegistro = new javax.swing.JTextField();
        cc_txtNit = new javax.swing.JTextField();
        cc_txtGiro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cc_txtbuscaProd = new javax.swing.JTextField();
        cc_btnSeleccionarproducto = new javax.swing.JButton();
        cc_btnSeleccionarCliente = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        cc_lblFechaCC = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cc_txtCorrelativoCC = new javax.swing.JTextField();
        cc_txtSerieCC = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cc_lblInventarioSacar = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cc_lblNuevoInventario = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        cc_tableVentaCC = new javax.swing.JTable();
        cc_btnQuitarLinea = new javax.swing.JButton();
        cc_btnDeshacer = new javax.swing.JButton();
        cc_btnGuardarImprimir = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        cc_lblSumas = new javax.swing.JLabel();
        cc_lblIva = new javax.swing.JLabel();
        cc_lbltotal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lblUltimoRegistroCC = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cbxCondicionPago = new javax.swing.JComboBox<>();
        admVenta = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        adm_TableVenta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        adm_generarReporte = new javax.swing.JButton();
        adm_TipoRepo = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        adm_ycanio = new com.toedter.calendar.JYearChooser();
        jLabel32 = new javax.swing.JLabel();
        adm_cbxTipoDoc = new javax.swing.JComboBox<>();
        adm_btnFiltrarBD = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        adm_btnQuitarBD = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        adm_txtCorr = new javax.swing.JTextField();
        adm_txtCliente = new javax.swing.JTextField();
        adm_btnFiltrarT = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        adm_dateChooser = new com.toedter.calendar.JDateChooser();
        adm_btnExcel = new javax.swing.JButton();
        adm_btnDetalle = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VENTAS");
        setBackground(new java.awt.Color(255, 255, 255));
        setName(""); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        ventaCF.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("CLIENTE");

        jLabel9.setText("DIRECCION");

        jLabel10.setText("FECHA ");

        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("CORRELATIVO");

        jLabel7.setText("SERIE");

        jLabel51.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 0, 0));
        jLabel51.setText("FACTURA DE CONSUMIDOR FINAL ");

        txtCorrelativoCF.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtCorrelativoCF.setForeground(new java.awt.Color(255, 0, 0));

        txtSerieCF.setEditable(false);

        jLabel12.setText("DUI");

        cbxNombreCF.setBackground(new java.awt.Color(255, 204, 204));
        cbxNombreCF.setEditable(true);

        lblFechaCF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFechaCF.setText("-----");

        btnSeleccionarClienteCF.setText("CLIENTES");
        btnSeleccionarClienteCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarClienteCFActionPerformed(evt);
            }
        });

        btnSeleccionarproductoCF.setText("PRODUCTOS");
        btnSeleccionarproductoCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarproductoCFActionPerformed(evt);
            }
        });
        btnSeleccionarproductoCF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSeleccionarproductoCFKeyPressed(evt);
            }
        });

        lblcantUnidadMedida.setText("0.00");

        jLabel6.setText("SE DESCONTARÁ DE INVENTARIO:");

        jLabel13.setText("EL INVENTARIO SERÁ: ");

        lblInventarioActual.setText("0.00");

        btnQuitarLinea.setText("QUITAR LINEA ");
        btnQuitarLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarLineaActionPerformed(evt);
            }
        });

        btnGuardarCF.setText("GUARDAR E IMPRIMIR");
        btnGuardarCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCFActionPerformed(evt);
            }
        });

        jLabel14.setText("TOTAL");

        lblTotalCF.setText("0.00");

        jLabel15.setText("SELECCIONE PRODUCTO: ");
        jLabel15.setToolTipText("CODIGO DE PRODUCTO");

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
        tableVentaCF.setGridColor(new java.awt.Color(255, 255, 255));
        tableVentaCF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tableVentaCFPropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(tableVentaCF);

        btnDeshacer.setText("DESHACER");
        btnDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshacerActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));

        jLabel23.setText("ULTIMO REGISTRO: ");

        lblUltimoRegistro.setText("------");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(lblUltimoRegistro)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(lblUltimoRegistro))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ventaCFLayout = new javax.swing.GroupLayout(ventaCF);
        ventaCF.setLayout(ventaCFLayout);
        ventaCFLayout.setHorizontalGroup(
            ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventaCFLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ventaCFLayout.createSequentialGroup()
                        .addComponent(btnQuitarLinea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeshacer)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarCF)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel14)
                        .addGap(37, 37, 37)
                        .addComponent(lblTotalCF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119))
                    .addGroup(ventaCFLayout.createSequentialGroup()
                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ventaCFLayout.createSequentialGroup()
                                .addGap(333, 333, 333)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(lblcantUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblInventarioActual, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ventaCFLayout.createSequentialGroup()
                                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbxNombreCF, javax.swing.GroupLayout.Alignment.LEADING, 0, 400, Short.MAX_VALUE)
                                    .addComponent(txtDuiCF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                    .addComponent(txtDireccionCF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                    .addGroup(ventaCFLayout.createSequentialGroup()
                                        .addComponent(txtbuscaProdCF)
                                        .addGap(295, 295, 295)))
                                .addGap(18, 18, 18)
                                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ventaCFLayout.createSequentialGroup()
                                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(ventaCFLayout.createSequentialGroup()
                                                .addComponent(btnSeleccionarClienteCF)
                                                .addGap(109, 109, 109))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventaCFLayout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblFechaCF)
                                                .addGap(18, 18, 18)))
                                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)))
                                    .addComponent(btnSeleccionarproductoCF))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSerieCF, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                    .addComponent(txtCorrelativoCF, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(ventaCFLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1076, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventaCFLayout.createSequentialGroup()
                .addContainerGap(527, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addGap(487, 487, 487))
        );
        ventaCFLayout.setVerticalGroup(
            ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventaCFLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel51)
                .addGap(25, 25, 25)
                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ventaCFLayout.createSequentialGroup()
                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxNombreCF, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccionCF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(btnSeleccionarClienteCF))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDuiCF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(15, 15, 15)
                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSeleccionarproductoCF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(txtbuscaProdCF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(ventaCFLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSerieCF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaCF)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtCorrelativoCF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100)
                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcantUnidadMedida)
                    .addComponent(jLabel6)
                    .addComponent(jLabel13)
                    .addComponent(lblInventarioActual))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ventaCFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitarLinea)
                    .addComponent(btnGuardarCF)
                    .addComponent(jLabel14)
                    .addComponent(lblTotalCF)
                    .addComponent(btnDeshacer))
                .addGap(0, 575, Short.MAX_VALUE))
        );

        tabbedVentaCF.addTab("FACTURA DE CONSUMIDOR FINAL ", ventaCF);

        ventaCC.setBackground(new java.awt.Color(255, 255, 255));

        cc_txtNombre.setEditable(false);
        cc_txtNombre.setBackground(new java.awt.Color(255, 255, 255));

        cc_txtDui.setEditable(false);
        cc_txtDui.setBackground(new java.awt.Color(255, 255, 255));

        cc_txtNombreNegocio.setEditable(false);
        cc_txtNombreNegocio.setBackground(new java.awt.Color(255, 255, 255));

        cc_txtDireccion.setEditable(false);
        cc_txtDireccion.setBackground(new java.awt.Color(255, 255, 255));

        cc_txtNoRegistro.setEditable(false);
        cc_txtNoRegistro.setBackground(new java.awt.Color(255, 255, 255));

        cc_txtNit.setEditable(false);
        cc_txtNit.setBackground(new java.awt.Color(255, 255, 255));

        cc_txtGiro.setEditable(false);
        cc_txtGiro.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("NOMBRE");

        jLabel2.setText("GIRO");

        jLabel3.setText("REGISTRO FISCAL");

        jLabel4.setText("DIRECCION");

        jLabel5.setText("NOMBRE DE NEGOCIO ");

        jLabel16.setText("DUI");

        jLabel17.setText("NIT");

        jLabel18.setText("SELECCIONE PRODUCTO: ");
        jLabel18.setToolTipText("CODIGO DE PRODUCTO");

        cc_btnSeleccionarproducto.setText("PRODUCTOS");
        cc_btnSeleccionarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cc_btnSeleccionarproductoActionPerformed(evt);
            }
        });
        cc_btnSeleccionarproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cc_btnSeleccionarproductoKeyPressed(evt);
            }
        });

        cc_btnSeleccionarCliente.setText("CLIENTES");
        cc_btnSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cc_btnSeleccionarClienteActionPerformed(evt);
            }
        });

        jLabel19.setText("FECHA ");

        cc_lblFechaCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cc_lblFechaCC.setText("-----");

        jLabel20.setForeground(new java.awt.Color(255, 0, 0));
        jLabel20.setText("CORRELATIVO");

        jLabel21.setText("SERIE");

        cc_txtCorrelativoCC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cc_txtCorrelativoCC.setForeground(new java.awt.Color(255, 0, 0));

        cc_txtSerieCC.setEditable(false);

        jLabel52.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 0, 0));
        jLabel52.setText("COMPROBANTE DE CREDITO FISCAL");

        jLabel22.setText("SE DESCONTARÁ DE INVENTARIO: ");

        cc_lblInventarioSacar.setText("0.00");

        jLabel24.setText("EL INVENTARIO SERÁ: ");

        cc_lblNuevoInventario.setText("0.00");

        cc_tableVentaCC.setForeground(new java.awt.Color(51, 102, 255));
        cc_tableVentaCC.setModel(new javax.swing.table.DefaultTableModel(
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
        cc_tableVentaCC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cc_tableVentaCC.setGridColor(new java.awt.Color(255, 255, 255));
        cc_tableVentaCC.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cc_tableVentaCCPropertyChange(evt);
            }
        });
        jScrollPane4.setViewportView(cc_tableVentaCC);

        cc_btnQuitarLinea.setText("QUITAR LINEA ");
        cc_btnQuitarLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cc_btnQuitarLineaActionPerformed(evt);
            }
        });

        cc_btnDeshacer.setText("DESHACER ");
        cc_btnDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cc_btnDeshacerActionPerformed(evt);
            }
        });

        cc_btnGuardarImprimir.setText("GUARDAR E IMPRIMIR");
        cc_btnGuardarImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cc_btnGuardarImprimirActionPerformed(evt);
            }
        });

        jLabel26.setText("TOTAL");

        jLabel27.setText("IVA ");

        jLabel28.setText("SUMAS");

        cc_lblSumas.setText("0.00");

        cc_lblIva.setText("0.00");

        cc_lbltotal.setText("0.00");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));

        jLabel25.setText("ULTIMO REGISTRO: ");

        lblUltimoRegistroCC.setText("------");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(lblUltimoRegistroCC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lblUltimoRegistroCC))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel29.setText("CONDICION DE PAGO: ");

        javax.swing.GroupLayout ventaCCLayout = new javax.swing.GroupLayout(ventaCC);
        ventaCC.setLayout(ventaCCLayout);
        ventaCCLayout.setHorizontalGroup(
            ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventaCCLayout.createSequentialGroup()
                .addGap(525, 525, 525)
                .addComponent(jLabel52)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventaCCLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cc_btnQuitarLinea)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cc_btnDeshacer)
                .addGap(18, 18, 18)
                .addComponent(cc_btnGuardarImprimir)
                .addGap(32, 32, 32)
                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ventaCCLayout.createSequentialGroup()
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26))
                        .addGap(22, 22, 22)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cc_lblIva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cc_lbltotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(ventaCCLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(cc_lblSumas, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(186, 186, 186))
            .addGroup(ventaCCLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ventaCCLayout.createSequentialGroup()
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ventaCCLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 579, Short.MAX_VALUE)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cc_lblInventarioSacar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(cc_lblNuevoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(87, 87, 87))
                    .addGroup(ventaCCLayout.createSequentialGroup()
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(28, 28, 28)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ventaCCLayout.createSequentialGroup()
                                .addComponent(cc_txtbuscaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(ventaCCLayout.createSequentialGroup()
                                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cc_txtNit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(cc_txtNoRegistro, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cc_txtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cc_txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cc_txtDui, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cc_txtNombreNegocio, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cc_txtGiro))
                                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ventaCCLayout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cc_btnSeleccionarproducto)
                                            .addComponent(cc_btnSeleccionarCliente))
                                        .addGap(130, 130, 130))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventaCCLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel29)
                                            .addGroup(ventaCCLayout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cc_lblFechaCC)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ventaCCLayout.createSequentialGroup()
                                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(2, 2, 2)
                                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cc_txtCorrelativoCC, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cc_txtSerieCC, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(ventaCCLayout.createSequentialGroup()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(87, 87, 87))
                                    .addGroup(ventaCCLayout.createSequentialGroup()
                                        .addComponent(cbxCondicionPago, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))))
        );
        ventaCCLayout.setVerticalGroup(
            ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventaCCLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel52)
                .addGap(25, 25, 25)
                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ventaCCLayout.createSequentialGroup()
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cc_txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cc_txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cc_txtNombreNegocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cc_txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cc_txtNoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(cc_txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cc_txtGiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29)
                                .addComponent(cbxCondicionPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(ventaCCLayout.createSequentialGroup()
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(cc_lblFechaCC)
                            .addComponent(jLabel21)
                            .addComponent(cc_txtSerieCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cc_btnSeleccionarCliente)
                            .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(cc_txtCorrelativoCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)))
                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(cc_txtbuscaProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ventaCCLayout.createSequentialGroup()
                        .addComponent(cc_btnSeleccionarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(cc_lblInventarioSacar)
                            .addComponent(jLabel24)
                            .addComponent(cc_lblNuevoInventario))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cc_btnQuitarLinea)
                        .addComponent(cc_btnDeshacer)
                        .addComponent(cc_btnGuardarImprimir))
                    .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(cc_lblSumas)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cc_lblIva)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ventaCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cc_lbltotal)
                    .addComponent(jLabel26))
                .addContainerGap(450, Short.MAX_VALUE))
        );

        tabbedVentaCF.addTab("COMPROBANTEDE DE CREDITO FISCAL ", ventaCC);

        admVenta.setBackground(new java.awt.Color(255, 255, 255));

        adm_TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FECHA", "TIPO DOCUMENTO", "SERIE", "CORRELATIVO", "CLIENTE", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(adm_TableVenta);
        if (adm_TableVenta.getColumnModel().getColumnCount() > 0) {
            adm_TableVenta.getColumnModel().getColumn(1).setMinWidth(40);
            adm_TableVenta.getColumnModel().getColumn(2).setPreferredWidth(10);
            adm_TableVenta.getColumnModel().getColumn(4).setMinWidth(60);
        }

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));

        adm_generarReporte.setText("GENERAR REPORTE ");
        adm_generarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_generarReporteActionPerformed(evt);
            }
        });

        adm_TipoRepo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOTAL DE CAJA RESUMIDO", "DETALLE DE VENTAS CON COSTO" }));

        jLabel30.setText("ELIJA UN REPORTE ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(adm_TipoRepo, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(adm_generarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adm_TipoRepo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(adm_generarReporte)
                .addContainerGap())
        );

        jLabel53.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(102, 0, 0));
        jLabel53.setText("ADMINISTRADOR DE VENTAS ");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));

        jLabel31.setText("AÑO: ");

        jLabel32.setText("TIPO DOCUMENTO");

        adm_cbxTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "COMPROBANTE DE CREDITO FISCAL ", "FACTURA DE CONSUMIDOR FINAL ", " " }));

        adm_btnFiltrarBD.setText("FILTRAR");
        adm_btnFiltrarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_btnFiltrarBDActionPerformed(evt);
            }
        });

        jLabel33.setText("FILTROS GENERALES ");

        adm_btnQuitarBD.setText("QUITAR");
        adm_btnQuitarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_btnQuitarBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(adm_ycanio, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(adm_cbxTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(adm_btnQuitarBD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(adm_btnFiltrarBD)
                .addGap(53, 53, 53))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(jLabel33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32)
                        .addComponent(adm_cbxTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adm_btnQuitarBD)
                        .addComponent(adm_btnFiltrarBD))
                    .addComponent(adm_ycanio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(12, 12, 12))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));
        jPanel6.setToolTipText("MOSTRARA LOS REGISTROS SIN IMPORTAR EL TIPO O EL AÑO DEL DOCUMENTO ");

        jLabel34.setText("CORRELATIVO");

        jLabel35.setText("CLIENTE");

        jLabel36.setText("FECHA");

        adm_btnFiltrarT.setText("FILTRAR");
        adm_btnFiltrarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_btnFiltrarTActionPerformed(evt);
            }
        });

        jLabel37.setText("FILTROS AVANZADOS");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adm_txtCorr, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(adm_txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adm_dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(adm_btnFiltrarT)
                .addGap(49, 49, 49))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34)
                        .addComponent(jLabel35)
                        .addComponent(jLabel36)
                        .addComponent(adm_txtCorr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(adm_txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(adm_btnFiltrarT)
                    .addComponent(adm_dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        adm_btnExcel.setBackground(new java.awt.Color(0, 102, 0));
        adm_btnExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        adm_btnExcel.setForeground(new java.awt.Color(255, 255, 255));
        adm_btnExcel.setText("ENVIAR A EXCEL");
        adm_btnExcel.setToolTipText("ENVIA A EXCEL EL CONTENIDO DE LA TABLA");
        adm_btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_btnExcelActionPerformed(evt);
            }
        });

        adm_btnDetalle.setText("VER DETALLE Y/O MODIFICAR");
        adm_btnDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_btnDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout admVentaLayout = new javax.swing.GroupLayout(admVenta);
        admVenta.setLayout(admVentaLayout);
        admVentaLayout.setHorizontalGroup(
            admVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, admVentaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addGap(486, 486, 486))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, admVentaLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(admVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(admVentaLayout.createSequentialGroup()
                        .addGroup(admVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 868, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(admVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(admVentaLayout.createSequentialGroup()
                                .addComponent(adm_btnDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(adm_btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31))
        );
        admVentaLayout.setVerticalGroup(
            admVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, admVentaLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(admVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(admVentaLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(admVentaLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(admVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(adm_btnDetalle)
                            .addComponent(adm_btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(254, 254, 254))
        );

        tabbedVentaCF.addTab("ADMINISTRADOR DE VENTYAS ", admVenta);

        getContentPane().add(tabbedVentaCF);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        
        if (!(objfrmSeleccionarClienteCF.nombre.isEmpty())) {
            seleccionarCliente(objfrmSeleccionarClienteCF.nombre);//cuando regresa de selccionar el cliente consulta el cliente seleccionado 
        }
        if (!(objfrmSeleccionarProducto.idproducto.isBlank())) {
            SeleccionarProductoporCodigo(objfrmSeleccionarProducto.idproducto);//cuando regresa de selccionar el prod consulta el prod seleccionado 
        }

        if (!(objfrmSeleccionarClienteCC.noRegistro.isBlank())) {
            SeleccionarCliente_noRegisro(objfrmSeleccionarClienteCC.noRegistro);//cuando regresa de selccionar el clientecc consulta el noRegistro seleccionado 
        }
    }//GEN-LAST:event_formWindowActivated

    private void btnGuardarCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCFActionPerformed
        // TODO add your handling code here:
        try {
            int respuesta = -1;
            if (lblTotalCF.getText().equals("0.00")) {
                respuesta = JOptionPane.showConfirmDialog(null,
                        "EL TOTAL DEL DOCUMENTO ES CERO,¿DESEA GUARDARLO?",
                        "ELIJA UNA OPCION:", ConfirmationCallback.YES_NO_OPTION);

            }
            if (respuesta == 1) {
                //  entra si la respuesta es no. si la repuesta es si entonces no entra 
                return;
            }

            if (!(esNumerico(txtCorrelativoCF.getText()))) {
                JOptionPane.showMessageDialog(null,
                        "INGRESE UN NUMERO CORRELATIVO VALIDO  ",
                        "ADVERTENCIA !",
                        JOptionPane.WARNING_MESSAGE);
                txtCorrelativoCF.requestFocus();
                return;
            }

            if (txtCorrelativoCF.getText().isBlank()) {
                JOptionPane.showMessageDialog(null,
                        "INGRESE EL CORRELATIVO ", "ADVERTENCIA !",
                        JOptionPane.WARNING_MESSAGE);
                txtCorrelativoCF.requestFocus();
                return;
            }

            Double total = Double.parseDouble(lblTotalCF.getText());

            if (total >= 220.00) {
                if (!(validacampos())) {
                    return;
                }
            }

            if (!(RegistrarVenta())) {
                return; //si el insert falla regresa para corregir factura 
            }

            if (!(RegistrarDetalle(tableVentaCF))) {
                return; //si el insert falla regresa para corregir factura 
            }

            montoLetra(lblTotalCF.getText()); // obtiene el monto total en letras
            generarFacturaPdf(); // genera la facturta en pdf
            Impresor objImpresor = new Impresor();
            objImpresor.imprimir(); // imprimira la factura en fisico
            limpiarFactura(); // limpia la pantalla
            txtCorrelativoCF.requestFocus();
            onbteneMaxrCorrelativo(); // actualiza el correlativo para la siguiente factura

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL IMPRIMIR " + e,
                    "ALERTA", JOptionPane.ERROR_MESSAGE);

        } catch (PrinterException | IOException | NumberFormatException ex) {
            Logger.getLogger(frmVenta.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarCFActionPerformed

    private void btnQuitarLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarLineaActionPerformed
        // TODO add your handling code here:

        eliminarItem(tableVentaCF);
    }//GEN-LAST:event_btnQuitarLineaActionPerformed

    private void btnSeleccionarproductoCFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSeleccionarproductoCFKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            objfrmSeleccionarProducto.setVisible(true); //hace visible la frmSeleccionarProducto
        }
    }//GEN-LAST:event_btnSeleccionarproductoCFKeyPressed

    private void btnSeleccionarproductoCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarproductoCFActionPerformed
        // TODO add your handling code here:
        objfrmSeleccionarProducto.setVisible(true); //hace visible la frmSeleccionarProducto
    }//GEN-LAST:event_btnSeleccionarproductoCFActionPerformed

    private void btnSeleccionarClienteCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarClienteCFActionPerformed
        // TODO add your handling code here:
        objfrmSeleccionarClienteCF.setVisible(true); //hace visible la VENTANA

    }//GEN-LAST:event_btnSeleccionarClienteCFActionPerformed

    private void tableVentaCFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tableVentaCFPropertyChange
        // TODO add your handling code here:
        cambioPropiedad(tableVentaCF);
    }//GEN-LAST:event_tableVentaCFPropertyChange

    private void btnDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshacerActionPerformed
        // TODO add your handling code here:
        limpiarFactura();
    }//GEN-LAST:event_btnDeshacerActionPerformed

    private void cc_btnSeleccionarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cc_btnSeleccionarproductoActionPerformed
        // TODO add your handling code here:
        objfrmSeleccionarProducto.setVisible(true);
    }//GEN-LAST:event_cc_btnSeleccionarproductoActionPerformed

    private void cc_btnSeleccionarproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cc_btnSeleccionarproductoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cc_btnSeleccionarproductoKeyPressed

    private void cc_btnSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cc_btnSeleccionarClienteActionPerformed
        // TODO add your handling code here:
        objfrmSeleccionarClienteCC.setVisible(true);

    }//GEN-LAST:event_cc_btnSeleccionarClienteActionPerformed

    private void cc_tableVentaCCPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cc_tableVentaCCPropertyChange
        // TODO add your handling code here:
        cambioPropiedad(cc_tableVentaCC);
    }//GEN-LAST:event_cc_tableVentaCCPropertyChange

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    
        txtCorrelativoCF.requestFocus();
        onbteneMaxrCorrelativo();
    }//GEN-LAST:event_formWindowOpened

    private void cc_btnQuitarLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cc_btnQuitarLineaActionPerformed
        // TODO add your handling code here:
        eliminarItemCC(cc_tableVentaCC);
    }//GEN-LAST:event_cc_btnQuitarLineaActionPerformed

    private void cc_btnGuardarImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cc_btnGuardarImprimirActionPerformed
        // TODO add your handling code here:
        try {

            int respuesta = -1;
            if (cc_lbltotal.getText().equals("0.00")) {
                respuesta = JOptionPane.showConfirmDialog(null,
                        "EL TOTAL DEL DOCUMENTO ES CERO,¿DESEA GUARDARLO?",
                        "ELIJA UNA OPCION:", ConfirmationCallback.YES_NO_OPTION);

            }
            if (respuesta == 1) {
                //  entra si la respuesta es no. si la repuesta es si entonces no entra 
                return;
            }

            if (!(esNumerico(cc_txtCorrelativoCC.getText()))) {
                JOptionPane.showMessageDialog(null,
                        "INGRESE UN NUMERO CORRELATIVO VALIDO  ",
                        "ADVERTENCIA !",
                        JOptionPane.WARNING_MESSAGE);
                cc_txtCorrelativoCC.requestFocus();
                return;
            }

            if (!validacamposCC()) {
                // si no se ha seleccionado el cliente no permite el registro del ccf 
                return;
            }

            if (!(RegistrarVentaCC())) {
                return; //si el insert falla regresa para corregir factura 
            }

            if (!(RegistrarDetalle(cc_tableVentaCC))) {
                return; //si el insert falla regresa para corregir factura 
            }
            montoLetra(cc_lbltotal.getText()); // obtiene el monto total en letras
            generarFacturaPdfCC(); // genera la facturta en pdf
            Impresor objImpresor = new Impresor();
            objImpresor.imprimir(); // imprimira la factura en fisico
            limpiarFacturaCC(); // limpia la pantalla
            cc_txtCorrelativoCC.requestFocus();
            onbteneMaxrCorrelativo(); // actualiza el correlativo para la siguiente factura

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL IMPRIMIR " + e,
                    "ALERTA", JOptionPane.ERROR_MESSAGE);
        } catch (PrinterException | IOException ex) {
            Logger.getLogger(frmVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cc_btnGuardarImprimirActionPerformed

    private void cc_btnDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cc_btnDeshacerActionPerformed
        // TODO add your handling code here:
        limpiarFacturaCC();
    }//GEN-LAST:event_cc_btnDeshacerActionPerformed

    private void adm_btnFiltrarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_btnFiltrarBDActionPerformed
        // TODO add your handling code here:
        Utilidades.limpiartabla(modeloventa);
        listarVentas();
    }//GEN-LAST:event_adm_btnFiltrarBDActionPerformed

    private void adm_btnQuitarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_btnQuitarBDActionPerformed
        // TODO add your handling code here:

        adm_ycanio.setYear(current_date.getYear());
        adm_cbxTipoDoc.setSelectedIndex(0);
        Utilidades.limpiartabla(modeloventa);
        listarVentas();
    }//GEN-LAST:event_adm_btnQuitarBDActionPerformed

    private void adm_btnFiltrarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_btnFiltrarTActionPerformed
        // TODO add your handling code here:
        Utilidades.limpiartabla(modeloventa);
        listarVentas_avanzado();
    }//GEN-LAST:event_adm_btnFiltrarTActionPerformed

    private void adm_btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_btnExcelActionPerformed
        // TODO add your handling code here:

        try {
            ExportadorExcel objExportadorExcel = new ExportadorExcel();

            objExportadorExcel.exportarExcel(adm_TableVenta);

        } catch (IOException ex) {
            System.out.println("Error al exportar: " + ex);
        }

    }//GEN-LAST:event_adm_btnExcelActionPerformed

    private void adm_generarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_generarReporteActionPerformed
        // TODO add your handling code here:

        frmFechasRango onjfrmFechasRango = new frmFechasRango(adm_TipoRepo.getSelectedItem().toString().trim());
        onjfrmFechasRango.setVisible(true);

    }//GEN-LAST:event_adm_generarReporteActionPerformed

    private void adm_btnDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_btnDetalleActionPerformed
        // TODO add your handling code here:

        int linea = adm_TableVenta.getSelectedRow();
        if (linea == -1) {
            return;
        }
        String tipo = String.valueOf(adm_TableVenta.getValueAt(linea, 1));
        tipo = firstNChars(tipo, 4); // recorto el tipo parta evaluar mejor 
        String serie = String.valueOf(adm_TableVenta.getValueAt(linea, 2));
        String correlativo = String.valueOf(adm_TableVenta.getValueAt(linea, 3));
        if (tipo.equals("FACT")) {

            frmDetalleFactura objfrmDetalleFactura = new frmDetalleFactura(serie, correlativo);
            objfrmDetalleFactura.setVisible(true);
        } else {
            frmDetalleComprobante objfrmDetalleComprobante = new frmDetalleComprobante(serie, correlativo);
            objfrmDetalleComprobante.setVisible(true);
        }


    }//GEN-LAST:event_adm_btnDetalleActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmVenta.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmVenta().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel admVenta;
    private javax.swing.JTable adm_TableVenta;
    private javax.swing.JComboBox<String> adm_TipoRepo;
    private javax.swing.JButton adm_btnDetalle;
    private javax.swing.JButton adm_btnExcel;
    private javax.swing.JButton adm_btnFiltrarBD;
    private javax.swing.JButton adm_btnFiltrarT;
    private javax.swing.JButton adm_btnQuitarBD;
    private javax.swing.JComboBox<String> adm_cbxTipoDoc;
    private com.toedter.calendar.JDateChooser adm_dateChooser;
    private javax.swing.JButton adm_generarReporte;
    private javax.swing.JTextField adm_txtCliente;
    private javax.swing.JTextField adm_txtCorr;
    private com.toedter.calendar.JYearChooser adm_ycanio;
    private javax.swing.JButton btnDeshacer;
    private javax.swing.JButton btnGuardarCF;
    private javax.swing.JButton btnQuitarLinea;
    private javax.swing.JButton btnSeleccionarClienteCF;
    private javax.swing.JButton btnSeleccionarproductoCF;
    private javax.swing.JComboBox<String> cbxCondicionPago;
    private javax.swing.JComboBox<String> cbxNombreCF;
    private javax.swing.JButton cc_btnDeshacer;
    private javax.swing.JButton cc_btnGuardarImprimir;
    private javax.swing.JButton cc_btnQuitarLinea;
    private javax.swing.JButton cc_btnSeleccionarCliente;
    private javax.swing.JButton cc_btnSeleccionarproducto;
    private javax.swing.JLabel cc_lblFechaCC;
    private javax.swing.JLabel cc_lblInventarioSacar;
    private javax.swing.JLabel cc_lblIva;
    private javax.swing.JLabel cc_lblNuevoInventario;
    private javax.swing.JLabel cc_lblSumas;
    private javax.swing.JLabel cc_lbltotal;
    private javax.swing.JTable cc_tableVentaCC;
    private javax.swing.JTextField cc_txtCorrelativoCC;
    private javax.swing.JTextField cc_txtDireccion;
    private javax.swing.JTextField cc_txtDui;
    private javax.swing.JTextField cc_txtGiro;
    private javax.swing.JTextField cc_txtNit;
    private javax.swing.JTextField cc_txtNoRegistro;
    private javax.swing.JTextField cc_txtNombre;
    private javax.swing.JTextField cc_txtNombreNegocio;
    private javax.swing.JTextField cc_txtSerieCC;
    private javax.swing.JTextField cc_txtbuscaProd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblFechaCF;
    private javax.swing.JLabel lblInventarioActual;
    private javax.swing.JLabel lblTotalCF;
    private javax.swing.JLabel lblUltimoRegistro;
    private javax.swing.JLabel lblUltimoRegistroCC;
    private javax.swing.JLabel lblcantUnidadMedida;
    private javax.swing.JTabbedPane tabbedVentaCF;
    private javax.swing.JTable tableVentaCF;
    private javax.swing.JTextField txtCorrelativoCF;
    private javax.swing.JTextField txtDireccionCF;
    private javax.swing.JTextField txtDuiCF;
    private javax.swing.JTextField txtSerieCF;
    private javax.swing.JTextField txtbuscaProdCF;
    private javax.swing.JPanel ventaCC;
    private javax.swing.JPanel ventaCF;
    // End of variables declaration//GEN-END:variables

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
            Paragraph noDocumento = new Paragraph(new Phrase(txtCorrelativoCF.getText() + espaciocorr,
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
            String nombrelimite = cbxNombreCF.getSelectedItem().toString();
            //limitar impresion nombre 
            nombrelimite = limitarImpresion(nombrelimite, (anchocf / 3.00));
            // fIn limitar impresion nombre   
            PdfPCell cliCliente = new PdfPCell(new Phrase(nombrelimite + "\n"
                    + txtDuiCF.getText() + "\n\n"
                    + txtDireccionCF.getText(), fuente));
            PdfPCell cliFecha = new PdfPCell(new Phrase(lblFechaCF.getText(), fuente));

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
                unidadMerdida = limitarImpresion(unidadMerdida, 6.00);
                //  fin limitar impresuin unidad de medida 
                String descripcion = tableVentaCF.getValueAt(i, 2).toString();
                //limitar impresion descripcion 
                descripcion = limitarImpresion(descripcion, (anchocf / 3.8));
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

    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    /**
     * *****************************************
     */
    private void generarFacturaPdfCC() {

        //genera una factuira en formato pdf 
        // String correlativo = txtCorrelativoCF.getText();
        try {

            int anchoccf = Integer.parseInt(obtenerAncho(2)); // obtiene el ancho del ccf

            FileOutputStream archivo;

            File salida = new File("src/factura/venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, archivo);
            doc.open();
            //fuente 
            Font fuente = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
            Font fuente2 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
            Font fuente3 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);
            Font fuente4 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
            Font fuente5 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);

            // imprimir espacio 
            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            espacio.add("\n");
            espacio.setAlignment(Element.ALIGN_CENTER);
            doc.add(espacio);

            // imprimir correlativo 
            String espaciocorr = "                                                                               ";//este espacio es para alinea el correlativo 
            Paragraph noDocumento = new Paragraph(cc_txtCorrelativoCC.getText() + espaciocorr,
                    fuente2);
            noDocumento.add(Chunk.NEWLINE);
            noDocumento.setAlignment(Element.ALIGN_CENTER);
            doc.add(noDocumento);
            // cliente y fecha 
            PdfPTable tablaCli = new PdfPTable(3);
            tablaCli.setWidthPercentage(anchoccf);
            tablaCli.getDefaultCell().setBorder(0);
            float[] columnaCli = new float[]{5f, 21f, 14f};
            tablaCli.setWidths(columnaCli);
            tablaCli.setHorizontalAlignment(Element.ALIGN_LEFT);
            String nombrelimite = cc_txtNombre.getText();
            String nregistro = cc_txtNoRegistro.getText();
            String nit = cc_txtNit.getText();
            String giro = cc_txtGiro.getText();
            giro = limitarImpresion(giro, (anchoccf / 3.8));
            String condiPago = cbxCondicionPago.getSelectedItem().toString();
            //limpiar impresion nombre 
            nombrelimite = limitarImpresion(nombrelimite, (anchoccf / 3.00));

            // fin limitar impresion nombre   
            PdfPCell cliCliente = new PdfPCell(new Phrase(nombrelimite + "\n"
                    + cc_txtDui.getText() + "\n\n\n"
                    + cc_txtDireccion.getText(), fuente5));
            PdfPCell clienteFecha = new PdfPCell(new Phrase(cc_lblFechaCC.getText() + "\n"
                    + "       " + nregistro + "\n" + nit + "\n" + giro + "\n"
                    + "                " + condiPago,
                    fuente5));

            cliCliente.setBorder(0);
            clienteFecha.setBorder(0);
            tablaCli.addCell("");
            tablaCli.addCell(cliCliente); // arrelaglar esto 041122
            tablaCli.addCell(clienteFecha);
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
            tablapro.setWidthPercentage(anchoccf);
            tablapro.getDefaultCell().setBorder(0);
            float[] columnapro = new float[]{5f, 11f, 25f, 20f, 15f};
            tablapro.setWidths(columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            for (int i = 0; i < cc_tableVentaCC.getRowCount(); i++) {
                String cantidad = cc_tableVentaCC.getValueAt(i, 3).toString();
                String unidadMerdida = cc_tableVentaCC.getValueAt(i, 4).toString();
                // limitar impresion unidad de medida 
                unidadMerdida = limitarImpresion(unidadMerdida, 6.00);

                //  fin limitar impresuin unidad de medida 
                String descripcion = cc_tableVentaCC.getValueAt(i, 2).toString();
                //limitar impresion descripcion 
                descripcion = limitarImpresion(descripcion, (anchoccf / 3.8));
                // fin limitar impresion descripcion  
                String precio = cc_tableVentaCC.getValueAt(i, 5).toString();//imprimir el precio sin iva 
                Double preciosinIva = Double.parseDouble(precio);
                preciosinIva /= 1.13;
                precio = String.format("%.2f", preciosinIva);
                String total = cc_tableVentaCC.getValueAt(i, 6).toString();
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

            int montoPosicion = 8 - (cc_tableVentaCC.getRowCount());
            for (int i = 0; i < montoPosicion; i++) {

                // imprimir espacio 
                espacio1.add(Chunk.NEWLINE);
                espacio1.add("");
                espacio1.setAlignment(Element.ALIGN_CENTER);
                doc.add(espacio);

            }

            // totales
            if (cc_lbltotal.getText().equals("0.00")) {
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
                    if (length2 > (anchoccf / 2.4)) {
                        montoEnLetra = "";
                        for (int j = 0; j < length2; j++) {

                            if (j < (anchoccf / 2.4)) {
                                montoEnLetra += String.valueOf(aCaracteres2[j]);
                            } else {
                                if (j < ((anchoccf / 2.4) * 2)) {
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
                        new Phrase(new Phrase(montoEnLetra, fuente4)), rect.getLeft(), rect.getBottom(), 0);
                if (!(grande.isBlank())) {
                    // por si el monto es demasiado largo 
                    Rectangle rect1 = new Rectangle(65, 395, 550, 800);
                    writer.setBoxSize("art", rect1);
                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                            new Phrase(new Phrase(grande, fuente4)), rect1.getLeft(), rect1.getBottom(), 0);
                }
                // sumas 
                int posicionx = (int) (anchoccf * 4.7);
                Rectangle rect2 = new Rectangle(posicionx, 405, 550, 800);
                writer.setBoxSize("art", rect2);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(
                                (new Phrase(cc_lblSumas.getText(), fuente3))
                        ), rect2.getLeft(), rect2.getBottom(), 0);

                // imprimir iva  
                Rectangle rectiva = new Rectangle(posicionx, 390, 550, 800);
                writer.setBoxSize("art", rectiva);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(
                                (new Phrase(cc_lblSumas.getText(), fuente3))
                        ), rectiva.getLeft(), rectiva.getBottom(), 0);

                Rectangle rect3 = new Rectangle(posicionx, 345, 550, 800);
                writer.setBoxSize("art", rect3);

                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                        new Phrase(
                                (new Phrase(cc_lbltotal.getText(), fuente3))
                        ), rect3.getLeft(), rect3.getBottom(), 0);

                doc.close();
                archivo.close();
                // Desktop.getDesktop().open(salida);

            }
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al imprimir" + e.toString());
        }
    }

    public String limitarImpresion(String cadena, Double llave) {
        /*este metodo se encarga de limitar el tamaño de impresion para algunos elementos 
        en el ccf y/o fact */
        Double ancho = (llave);
        try {
            char[] cadenaChar = cadena.toCharArray();
            int nombrelimiteLength = cadena.length();
            if (nombrelimiteLength > ancho) {
                cadena = "";
                for (int j = 0; j < ancho; j++) {
                    cadena += String.valueOf(cadenaChar[j]);
                }
            }
        } catch (Exception e) {
            System.out.println("error" + e);
        }
        return cadena;
    }

}
