/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factura;


import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author david
 */
public class Impresor {

    private final static Logger LOGGER = Logger.getLogger("mx.hash.impresionpdf.Impresor");

    public static void main(String[] args) {
        Impresor impresor = new Impresor();

        try {
            impresor.imprimir();
        } catch (PrinterException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error de impresion", "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /***
     * Manda un documento a imprimir a la impresora que se indique en el dialogo
     * @throws PrinterException
     * @throws IOException 
     */
    public void imprimir() throws PrinterException, IOException {
        // Indicamos el nombre del archivo Pdf que deseamos imprimir
        PDDocument document = PDDocument.load(new File("C:/Users/admin/Documents/NetBeansProjects/sistemaAcomsal/src/factura/venta.pdf"));

        PrinterJob job = PrinterJob.getPrinterJob();

        LOGGER.log(Level.INFO, "Mostrando el dialogo de impresion");
        if (job.printDialog() == true) {            
            job.setPageable(new PDFPageable(document));

          LOGGER.log(Level.INFO, "Imprimiendo documento");
            job.print();
            document.close();
        }
    }
}

