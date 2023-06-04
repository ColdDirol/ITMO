package commands.commands.secured;

import commands.commands.CommandInterface;
import exceptions.FileHasBeenDeletedException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import xmlactions.XmlWriter;

public class SaveExit implements CommandInterface {
    XmlWriter xmlWriter = new XmlWriter();
    private Logger logger = LogManager.getLogger(Save.class.getSimpleName());

    public void saveExit() throws FileHasBeenDeletedException {
        xmlWriter.xmlWrite();
        System.out.println("Flat catalog has been saved!");
        logger.info("Flat catalog has been saved!");

        System.out.println("Server stopped!");
        System.exit(0);
    }

    @Override
    public String getCommandName() {
        return "save_exit";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to save collection to the XML file and stop the Server.";
    }
}
