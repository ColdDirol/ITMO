package commands.commands.server;

import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;

public class Echo implements CommandInterface {
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(Echo.class.getSimpleName());

    public void echo(String key) {
        responseArrayList.addElementToTheResponseArrayList(key);
        logger.info(key);
    }

    @Override
    public String getCommandName() {
        return "echo";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to send message with echo key back to the client.";
    }
}
