import java.util.ArrayList;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class Model extends Observable {
    private String title;
    private ArrayList<String> fields;
    private ArrayList<String> descriptions;
    private ArrayList<Integer> values;

    public Model() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
        notifyObservers();
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
        notifyObservers();
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
        notifyObservers();
    }
}
