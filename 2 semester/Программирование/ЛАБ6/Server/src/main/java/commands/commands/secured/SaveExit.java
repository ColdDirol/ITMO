package commands.commands.secured;

import commands.commands.CommandInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.PrepareToStop;
import serverlogic.output.ResponseArrayList;
import xmlactions.XmlWriter;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class SaveExit implements CommandInterface {
    XmlWriter xmlWriter = new XmlWriter();
    private Logger logger = LogManager.getLogger(Save.class.getSimpleName());
    PrepareToStop prepareToStop = new PrepareToStop();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void saveExit(SocketChannel clientChannel) throws IOException {
        xmlWriter.xmlWrite();
        System.out.println("Flat catalog has been saved!");
        logger.info("Flat catalog has been saved!");

        System.out.println("Server stopped!");

        responseArrayList.addElementToTheResponseArrayList("STOP");

        prepareToStop.setPrepareToStop(true);
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
