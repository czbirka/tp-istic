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
     * List containing all the data.
     */
    private ArrayList<Field> fields;

    /**
     * Constructor.
     */
    public Model() {
        super();
        fields = new ArrayList<Field>();
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
     * Adds an entry in the model.
     * @param fieldName
     * @param fieldDescription
     * @param fieldValue
     */
    public void addField(String fieldName, String fieldDescription, float fieldValue) {
        fields.add(new Field(fieldName, fieldDescription, fieldValue));
        notifyObservers();
    }

    /**
     * Adds an entry in the model.
     * @param field
     */
    public void addField(Field field) {
        fields.add(field);
        notifyObservers();
    }

    /**
     * Gets the data list.
     * @return All the fields.
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    /**
     * Gets the field at the specified index.
     * @param index
     * @return A specific field.
     */
    public Field getField(int index) {
        return fields.get(index);
    }

    /**
     * Sets the field at the specified index.
     * @param index
     * @param field
     */
    public void setField(int index, Field field) {
        fields.set(index, field);
        notifyObservers();
    }

    /**
     * Gets the name of the specified field.
     * @param index
     * @return The field's name.
     */
    public String getFieldName(int index) {
        return fields.get(index).getName();
    }

    /**
     * Gets the description of the specified field.
     * @param index
     * @return The field's description.
     */
    public String getFieldDescription(int index) {
        return fields.get(index).getDescription();
    }

    /**
     * Gets the number of fields.
     * @return fields' size.
     */
    public int fieldCount() {
        return fields.size();
    }

    /**
     * Gets the sum of all values.
     * @return sum of all values
     */
    public float getTotalValue() {
        float total = 0;

        for (int i = 0; i < fields.size(); i++) {
            total += fields.get(i).getValue();
        }

        return total;
    }

    /**
     * Gets a value in percent.
     * @param index
     * @return value in percent
     */
    public float getValueAsPercent(int index) {
        return fields.get(index).getValue() / getTotalValue();
    }
}
