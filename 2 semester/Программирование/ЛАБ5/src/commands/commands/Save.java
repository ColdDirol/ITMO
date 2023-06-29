package commands.commands;

import collection.flat.Flat;
import exceptions.FileHasBeenDeletedException;
import xmlactions.XmlReader;
import xmlactions.XmlWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Map;

/**
 * The class Save is used to execute the *save* command
 */
public class Save {
    XmlWriter xmlWriter = new XmlWriter();
    public void save() throws ParserConfigurationException, TransformerException, FileHasBeenDeletedException {
        xmlWriter.xmlWrite();
    }
}
