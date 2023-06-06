package backend.plugfinder.repositories;

public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
    private Object value2;

    private boolean B_key;

    public SearchCriteria(String key, String op, Object value, Object value2) {
        this.key = key;
        this.operation = op;
        this.value = value;
        this.value2 = value2;
    }

    //region Getters y setters

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }
}