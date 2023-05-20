package spaceobjects.satellites;

public enum Seasons {
    WINTER("Зима"),
    SPRING("Весна"),
    SUMMER("Лето"),
    FALL("Осень");

    private String title;

    Seasons(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
