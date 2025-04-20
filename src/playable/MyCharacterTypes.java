package playable;

public enum MyCharacterTypes {
    HERO("H"),
    VIKING("U"),
    HEALER("U"),
    ARCHER("U"),
    SKELETON("S");

    private final String symbol;

    MyCharacterTypes(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
