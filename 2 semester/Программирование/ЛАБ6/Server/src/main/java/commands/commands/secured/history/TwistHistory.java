package commands.commands.secured.history;

import collection.ServerCollection;
import collection.flat.Flat;
import commands.commands.CommandInterface;
import history.HistoryCollection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class TwistHistory implements CommandInterface {
    ServerCollection serverCollection = new ServerCollection();
    HistoryCollection historyCollection = new HistoryCollection();
    Logger logger = LogManager.getLogger(TwistHistory.class.getSimpleName());

    public void twistHistory(Integer id) {
        ArrayList<Map.Entry<Map.Entry<Integer, LocalDateTime>, Hashtable<Integer, Flat>>> historyEntryArrayList = new ArrayList<>(HistoryCollection.getHistoryLinkedHashMap().entrySet());

        historyEntryArrayList.stream().forEach(entry -> {
            if(entry.getKey().getKey().equals(id)) serverCollection.setHashtable(entry.getValue());
        });

        historyCollection.addHistoryElement(serverCollection.getHahstable());

        System.out.println("You have returned to the previous changes!");
        logger.info("You have returned to the previous changes!");
    }

    @Override
    public String getCommandName() {
        return "twist_history";
    }

    @Override
    public String description() {
        return "The \"" + this.getCommandName() + "\" command is used to come back to the previous server collection that was changed.";
    }
}
