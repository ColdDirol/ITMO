package plants;

public enum LeavesTypes {
    GREEN_LEAVES("Зеленые листья"),
    YELLOW_LEAVES("Желтые листья"),
    RED_LEAVES("Красные листья"),
    PINK_LEAVES("Розовые листья");

    private String title;

    LeavesTypes(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
