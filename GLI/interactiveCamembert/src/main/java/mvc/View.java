package mvc;

import listeners.ClickListener;
import observer.Observable;
import observer.Observer;
import utils.GlobalConfigs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class View extends JComponent implements Observer {

    /**
     * mvc.Controller.
     */
    private Controller controller;

    /**
     * Arcs radius.
     */
    private static final double TITLE_CIRCLE_RADIUS = 150;
    private static final double BLANK_CIRCLE_RADIUS = TITLE_CIRCLE_RADIUS * 1.4;
    private static final double ARC_RADIUS = TITLE_CIRCLE_RADIUS * 2;
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
    private Arc2D blankCircle, titleCircle, dragCircle;

    /**
     * Camembert's coordinates.
     */
    private int originX, originY;

    /**
     * Description box properties.
     */
    private int boxWidth, boxHeight, defaultOffset;

    /**
     * Default start angle.
     */
    private double defaultStartAngle;

    /**
     * Camembert's title.
     */
    private String title;

    /**
     * Selected arc index.
     */
    private int focusedArcIndex;

    /**
     * Focused arc information.
     */
    private String focusedArcName, focusedArcDesc, focusedArcValue;

    /**
     * mvc.Model's total values.
     */
    private String totalValue;

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
     * Initializes the view's attributes.
     */
    private void initialize() {
        blankCircle = createArc(0, 360, Arc2D.OPEN, BLANK_CIRCLE_RADIUS);
        titleCircle = createArc(0, 360, Arc2D.OPEN, TITLE_CIRCLE_RADIUS);
        dragCircle  = createArc(0, 360, Arc2D.OPEN, ARC_RADIUS);

        boxWidth = 100;
        boxHeight = 40;
        defaultOffset = 5;

        focusedArcIndex = -1;
        focusedArcDesc = "";
        focusedArcName = "";
        focusedArcValue = "";
    }

    /**
     * Gets the camembert's position on x-axis.
     * @return originX
     */
    public int getOriginX() {
        return originX;
    }

    /**
     * Sets the camembert's position on x-axis.
     * @param originX
     */
    public void setOriginX(int originX) {
        this.originX = originX;
    }

    /**
     * Gets the camembert's position on y-axis.
     * @return
     */
    public int getOriginY() {
        return originY;
    }

    /**
     * Sets the camembert's position on y-axis.
     * @param originY
     */
    public void setOriginY(int originY) {
        this.originY = originY;
    }

    /**
     * Gets the arcList.
     * @return the arcList
     */
    public ArrayList<Arc2D> getArcList() {
        return arcList;
    }

    /**
     * Sets the arcList.
     * @param arcList
     */
    public void setArcList(ArrayList<Arc2D> arcList) {
        this.arcList = arcList;
    }

    /**
     * Gets the center circle.
     * @return the center circle
     */
    public Arc2D getBlankCircle() {
        return blankCircle;
    }

    /**
     * Sets the center circle.
     * @param blankCircle
     */
    public void setBlankCircle(Arc2D blankCircle) {
        this.blankCircle = blankCircle;
    }

    /**
     * Gets the circle used for the drag action.
     * @return drag circle
     */
    public Arc2D getDragCircle() {
        return dragCircle;
    }

    /**
     * Sets the circle used for the drag action.
     * @param dragCircle
     */
    public void setDragCircle(Arc2D dragCircle) {
        this.dragCircle = dragCircle;
    }

    /**
     * Gets the default start angle.
     * @return defaultStartAngle
     */
    public double getDefaultStartAngle() {
        return defaultStartAngle;
    }

    /**
     * Sets the default start angle.
     * @param defaultStartAngle
     */
    public void setDefaultStartAngle(double defaultStartAngle) {
        this.defaultStartAngle = defaultStartAngle;
    }

    /**
     * Gets the selected arc index.
     * @return the selected arc index
     */
    public int getFocusedArcIndex() {
        return focusedArcIndex;
    }

    /**
     * Sets the selected arc's index
     * @param focusedArcIndex
     */
    public void setFocusedArcIndex(int focusedArcIndex) {
        this.focusedArcIndex = focusedArcIndex;
    }

    /**
     * Get the selected arc's name.
     * @return the selected arc's name
     */
    public String getFocusedArcName() {
        return focusedArcName;
    }

    /**
     * Sets the selected arc's name.
     * @param focusedArcName
     */
    public void setFocusedArcName(String focusedArcName) {
        this.focusedArcName = focusedArcName;
    }

    /**
     * Gets the selected arc's description.
     * @return
     */
    public String getFocusedArcDesc() {
        return focusedArcDesc;
    }

    /**
     * Sets the selected arc's description.
     * @param focusedArcDesc
     */
    public void setFocusedArcDesc(String focusedArcDesc) {
        this.focusedArcDesc = focusedArcDesc;
    }

    /**
     * Gets the selected arc's value.
     * @return the selected arc's value
     */
    public String getFocusedArcValue() {
        return focusedArcValue;
    }

    /**
     * Sets the selected arc's value.
     * @param focusedArcValue
     */
    public void setFocusedArcValue(String focusedArcValue) {
        this.focusedArcValue = focusedArcValue;
    }

    /**
     * Gets the camembert's title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the camembert's title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Method called when the observable object is modified
     * @param observable
     */
    @Override
    public void update(Observable observable) {
        Model model = (Model) observable;

        title = model.getTitle();
        totalValue = "" + model.getTotalValue();
        computeArcs(model);
    }

    /**
     * Clears the MouseListener list.
     */
    public void clearMouseListeners() {
        for (MouseListener m : getMouseListeners()) {
            removeMouseListener(m);
        }
    }

    /**
     * Clears the MouseMotionListener list.
     */
    public void clearDragListeners() {
        for (MouseMotionListener m : getMouseMotionListeners()) {
            removeMouseMotionListener(m);
        }
    }

    public void computeArcs(Model model) {

        arcList.clear();

        // Arcs settings
        double angleStart = defaultStartAngle, angleEnd;

        // Compute the arcs
        for (int i = 0; i < model.getValues().size(); i++) {

            // Compute the arc angle from the model values
            angleEnd = 360 * model.getValueAsPercent(i);

            // Create the arc from the angle values
            arcList.add(createArc(angleStart, angleEnd, Arc2D.PIE, ARC_RADIUS));

            angleStart += angleEnd;
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
     * Resizes an arc with a new radius value
     *
     * @param arc
     * @param radius
     * @return The resized arc
     */
    public Arc2D resizeArc(Arc2D arc, double radius) {
        return createArc(arc.getAngleStart(), arc.getAngleExtent(), arc.getArcType(), radius);
    }

    /**
     * Focuses the next arc in the list.
     */
    public void next() {
        if (focusedArcIndex > -1) {
            focusedArcIndex = (focusedArcIndex + 1) % arcList.size();
            controller.updateViewInfo();
            repaint();
        }
    }

    /**
     * Focuses the previous arc in the list.
     */
    public void previous() {
        if (focusedArcIndex > -1) {
            if (--focusedArcIndex < 0)
                focusedArcIndex = arcList.size() - 1;
            controller.updateViewInfo();
            repaint();
        }
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
            String boxContent = focusedArcDesc.length() > 0
                    ? focusedArcName + " " + focusedArcDesc + " (" + focusedArcValue + ")"
                    : focusedArcName + " (" + focusedArcValue + ")";
            
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
     * Draws the camembert.
     *
     * @param g
     */
    @Override
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

        int valueWidth = graph.getFontMetrics().stringWidth(totalValue);
        int valueHeight = graph.getFontMetrics().getHeight();

        // Shows the total of the model's values at the center of the camembert
        graph.drawString(totalValue, originX - valueWidth / 2, originY + valueHeight + defaultOffset);
    }
}
