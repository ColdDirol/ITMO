package clientlogic;

public enum ConsoleOutputMode { // обстановка
    READABLE("ЧИТАЕМЫЙ"),
    UNREADABLE("НЕЧИТАЕМЫЙ");

    private String title;

    ConsoleOutputMode(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
