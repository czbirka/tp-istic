package mvc;

import observer.Observable;

import java.util.ArrayList;

/**
 * Created by thomas & amona on 13/10/14.
 */
public class Model extends Observable {

    /**
     * Camembert's title.
     */
    private String title;

    /**
     * List containing all the fields names.
     */
    private ArrayList<String> fields;

    /**
     * List containing all the fields description.
     */
    private ArrayList<String> descriptions;

    /**
     * List containing all the fields values.
     */
    private ArrayList<Float> values;

    /**
     * Constructor.
     */
    public Model() {
        super();

        fields = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        values = new ArrayList<Float>();
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
        notifyObservers();
    }

    /**
     * Gets the fields list.
     * @return the fields list
     */
    public ArrayList<String> getFields() {
        return fields;
    }

    /**
     * Sets the fields list.
     * @param fields
     */
    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
        notifyObservers();
    }

    /**
     * Gets the descriptions list.
     * @return the descriptions list
     */
    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    /**
     * Sets the descriptions list.
     * @param descriptions
     */
    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
        notifyObservers();
    }

    /**
     * Gets the values list.
     * @return the values list
     */
    public ArrayList<Float> getValues() {
        return values;
    }

    /**
     * Sets the values list.
     * @param values
     */
    public void setValues(ArrayList<Float> values) {
        this.values = values;
        notifyObservers();
    }

    /**
     * Add an entry in the model.
     * @param fieldName
     * @param fieldDescription
     * @param fieldValue
     */
    public void addField(String fieldName, String fieldDescription, float fieldValue) {
        fields.add(fieldName);
        descriptions.add(fieldDescription);
        values.add(fieldValue);
    }

    /**
     * Gets the sum of all values.
     * @return sum of all values
     */
    public float getTotalValue() {
        float total = 0;

        for (int i = 0; i < values.size(); i++) {
            total += values.get(i);
        }

        return total;
    }

    /**
     * Gets a value in percent.
     * @param index
     * @return value in percent
     */
    public float getValueAsPercent(int index) {
        return values.get(index) / getTotalValue();
    }
}
