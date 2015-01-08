
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

        model.addObserver(controller);
        model.addObserver(view);

        JFrame frame = new JFrame("Interactive Camembert");
        frame.add(view, BorderLayout.CENTER);

        controller.setView(view);
        model.setTitle("Web browsers");
        frame.setSize(GlobalConfigs.WINDOW_WIDTH, GlobalConfigs.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
