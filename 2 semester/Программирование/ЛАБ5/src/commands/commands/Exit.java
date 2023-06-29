package commands.commands;

/**
 * The class Exit is used to execute the *exit* command
 */
public class Exit {
    public void exit(){
        System.out.println("All the best...");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit";
    }
}
