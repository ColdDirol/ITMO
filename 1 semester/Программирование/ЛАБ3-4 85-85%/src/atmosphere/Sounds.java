package atmosphere;

public enum Sounds {
    SAD("*Грустная музыка*"),
    HAPPY("*Веселая музыка*"),
    EPIC("*Эпическая музыка*"),
    PUNK("*Издалека послышался панк-рок*"),
    ELECTRO("*Жарит электро*"),
    RAIN("*Звук дождя*"),
    DRUMROLL("*Барабанная дробь*");

    private String title;

    Sounds(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
