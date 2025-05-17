package placable;

public enum MyObjectTypes {
    GROUND("."),
    ZONE("-"),
    CASTLE("M"),
    ROAD("="),
    COIN("@"),
    ARTEFACT("!"),
    WALL("#"),
    HOLYGRAIL("Y"),
    TUNNEL("O"),
    BARBER("B"),
    HOTEL("K"),
    CAFE("C");


    private final String symbol;

    MyObjectTypes(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}