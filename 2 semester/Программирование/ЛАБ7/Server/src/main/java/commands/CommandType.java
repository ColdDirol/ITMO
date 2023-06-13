package commands;

/**
 * Interface CommandType has only one method - void execute(). This method helps contain value with runnable method in Map(String, CommandType) commandsMap in the CommandsManager class.
 */
public interface CommandType {
    void execute();
}
