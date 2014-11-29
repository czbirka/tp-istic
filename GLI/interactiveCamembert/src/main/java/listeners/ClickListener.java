package listeners;

import mvc.Controller;
import mvc.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Created by thomas & amona on 27/10/14.
 */
public class ClickListener implements MouseListener {

    private Controller controller;

    /**
     * Constructor.
     * @param controller
     */
    public ClickListener(Controller controller) {
        this.controller = controller;
        this.controller.getView().clearMouseListeners();
    }

    /**
     * Selects the arc under the mouse when clicked.
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        View view = controller.getView();
        ArrayList<Arc2D> arcList = view.getArcList();

        if (view.getDragCircle().contains(e.getX(), e.getY()) && !view.getBlankCircle().contains(e.getX(), e.getY())) {
            for (Arc2D arc : arcList) {
                // Check if the mouse coordinates are inside the arc primitive.
                if (arc.contains(e.getX(), e.getY())) {
                    view.setFocusedArcIndex(arcList.indexOf(arc));
                    controller.updateViewInfo();
                    view.repaint();
                }
            }
        }
        else {
            view.setFocusedArcIndex(-1);
            view.repaint();
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
