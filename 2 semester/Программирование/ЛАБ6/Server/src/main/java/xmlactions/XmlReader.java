package xmlactions;

import collection.flat.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The XmlReader class is parser from XML to Hashtable Flat object collection.
 */
public class XmlReader implements XmlMovie {
    String filepath;
    File file;
    public XmlReader() {
        filepath = System.getenv("LAB6");
        file = new File(filepath);
    }

    public ArrayList<Flat> xmlRead() throws ParserConfigurationException, SAXException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
        ArrayList<Flat> flatsArrayList = new ArrayList<>();

        NodeList flatNodeList = document.getElementsByTagName("flat");
        for(int i = 0; i < flatNodeList.getLength(); i++){
            if(flatNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element flatElement = (Element) flatNodeList.item(i);

                Integer id = 0;
                String name = "";
                float x = 0;
                float y = 0;
                LocalDateTime creationDate;
                float area = 0;
                Long numberOfRooms = Long.valueOf(0);
                Furnish furnish = null;
                View view = null;
                Transport transport = null;
                String houseName = "";
                int houseYear = 0;
                Integer houseNumberOfLifts = 0;

                Flat flat = new Flat();
                id = Integer.parseInt(flatElement.getAttribute("id"));
                if (id == 0 | id < 0) {
                    System.out.println("ERROR! Invalid value in the *id* field");
                    System.out.println("The file could not be read.");
                    System.exit(0);
                } else flat.setId(id);

                NodeList flatChildNodes = flatElement.getChildNodes();
                for(int j = 0; j < flatChildNodes.getLength(); j++){
                    if(flatChildNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                        Element flatChildElement = (Element) flatChildNodes.item(j);

                        switch (flatChildElement.getNodeName()){
                            case "name" : {
                                name = flatChildElement.getTextContent();
                                if(name.isEmpty()) {
                                    System.out.println("ERROR! Invalid value in the *name* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setName(name);
                            } break;

                            case "coordinates" : {
                                NodeList coordinatesNodeList = document.getElementsByTagName("coordinates");
                                for(int k = 0; k < coordinatesNodeList.getLength(); k++){
                                    if(coordinatesNodeList.item(k).getNodeType() == Node.ELEMENT_NODE){
                                        Element coordinatesElement = (Element) coordinatesNodeList.item(k);

                                        NodeList coordinatesChildNodes = coordinatesElement.getChildNodes();
                                        for(int l = 0; l < coordinatesChildNodes.getLength(); l++) {
                                            if(coordinatesChildNodes.item(l).getNodeType() == Node.ELEMENT_NODE){
                                                Element coordinatesChildElement = (Element) coordinatesChildNodes.item(l);

                                                switch (coordinatesChildElement.getNodeName()) {
                                                    case "x" : {
                                                        x = Float.valueOf(coordinatesChildElement.getTextContent());
                                                        flat.setCoordinatesX(x);
                                                    } break;

                                                    case "y" : {
                                                        y = Float.valueOf(coordinatesChildElement.getTextContent());
                                                        flat.setCoordinatesY(y);
                                                    } break;
                                                }
                                            }
                                        }
                                    }
                                }
                            } break;

                            case "creation_date" : {
                                creationDate = LocalDateTime.parse(flatChildElement.getTextContent());
                                if(creationDate.equals(null)) {
                                    System.out.println("ERROR! Invalid value in the *creation_date* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setCreationDate(creationDate);
                            } break;

                            case "area" : {
                                area = Float.parseFloat(flatChildElement.getTextContent());
                                if(area < 0) {
                                    System.out.println("ERROR! Invalid value in the *area* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setArea(area);
                            } break;

                            case "numbers_of_rooms" : {
                                numberOfRooms = Long.parseLong(flatChildElement.getTextContent());
                                if(numberOfRooms < 0 | numberOfRooms > 7) {
                                    System.out.println("ERROR! Invalid value in the *number_of_rooms* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setNumberOfRooms(numberOfRooms);
                            } break;

                            case "furnish" : {
                                furnish = Furnish.valueOf(flatChildElement.getTextContent());
                                if(furnish.equals(null)) {
                                    System.out.println("ERROR! Invalid value in the *furnish* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setFurnish(furnish);
                            } break;

                            case "view" : {
                                view = View.valueOf(flatChildElement.getTextContent());
                                if(view.equals(null)) {
                                    System.out.println("ERROR! Invalid value in the *view* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setView(view);
                            } break;

                            case "transport" : {
                                transport = Transport.valueOf(flatChildElement.getTextContent());
                                if(view.equals(null)) {
                                    System.out.println("ERROR! Invalid value in the *transport* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setTransport(transport);
                            } break;

                            case "house" : {
                                houseName = flatChildElement.getAttribute("name");
                                if(houseName.equals(null)) {
                                    System.out.println("ERROR! Invalid value in the *house_name* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setHouseName(houseName);
                                houseYear = Integer.parseInt(flatChildElement.getAttribute("year"));
                                if(houseYear < 0 | houseYear > 874) {
                                    System.out.println("ERROR! Invalid value in the *house_year* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setHouseYear(houseYear);
                                houseNumberOfLifts = Integer.valueOf(flatChildElement.getAttribute("number_of_lifts"));
                                if(houseNumberOfLifts < 0) {
                                    System.out.println("ERROR! Invalid value in the *house_number_of_lifts* field");
                                    System.out.println("The file could not be read.");
                                    System.exit(0);
                                }
                                flat.setHouseNumberOfLifts(houseNumberOfLifts);
                            }
                        }
                    }
                }
                flatsArrayList.add(flat);
            }
        }
        return flatsArrayList;
        } catch (IOException e) {
            System.err.println("The file " + filepath + " does not exist!");
            System.out.println(e.getMessage());
            System.exit(0);
            return null;
        }
    }

    @Override
    public String description() {
        return "The \"XmlReader\" class is used to parse the collection from XML format to the static HashTable from ServerCollection class (to the chose ENVIRONMENT_VARIABLE).";
    }
}
