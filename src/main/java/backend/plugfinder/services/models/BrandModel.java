package backend.plugfinder.services.models;

public class BrandModel {
    //region Atributes
    private String name;
    private boolean known;
    //endregion

    //region Getter & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }
    //endregion
}
