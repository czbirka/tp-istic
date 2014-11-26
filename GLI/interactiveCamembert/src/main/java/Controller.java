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
     * Binds the view to the controller.
     * @param view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Gets the description from the model and binds it to the view.
     * @param focusedArcIndex
     */
    public void setViewInfo(int focusedArcIndex) {
        String name = model.getFields().get(focusedArcIndex);
        String description = model.getDescriptions().get(focusedArcIndex);
        String value = model.getValues().get(focusedArcIndex) + " %";

        view.setFocusedArcInfo(name, description, value);
    }
}
