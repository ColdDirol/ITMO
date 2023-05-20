import collection.CollectionManager;
import commands.CommandsManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        CollectionManager collectionManager = new CollectionManager();
        collectionManager.collectionReader();

        CommandsManager commandsManager = new CommandsManager();

        while(true){
            try {
                System.out.print("Enter the command: ");
                String input = bufferedReader.readLine();
                commandsManager.executeCommand(input);
            } catch (NullPointerException e) {
                System.exit(0);
            }
        }
    }
}