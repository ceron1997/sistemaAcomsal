/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author admin
 */
public class TablaCC {

    String items[] = new String[]{"MASTER", "UNIDAD", "DOCENA", "PROMOCION"};
    public JComboBox cbxUnidadMedida = new JComboBox(items);
    // JTextField textField = new JTextField();

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
    public TablaCC() {
        
    }

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
