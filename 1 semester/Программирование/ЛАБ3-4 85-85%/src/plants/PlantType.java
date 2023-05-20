package plants;

public enum PlantType {
    TREE("Дерево"),
    BUSH("Куст");

    private String title;

    PlantType(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
