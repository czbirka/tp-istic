import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Created by thomas & amona on 27/10/14.
 */
public class Listener implements MouseListener {
    private Controller controller;

    /**
     * Constructor.
     * @param controller
     */
    public Listener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Selects the arc under the mouse when clicked.
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        View view = controller.getView();
        ArrayList<Arc2D> arcList = view.getArcList();

        for (Arc2D arc : arcList) {
            // Check if the mouse coordinates are inside the arc primitive.
            if (arc.contains(mouseEvent.getX(), mouseEvent.getY()) &&
                    !view.getBlankCircle().contains(mouseEvent.getX(), mouseEvent.getY())) {
                view.setFocusedArcIndex(arcList.indexOf(arc));
                controller.updateViewInfo();
                view.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
