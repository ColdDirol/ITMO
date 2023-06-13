package commands.commandsmanager;

public abstract class CommandsManagerAbstract implements CommandsManagerInterface {
    public String getCommand(String fullCommand) {
        String command = "";

        for (int i = 0; i < fullCommand.length(); i++) {
            if (fullCommand.charAt(i) != ' ') command += fullCommand.charAt(i);
            else break;
        }

        return command;
    }

    public String getAttribute(String fullCommand) {
        int cnt = 0;
        String attribute = "";

        for (int i = 0; i < fullCommand.length(); i++) {
            if (fullCommand.charAt(i) == ' ') cnt = i;
            if (cnt > 0 & i > cnt) attribute += fullCommand.charAt(i);
        }
        return attribute;
    }
}
