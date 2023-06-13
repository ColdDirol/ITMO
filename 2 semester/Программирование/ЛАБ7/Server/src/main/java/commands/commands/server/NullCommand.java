package commands.commands.server;

import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.condition.CommandBelonging;
import serverlogic.output.ResponseArrayList;

public class NullCommand implements CommandInterface {
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(NullCommand.class.getSimpleName());

    public void nullCommand() {
        int answer = (int) (Math.random() * 2);
        if(answer == 1) {
            System.out.println("next");
            logger.info("next");
        } else {
            System.out.println("wait");
            logger.info("wait");
        }
    }

    @Override
    public String getCommandName() {
        return "null_command";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to stop execution current command and request the next command from the client.";
    }
}
