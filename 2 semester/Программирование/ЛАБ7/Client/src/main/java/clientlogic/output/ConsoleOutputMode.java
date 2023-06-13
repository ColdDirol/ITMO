package clientlogic.output;

import clientlogic.ClientLogicInterface;

public enum ConsoleOutputMode implements ClientLogicInterface { // обстановка
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

    @Override
    public String description() {
        return "The ConsoleOutputMode class is used to define console output mode.";
    }
}
