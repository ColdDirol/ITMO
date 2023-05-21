package commands.commands;

import exceptions.FileHasBeenDeletedException;
import serverlogic.ResponseArrayList;
import xmlactions.XmlWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Save {
    XmlWriter xmlWriter = new XmlWriter();
    ResponseArrayList responseArrayList = new ResponseArrayList();
    public void save() throws ParserConfigurationException, TransformerException, FileHasBeenDeletedException {
        xmlWriter.xmlWrite();
        responseArrayList.addElementToTheResponseArrayList("Flat catalog has been saved!");
    }
}
