import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class View extends JComponent implements Observer {

    /**
     * Controller.
     */
    private Controller controller;

    /**
     * Arcs radius.
     */
    private static final double TITLE_CIRCLE_RADIUS = 150;
    private static final double BLANK_CIRCLE_RADIUS = TITLE_CIRCLE_RADIUS * 1.4;
    private static final double ARC_RADIUS = TITLE_CIRCLE_RADIUS * 2.5;
    private static final double FOCUSED_ARC_RADIUS = ARC_RADIUS * 1.1;

    /**
     * Colors.
     */
    private static final Color FOCUSED_ARC_COLOR = new Color(205, 0, 116);
    private static final Color BOX_BG_COLOR =  new Color(255, 116, 0);


    /**
     * List containing all the arcs for the view.
     */
    private ArrayList<Arc2D> arcList;

    /**
     * Center and title circle.
     */
    private Arc2D blankCircle, titleCircle;

    /**
     * Camembert's coordinates.
     */
    private int originX, originY;

    /**
     * Description box properties.
     */
    private int boxWidth, boxHeight, defaultOffset;

    /**
     * Camembert's title.
     */
    private String title;

    /**
     * Selected arc index.
     */
    private int focusedArcIndex;

    /**
     * Focused arc informations.
     */
    private String focusedArcName, focusedArcDesc, focusedArcValue;

    /**
     * Constructor.
     */
    public View(Controller controller) {
        this.controller = controller;

        arcList = new ArrayList<Arc2D>();

        originX = GlobalConfigs.WINDOW_WIDTH / 2;
        originY = GlobalConfigs.WINDOW_HEIGHT / 2;

        initialize();
    }

    /**
     * Sets the focused arc information.
     * @param name
     * @param description
     * @param value
     */
    void setFocusedArcInfo(String name, String description, String value) {
        this.focusedArcName = name;
        this.focusedArcDesc = description;
        this.focusedArcValue = value;
    }

    /**
     * Initializes the view's attributes.
     */
    private void initialize() {
        blankCircle = createArc(0, 360, Arc2D.OPEN, BLANK_CIRCLE_RADIUS);
        titleCircle = createArc(0, 360, Arc2D.OPEN, TITLE_CIRCLE_RADIUS);

        boxWidth = 100;
        boxHeight = 40;
        defaultOffset = 5;

        focusedArcIndex = -1;
        focusedArcDesc = "";
        focusedArcName = "";
        focusedArcValue = "";
    }

    /**
     * Method called when the observable object is modified
     * @param observable
     */
    @Override
    public void update(Observable observable) {
        Model model = (Model) observable;

        title = model.getTitle();

        // Arcs settings
        float angleStart = 0, angleEnd = 0;

        // Compute the arcs
        for (int i = 0; i < model.getValues().size(); i++) {

            // Compute the arc angle from the model values
            angleEnd = 360 * model.getValueAsPercent(i);

            // Create the arc from the angle values
            final Arc2D arc = createArc(angleStart, angleEnd, Arc2D.PIE, ARC_RADIUS);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if (arc.contains(mouseEvent.getX(), mouseEvent.getY()) &&
                            !blankCircle.contains(mouseEvent.getX(), mouseEvent.getY())) {
                        focusedArcIndex = arcList.indexOf(arc);
                        controller.setViewInfo(focusedArcIndex);
                        repaint();
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
            });

            angleStart += angleEnd;
            arcList.add(arc);
        }
    }

    /**
     * Creates an arc from a given angle start and angle end value and a radiusCoefficient value.
     *
     * @param angleStart
     * @param angleEnd
     * @param type
     * @param radius
     * @return a new arc
     */
    public Arc2D createArc(double angleStart, double angleEnd, int type, double radius) {
        return new Arc2D.Double(originX - radius / 2,
                originY - radius / 2,
                radius, radius,
                angleStart,
                angleEnd,
                type);
    }

    /**
     * Resize an arc with a new radius value
     *
     * @param arc
     * @param radius
     * @return The resized arc
     */
    public Arc2D resizeArc(Arc2D arc, double radius) {
        return createArc(arc.getAngleStart(), arc.getAngleExtent(), arc.getArcType(), radius);
    }

    /**
     * Creates a box at the selected arc's coordinates
     *
     * @param focusedArc
     * @return
     */
    public Shape getBoxFromArc(Arc2D focusedArc) {

        // Get the middle angle of the focused arc
        double middleAngle = (focusedArc.getAngleExtent() / 2) + focusedArc.getAngleStart();
        double middleAngleInRad = middleAngle * (Math.PI / 180.f);

        // Convert polar to cartesian coordinates
        int startX = (int) (FOCUSED_ARC_RADIUS * Math.cos(middleAngleInRad));
        int startY = (int) (FOCUSED_ARC_RADIUS * Math.sin(middleAngleInRad));

        // Compute the offset depending on the location
        int offsetX = startX < 0 ? (-boxWidth - defaultOffset) : defaultOffset;
        int offsetY = startY > 0 ? (-boxHeight - defaultOffset) : defaultOffset;

        // Shift using the camembert's coordinates
        double rectX = originX + startX / 2 + offsetX;
        double rectY = originY - startY / 2 + offsetY;

        return new RoundRectangle2D.Double(rectX, rectY, boxWidth, boxHeight, 1, 1);
    }

    /**
     * Draws the arcs.
     *
     * @param graph
     */
    public void drawArcs(Graphics2D graph) {
        int r = 255;
        for (Arc2D arc : arcList) {
            graph.setColor(new Color(r, 0, 0));
            r -= 255 / arcList.size();
            graph.fill(arc);
        }

        // Draw the box with the name & description if an arc is selected
        if (focusedArcIndex > -1) {
            Arc2D focusedArc = resizeArc(arcList.get(focusedArcIndex), FOCUSED_ARC_RADIUS);
            graph.setColor(FOCUSED_ARC_COLOR);
            graph.fill(focusedArc);

            // Content shows the selected arc's name and description (if it's not empty)
            String boxContent = focusedArcDesc.length() > 0 ? focusedArcName + " " + focusedArcDesc : focusedArcName;
            boxWidth = graph.getFontMetrics().stringWidth(boxContent) + defaultOffset * 2;

            // Create the description shape and draw it
            Shape descriptionBox = getBoxFromArc(focusedArc);
            int x = (int) descriptionBox.getBounds2D().getX() + defaultOffset;
            int y = (int) descriptionBox.getBounds2D().getY() + defaultOffset + boxHeight / 2 ;

            graph.setColor(BOX_BG_COLOR);
            graph.fill(descriptionBox);

            graph.setColor(Color.BLACK);
            graph.drawString(boxContent, x, y);
        }
    }

    /**
     * Draws the window.
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        Graphics2D graph = (Graphics2D) g;
        int titleWidthInPx = graph.getFontMetrics().stringWidth(title);

        // Enable anti-aliasing
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawArcs(graph);

        // Draw the main circles
        graph.setColor(Color.WHITE);
        graph.fill(blankCircle);

        graph.setColor(Color.BLACK);
        graph.fill(titleCircle);

        // Draw the title at the center of the camembert
        graph.setColor(Color.WHITE);
        graph.drawString(title, originX - titleWidthInPx / 2, originY);

        int valueWidth = graph.getFontMetrics().stringWidth(focusedArcValue);
        int valueHeight = graph.getFontMetrics().getHeight();

        // Shows the selected arc's value at the center of the camembert
        graph.drawString(focusedArcValue, originX - valueWidth / 2, originY + valueHeight + defaultOffset);
    }
}
