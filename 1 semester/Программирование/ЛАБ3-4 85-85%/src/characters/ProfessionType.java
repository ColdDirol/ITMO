package characters;

public enum ProfessionType {
    SLACKER("Безработный"),
    PAINTER("Художник"),
    ASTRONOMER("Астороном"),
    MECHANIC("Механик"),
    HUNTER("Охотник"),
    MUSICIAN("Музыкант"),
    POET("Поэт"),
    DOCTOR("Доктор"),
    SCIENTIST("Ученый"),
    BIG_BOSS("БИГ БОСС"),
    JOURNALIST("Журналист");

    private String title;

    ProfessionType(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
