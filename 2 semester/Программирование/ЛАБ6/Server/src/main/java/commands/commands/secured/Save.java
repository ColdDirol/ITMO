package commands.commands.secured;

import commands.commands.CommandInterface;
import exceptions.FileHasBeenDeletedException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import serverlogic.output.ResponseArrayList;
import xmlactions.XmlWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Save implements CommandInterface {
    XmlWriter xmlWriter = new XmlWriter();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    private Logger logger = LogManager.getLogger(Save.class.getSimpleName());
    public void save() throws ParserConfigurationException, TransformerException, FileHasBeenDeletedException {
        xmlWriter.xmlWrite();
        //responseArrayList.addElementToTheResponseArrayList("Flat catalog has been saved!");
        System.out.println("Flat catalog has been saved!");
        logger.info("Flat catalog has been saved!");
    }

    @Override
    public String getCommandName() {
        return "save";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to save collection to the XML file.";
    }
}
