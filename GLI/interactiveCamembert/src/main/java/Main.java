import components.NextButton;
import components.PreviousButton;
import mvc.Controller;
import mvc.Model;
import mvc.View;
import utils.GlobalConfigs;

import javax.swing.*;
import java.awt.*;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);

        controller.setView(view);

        model.addObserver(view);
        model.setTitle("Navigateurs");

        JFrame frame = new JFrame();
        frame.add(view, BorderLayout.CENTER);
        NextButton next = new NextButton("<", controller);
        PreviousButton previous = new PreviousButton(">", controller);
        JPanel panel = new JPanel();
        panel.add(next);
        panel.add(previous);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setSize(GlobalConfigs.WINDOW_WIDTH, GlobalConfigs.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
