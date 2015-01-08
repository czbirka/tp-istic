package components;

import mvc.Controller;
import mvc.Field;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas & amona on 20/11/14.
 */
public class AddFieldButton extends JButton implements ActionListener {

    private Controller controller;

    public AddFieldButton(String text, Controller controller) {
        super(text);
        this.controller = controller;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        controller.createField();
    }
}
