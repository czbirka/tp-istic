import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas & amona on 26/11/14.
 */
public class NextButton extends JButton implements ActionListener {

    private Controller controller;

    public NextButton(String text, Controller controller) {
        super(text);
        this.controller = controller;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        controller.showNext();
    }
}
