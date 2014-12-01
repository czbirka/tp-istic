package components;

import mvc.Controller;
import mvc.Field;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * Created by thomas & amona on 01/12/14.
 */
public class CamembertTableModel extends AbstractTableModel {

    public static final int NAME_INDEX = 0;
    public static final int DESC_INDEX = 1;
    public static final int VALUE_INDEX = 2;

    protected String[] columnNames;
    protected Vector dataVector;
    protected Controller controller;

    public CamembertTableModel(String[] columnNames, Controller controller) {
        this.columnNames = columnNames;
        this.controller = controller;
        dataVector = new Vector();
        for (Field field : controller.getFields()) {
            dataVector.add(field);
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case NAME_INDEX:
            case DESC_INDEX:
            case VALUE_INDEX:
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        Field field = controller.getField(row);
        switch (column) {
            case NAME_INDEX:
                return field.getName();
            case DESC_INDEX:
                return field.getDescription();
            case VALUE_INDEX:
                return field.getValue();
            default:
                return new Object();
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        Field field = controller.getField(row);
        switch (column) {
            case NAME_INDEX:
                field.setName((String) value);
                break;
            case DESC_INDEX:
                field.setDescription((String) value);
                break;
            case VALUE_INDEX:
                field.setValue(Float.parseFloat((String) value));
                break;
            default:
                System.out.println("Invalid index");
        }
        fireTableCellUpdated(row, column);
        controller.setField(row, field);
    }

    @Override
    public int getRowCount() {
        return dataVector.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public void addRow(Field field) {
        dataVector.add(field);
        controller.addField(field);
        fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
    }

}
