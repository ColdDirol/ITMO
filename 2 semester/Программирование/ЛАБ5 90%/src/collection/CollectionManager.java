package collection;

import collection.flat.Flat;
import org.xml.sax.SAXException;
import xmlactions.XmlReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The class CollectionManager allows to Read collection from XML file
 */
public class CollectionManager {
    Collection collection = new Collection();
    XmlReader xmlReader = new XmlReader();
    ArrayList<Flat> flatArrayList;

    /**
     * The method collectionReader() is method for Read and Remember information from XML file
     *
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void collectionReader() throws ParserConfigurationException, IOException, SAXException { // main execution method in the program
        flatArrayList = xmlReader.xmlRead();

        for(int i = 0; i < flatArrayList.size(); i++){
            Flat flat = flatArrayList.get(i);
            Integer id = flat.getId();
            collection.setHashtableElements(id, flat);
        }
    }

    public Collection getCollection() {
        return collection;
    }
}
