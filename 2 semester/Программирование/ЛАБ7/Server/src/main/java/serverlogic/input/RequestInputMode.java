package serverlogic.input;

import serverlogic.ServerLogicInterface;

public enum RequestInputMode implements ServerLogicInterface { // обстановка
    CLIENT("CLIENT"),
    SERVER("SERVER");

    private String title;

    RequestInputMode(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public String description() {
        return "The RequestInputMode is used to protect the secured server commands from the Client.";
    }
}
