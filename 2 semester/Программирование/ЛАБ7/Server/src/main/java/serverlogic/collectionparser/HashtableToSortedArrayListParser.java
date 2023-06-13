package serverlogic.collectionparser;

import collection.flat.Flat;
import serverlogic.ServerLogicInterface;

import java.util.*;

public class HashtableToSortedArrayListParser implements ServerLogicInterface {
    public static ArrayList<Map.Entry<Integer, Flat>> sortByFlatName(Hashtable<Integer, Flat> hashtable) {
        ArrayList<Map.Entry<Integer, Flat>> entryArrayList = new ArrayList<>(hashtable.entrySet());
        Collections.sort(entryArrayList, (o1, o2) -> o1.getValue().getName().compareTo(o2.getValue().getName()));
        return entryArrayList;
    }

    @Override
    public String description() {
        return "The HashtableToSortedArrayListParser is used to sort collection alphabetically.";
    }
}
