package mvc;

import components.AddFieldButton;
import components.CamembertTable;
import components.NextButton;
import components.PreviousButton;
import listeners.ClickListener;
import listeners.DragListener;
import observer.Observable;
import observer.Observer;
import utils.GlobalConfigs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class Controller implements Observer {

    private Model model;
    private View view;
    private PreviousButton previousButton;
    private NextButton nextButton;
    private CamembertTable table;

    /**
     * Constructor.
     * @param model
     */
    public Controller(Model model) {
        this.model = model;

        model.addField("Chrome", "By Google", 44.4f);
        model.addField("Internet explorer", "By Microsoft", 22.6f);
        model.addField("Firefox", "By Mozilla", 18.2f);
        model.addField("Safari", "By Apple", 9.8f);
        model.addField("Opera", "By Opera Software", 1.3f);
        model.addField("Other", "", 3.7f);
    }

    /**
     * Method called when the observable object is modified
     * @param observable
     */
    @Override
    public void update(Observable observable) {
        updateViewInfo();
    }

    /**
     * Gets the model.
     * @return model
     */
    public Model getModel() {
        return model;
    }

    /**
     * Gets the view.
     * @return the view
     */
    public View getView() {
        return this.view;
    }

    /**
     * Binds the view to the controller.
     * @param view
     */
    public void setView(View view) {
        this.view = view;
        resetListeners();
        initButtons();
        initTable();
    }

    /**
     * Initializes the next & previous buttons.
     */
    private void initButtons() {
        nextButton = new NextButton("<", this);
        previousButton = new PreviousButton(">", this);
        hideButtons();
        JPanel panel = new JPanel();
        panel.add(nextButton);
        panel.add(previousButton);
        view.getParent().add(panel, BorderLayout.SOUTH);
    }

    /**
     * Initializes the JTable.
     */
    private void initTable() {
        table = new CamembertTable(this);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension((int)(GlobalConfigs.WINDOW_WIDTH * .5), (int)(GlobalConfigs.WINDOW_HEIGHT * .2)));
        panel.add(scrollPane);
        panel.add(new AddFieldButton("Add", this));
        view.getParent().add(panel, BorderLayout.NORTH);
    }

    /**
     * Shows the buttons.
     */
    public void showButtons() {
        nextButton.setVisible(true);
        previousButton.setVisible(true);
    }

    /**
     * Hides the buttons.
     */
    public void hideButtons() {
        nextButton.setVisible(false);
        previousButton.setVisible(false);
    }

    /**
     * Gets the focused field information from the model and binds it to the view.
     */
    public void updateViewInfo() {
        int focusedArcIndex = view.getFocusedArcIndex();

        if (focusedArcIndex > -1) {
            String name = model.getFieldName(focusedArcIndex);
            String description = model.getFieldDescription(focusedArcIndex);
            String value = String.format("%.2f", model.getValueAsPercent(focusedArcIndex) * 100);

            view.setFocusedArcInfo(name, description, value);
            if (!table.isCellSelected(focusedArcIndex, 0))
                table.changeSelection(focusedArcIndex, 0, true, false);
        }
    }

    /**
     * Gets the model's fields list.
     * @return fields list.
     */
    public ArrayList<Field> getFields() {
        return model.getFields();
    }

    /**
     * Gets a specific field from the model.
     * @param index
     * @return field at the specified index.
     */
    public Field getField(int index) {
        return model.getField(index);
    }

    /**
     * Sets a specific field in the model.
     * @param index
     * @param field
     */
    public void setField(int index, Field field) {
        model.setField(index, field);
    }

    /**
     * Adds a field to the model.
     * @param field
     */
    public void addField(Field field) {
        model.addField(field);
    }

    /**
     * Focuses the next arc on the view's list.
     */
    public void showNext() {
        int focusedArcIndex = view.getFocusedArcIndex();
        ArrayList<Arc2D> arcList = view.getArcList();

        if (focusedArcIndex > -1) {
            view.setFocusedArcIndex((focusedArcIndex + 1) % arcList.size());
            updateViewInfo();
            view.repaint();
        }
    }

    /**
     * Focuses the previous arc on the view's list.
     */
    public void showPrevious() {
        int focusedArcIndex = view.getFocusedArcIndex();
        ArrayList<Arc2D> arcList = view.getArcList();

        if (focusedArcIndex > -1) {
            focusedArcIndex--;
            view.setFocusedArcIndex((focusedArcIndex < 0) ? (arcList.size() - 1) : focusedArcIndex);
            updateViewInfo();
            view.repaint();
        }
    }

    /**
     * Sets the index of the focused arc.
     * @param focusedArcIndex
     */
    public void setFocusedArcIndex(int focusedArcIndex) {
        view.setFocusedArcIndex(focusedArcIndex);
    }

    /**
     * Adds a ClickListener to the view.
     */
    private void addClickListener() {
        view.clearMouseListeners();
        view.addMouseListener(new ClickListener(this));
    }

    /**
     * Adds a DragListener to the view.
     */
    private void addDragListener() {
        view.clearMouseMotionListeners();
        view.addMouseListener(new DragListener(this));
    }

    /**
     * Resets the view's Listeners.
     */
    public void resetListeners() {
        addClickListener();
        addDragListener();
    }

    /**
     * Adds an empty row in the table.
     */
    public void createField() {
        table.addRow(new Field());
    }

    /**
     * Deselects any row selected from the table.
     */
    public void clearTableSelection() {
        table.clearSelection();
    }
}


