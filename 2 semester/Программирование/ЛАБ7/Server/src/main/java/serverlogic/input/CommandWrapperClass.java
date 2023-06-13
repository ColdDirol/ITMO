package serverlogic.input;

import serverlogic.ServerLogicInterface;

public class CommandWrapperClass implements ServerLogicInterface {

    // The fields in the WRAPPER
    // <
    private String command;
    private RequestInputMode requestInputMode;
    // >

    // The Getters and the Setters of the fields
    // <
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public RequestInputMode getRequestInputMode() {
        return requestInputMode;
    }
    public void setRequestInputMode(RequestInputMode requestInputMode) {
        this.requestInputMode = requestInputMode;
    }
    // >

    @Override
    public String description() {
        return "The CommandWrapperClass is used to make wrapper around command and its mode.";
    }
}
