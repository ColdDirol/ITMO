package history;

import collection.flat.Flat;

import java.time.LocalDateTime;
import java.util.*;

public class HistoryCollection {
    private static LinkedHashMap<Map.Entry<Integer, LocalDateTime>, Hashtable<Integer, Flat>> historyLinkedHashMap = new LinkedHashMap<Map.Entry<Integer, LocalDateTime>, Hashtable<Integer, Flat>>();
    private static Integer currentId = 1;

    public static LinkedHashMap<Map.Entry<Integer, LocalDateTime>, Hashtable<Integer, Flat>> getHistoryLinkedHashMap() {
        return historyLinkedHashMap;
    }

    public void addHistoryElement(Hashtable<Integer, Flat> hashtable) {
        Map.Entry<Integer, LocalDateTime> mapEntry = new AbstractMap.SimpleEntry<>(currentId, LocalDateTime.now());
        historyLinkedHashMap.put(mapEntry, new Hashtable<>(hashtable));
        currentId++;
    }
}
