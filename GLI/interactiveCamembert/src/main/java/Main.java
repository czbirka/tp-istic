import javax.swing.*;
import java.awt.*;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();

        model.addObserver(view);
        model.addObserver(controller);

        JFrame frame = new JFrame();
        frame.add(view, BorderLayout.CENTER);

        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
