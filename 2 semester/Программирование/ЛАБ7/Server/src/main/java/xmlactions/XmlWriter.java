package xmlactions;

import collection.ServerCollection;
import collection.flat.Flat;
import exceptions.FileHasBeenDeletedException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Hashtable;

/**
 * The XmlWriter class is parser from Hashtable Flat object collection back to the XML.
 */
public class XmlWriter implements XmlMovie {
    ServerCollection serverCollection = new ServerCollection();
    Hashtable<Integer, Flat> hashtable = serverCollection.getHahstable();
    // получить набор всех значений
    java.util.Collection<Flat> valuesSet = hashtable.values();
    String filepath;
    File file;
    public XmlWriter() {
        filepath = System.getenv("LAB7");
        file = new File(filepath);
    }
    public void xmlWrite() throws FileHasBeenDeletedException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElementNS("", "flat_catalog");

            document.appendChild(rootElement);

            for (Flat value : valuesSet) rootElement.appendChild(getFlat(document, value));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            StreamResult toFile = new StreamResult(file);

            transformer.transform(source, toFile);
            System.out.println("Saving the catalog to XML is complete");
        } catch (ParserConfigurationException parserConfigurationException) {
            System.out.println("The data was not saved to the file correctly " + filepath + "!");
            System.out.println("Quitting the program will result in a total loss of data!");
        } catch (TransformerException transformerException) {
            System.out.println("The data was not saved to the file correctly " + filepath + "!");
            System.out.println("Quitting the program will result in a total loss of data!");
        } finally {
            if(!file.exists()) {
                throw new FileHasBeenDeletedException(filepath);
            }
        }

    }

    private static Node getFlat(Document document, Flat flat) {
        Element flatElement = document.createElement("flat");
        Element coordinatesElement = document.createElement(("coordinates"));
        Element houseElement = document.createElement(("house"));

        // устанавливаем атрибут во flat
        flatElement.setAttribute("id", Integer.toString(flat.getId()));
        // работа с дочерними нодами flat
        flatElement.appendChild(getNodeElements(document, "name", flat.getName()));
        // работа с дочерними нодами coordinates
        coordinatesElement.appendChild((getNodeElements(document, "x", Float.toString(flat.getCoordinatesX()))));
        coordinatesElement.appendChild((getNodeElements(document, "y", Float.toString(flat.getCoordinatesY()))));

        // запись ноды coordinates во flat
        flatElement.appendChild(coordinatesElement);

        // работа с дочерними нодами flat
        flatElement.appendChild(getNodeElements(document, "creation_date", flat.getCreationDate().toString()));
        flatElement.appendChild(getNodeElements(document, "area", Float.toString(flat.getArea())));
        flatElement.appendChild(getNodeElements(document, "numbers_of_rooms", Long.toString(flat.getNumberOfRooms())));
        flatElement.appendChild(getNodeElements(document, "furnish", flat.getFurnish().toString()));
        flatElement.appendChild(getNodeElements(document, "view", flat.getView().toString()));
        flatElement.appendChild(getNodeElements(document, "transport", flat.getTransport().toString()));
        // работа с аттрибутами house
        houseElement.setAttribute("name", flat.getHouseName());
        houseElement.setAttribute("year", Integer.toString(flat.getHouseYear()));
        houseElement.setAttribute("number_of_lifts", Integer.toString(flat.getHouseNumberOfLifts()));
        // запись ноды house во flat
        flatElement.appendChild(houseElement);

        return flatElement;
    }

    private static Node getNodeElements(Document document, String name, String value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));
        return node;
    }

    @Override
    public String description() {
        return "The \"XmlWriter\" class is used to parse the static HashTable from ServerCollection class from Collection to XML format (to the chose ENVIRONMENT_VARIABLE).";
    }
}
