package commands.commands.secured.history;

import collection.flat.Flat;
import commands.commands.CommandInterface;
import history.HistoryCollection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class ShowHistory implements CommandInterface {
    HistoryCollection historyCollection = new HistoryCollection();
    Logger logger = LogManager.getLogger(ShowHistory.class.getSimpleName());

    String id;
    String changeDate;
    String numberOfObjects;

    public void showHistory() {
        ArrayList<Map.Entry<Map.Entry<Integer, LocalDateTime>, Hashtable<Integer, Flat>>> historyEntryArrayList = new ArrayList<>(historyCollection.getHistoryLinkedHashMap().entrySet());

        historyEntryArrayList.stream().forEach(entry -> {
            id = "~" + entry.getKey().getKey();
            System.out.println(id);
            logger.info(id);


            changeDate = "Changed: "
                        + entry.getKey().getValue().getHour() + ":"
                        + entry.getKey().getValue().getMinute() + " "
                        + entry.getKey().getValue().getDayOfMonth() + " "
                        + entry.getKey().getValue().getMonth() + " "
                        + entry.getKey().getValue().getYear();
            System.out.println(changeDate);
            logger.info(changeDate);


            numberOfObjects = "Number of objects: " + entry.getValue().size();
            System.out.println(numberOfObjects);
            logger.info(numberOfObjects);
        });
    }

    @Override
    public String getCommandName() {
        return "show_history";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to show short information about history of changes.";
    }
}