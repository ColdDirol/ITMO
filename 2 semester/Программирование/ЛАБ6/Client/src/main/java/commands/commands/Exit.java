package commands.commands;

public class Exit implements Command {
    public void exit() {
        System.out.println("All the best...");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit";
    }

    @Override
    public String description() {
        return "The \"exit\" command is used to clear the collection for clear the collection.";
    }
}
