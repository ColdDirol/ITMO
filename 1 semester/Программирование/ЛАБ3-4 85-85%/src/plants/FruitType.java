package plants;

public enum FruitType {
    GREEN_APPLE("Зеленые яблочки"),
    LUNAR_PEAR("Лунные груши"),
    LUNAR_RESPBERRY("Лунная малина"),
    EARTHLY_RESPBERRY("Земная малина"),
    WITHOUT_FRUITS("Ничего");

    private String title;

    FruitType(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
