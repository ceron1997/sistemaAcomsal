/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

//import Reder.Render;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author David
 */
public class Tabla {

    String items[] = new String[]{"MASTER", "UNIDAD", "DOCENA", "PROMOCION"};
    public JComboBox cbxUnidadMedida = new JComboBox(items);
    // JTextField textField = new JTextField();

    public Tabla() {

    }

    private final boolean[] editable = {false, false, false, true, true, true, true};
    String[] Column = new String[]{
        "ITEM", "CODIGO ", "DESCRIPCION", "CANTIDAD", "UNIDAD DE MEDIDA ", "PRECIO UNITARIO", "TOTAL"
    };

    public DefaultTableModel dt1 = new DefaultTableModel(Column, 0) {

        @Override
        public boolean isCellEditable(int row, int column) {
            return editable[column];
        }
    };

    public void visualizar(JTable tabla, Object[] datos) {

        dt1.addRow(datos);
        tabla.setModel(dt1);
        setBox(tabla, tabla.getColumnModel().getColumn(4));
        //  setbox2(tabla, tabla.getColumnModel().getColumn(3));

    }

    public void setBox(JTable tabla, TableColumn column) {

        column.setCellEditor(new DefaultCellEditor(cbxUnidadMedida));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        column.setCellRenderer(renderer);

    }
    

   
}
