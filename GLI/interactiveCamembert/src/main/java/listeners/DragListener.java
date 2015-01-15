package listeners;

import mvc.Controller;
import mvc.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by thomas & amona on 15/11/14.
 */
public class DragListener implements MouseListener {

    private Controller controller;

    /**
     * Constructor.
     * @param controller
     */
    public DragListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Handles the rotation of the camembert.
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

        final View view = controller.getView();
        final int originX = view.getOriginX();
        final int originY = view.getOriginY();

        if (view.getDragCircle().contains(e.getX(), e.getY()) && !view.getBlankCircle().contains(e.getX(), e.getY())) {

            // Get the mouse initial position
            int initialX = e.getX() - originX;
            int initialY = originY - e.getY();

            // Get the distance from the circle's center to the mouse initial position : √(x² + y²)
            double initialDist = Math.sqrt(Math.pow(initialX, 2) + Math.pow(initialY, 2));

            // Get the initial angle : arccos(initialX / initialDist)
            double initialA = Math.toDegrees(Math.acos(initialX / initialDist));
            if (initialY < 0) {
                initialA = 360 - initialA;
            }
            final double initialAngle = view.getDefaultStartAngle() - initialA;

            view.addMouseMotionListener(new MouseMotionListener() {

                @Override
                public void mouseDragged(MouseEvent dragEvent) {

                    // Get the mouse current position
                    int currentX = dragEvent.getX() - originX;
                    int currentY = originY - dragEvent.getY();

                    // Get the distance from the circle's center to the mouse current position : √(x² + y²)
                    double currentDist = Math.sqrt(Math.pow(currentX, 2) + Math.pow(currentY, 2));

                    // Get the current angle : arccos(currentX / currentDist)
                    double angle = Math.toDegrees(Math.acos(currentX / currentDist));
                    if (currentY < 0) {
                        angle = 360 - angle;
                    }

                    // Update the view
                    view.setDefaultStartAngle((angle + initialAngle) % 360);
                    view.computeArcs(controller.getModel());
                    view.repaint();
                }

                @Override
                public void mouseMoved(MouseEvent mouseEvent) {

                }
            });
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        controller.resetListeners();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
