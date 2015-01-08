package components;

import mvc.Controller;
import mvc.Field;
import utils.GlobalConfigs;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by thomas & amona on 01/12/14.
 */
public class CamembertTable extends JTable implements MouseListener {

    private static final String[] columnNames = { "Name", "Description", "Value" };
    private Controller controller;
    CamembertTableModel tableModel;

    public CamembertTable(Controller controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        tableModel = new CamembertTableModel(columnNames, controller);
        this.setModel(tableModel);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(this);
    }

    public void addRow(Field field) {
        tableModel.addRow(field);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        controller.setFocusedArcIndex(getSelectedRow());
        controller.updateViewInfo();
        controller.showButtons();
        controller.getView().repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
