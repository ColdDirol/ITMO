package atmosphere.weather;

public enum Direction {
    NORTH("Север"),
    SOUTH("Юг"),
    WEST("Запад"),
    EAST("Восток");
    private String title;

    Direction(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
