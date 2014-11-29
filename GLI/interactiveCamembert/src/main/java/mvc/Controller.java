package mvc;

import listeners.ClickListener;
import listeners.DragListener;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class Controller {

    private Model model;
    private View view;

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
        this.view.addMouseListener(new ClickListener(this));
        this.view.addMouseListener(new DragListener(this));
    }

    /**
     * Gets the description from the model and binds it to the view.
     */
    public void updateViewInfo() {
        int focusedArcIndex = view.getFocusedArcIndex();

        String name = model.getFields().get(focusedArcIndex);
        String description = model.getDescriptions().get(focusedArcIndex);
        String value = String.format("%.2f", model.getValueAsPercent(focusedArcIndex) * 100) + " %";

        view.setFocusedArcInfo(name, description, value);
    }

    public void showNext() {
        view.next();
    }

    public void showPrevious() {
        view.previous();
    }
}
