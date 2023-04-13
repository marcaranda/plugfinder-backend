package backend.plugfinder.controllers.dto;

public class BrandDto {
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
