import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class View extends JComponent implements Observer {

    @Override
    public void update() {

    }

    protected void paintComponent(Graphics g) {
        Graphics2D graph = (Graphics2D)g;

        Arc2D arc = new Arc2D.Double(100, 100, 100, 50, 90, 180, Arc2D.OPEN);
        graph.fill(arc);
    }
}
