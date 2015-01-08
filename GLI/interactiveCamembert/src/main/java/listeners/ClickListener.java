package listeners;

import mvc.Controller;
import mvc.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Created by thomas & amona on 15/11/14.
 */
public class ClickListener implements MouseListener {

    private Controller controller;

    /**
     * Constructor.
     * @param controller
     */
    public ClickListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Handles the selection/deselection of the arcs.
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        View view = controller.getView();
        ArrayList<Arc2D> arcList = view.getArcList();

        if (e.getClickCount() == 1) {
            if (view.getDragCircle().contains(e.getX(), e.getY()) && !view.getBlankCircle().contains(e.getX(), e.getY())) {
                for (Arc2D arc : arcList) {
                    // Check if the mouse coordinates are inside the arc primitive.
                    if (arc.contains(e.getX(), e.getY())) {
                        controller.setFocusedArcIndex(arcList.indexOf(arc));
                        controller.updateViewInfo();
                        controller.showButtons();
                        view.repaint();
                    }
                }
            } else if (view.getFocusedArcIndex() != -1) {
                Arc2D focusedArc = view.resizeArc(arcList.get(view.getFocusedArcIndex()), view.FOCUSED_ARC_RADIUS);
                if (!focusedArc.contains(e.getX(), e.getY()) || view.getBlankCircle().contains(e.getX(), e.getY())) {
                    controller.setFocusedArcIndex(-1);
                    controller.clearTableSelection();
                    view.repaint();
                    controller.hideButtons();
                }
            }
        }
        // Double click the center circle to reset the angle
        else if (e.getClickCount() == 2) {
            if (view.getTitleCircle().contains(e.getX(), e.getY())) {
                view.setDefaultStartAngle(0);
                view.computeArcs(controller.getModel());
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
