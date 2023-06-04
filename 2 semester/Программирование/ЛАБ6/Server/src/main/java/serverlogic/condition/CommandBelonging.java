package serverlogic.condition;

import serverlogic.ServerLogicInterface;

public class CommandBelonging implements ServerLogicInterface {
    private CommandsSet commandsSet = new CommandsSet();

    public boolean isCommonSideCommand(String command) {
        boolean result = commandsSet.getCommonSideCommandsSet().stream().filter(element -> element.equals(command)).findFirst().isPresent();
        return result;
    }

    public boolean isServerSideCommand(String command) {
        boolean result = commandsSet.getServerSideCommandsSet().stream().filter(element -> element.equals(command)).findFirst().isPresent();
        return result;
    }

    public boolean isSecuredServerSideCommands(String command) {
        boolean result = commandsSet.getSecuredServerSideCommands().stream().filter(element -> element.equals(command)).findFirst().isPresent();
        return result;
    }

    @Override
    public String description() {
        return "The CommandBelonging is used to get boolean value about command contains in the CommandsSet.";
    }
}
