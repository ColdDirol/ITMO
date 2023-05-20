package plants;

public enum FruitFlavorType {
    SOUR("Кислый"),
    SWEET("Сладкий"),
    WITHOUT_FLAVOR("Без вкуса");

    private String title;

    FruitFlavorType(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
