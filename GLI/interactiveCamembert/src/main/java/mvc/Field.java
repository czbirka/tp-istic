package mvc;


/**
 * Created by thomas & amona on 20/11/14.
 */
public class Field {

    private String name;
    private String description;
    private Float value;

    public Field() {
        name = new String();
        description = new String();
        value = new Float(-1);
    }

    public Field(String name, String description, Float value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
