package components;

import mvc.Controller;
import utils.GlobalConfigs;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;

/**
 * Created by thomas & amona on 01/12/14.
 */
public class CamembertTable extends JTable {

    private static final String[] columnNames = { "Name", "Description", "Value" };
    private Controller controller;

    public CamembertTable(Controller controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        CamembertTableModel tableModel = new CamembertTableModel(columnNames, controller);
        this.setModel(tableModel);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
