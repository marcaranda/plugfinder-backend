package backend.plugfinder.helpers;

public enum Zones {
    BARCELONA("Barcelona"),
    TARRAGONA("Tarragona"),
    GERONA("Gerona"),
    LERIDA("Lerida");

    private final String name;

    Zones(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


