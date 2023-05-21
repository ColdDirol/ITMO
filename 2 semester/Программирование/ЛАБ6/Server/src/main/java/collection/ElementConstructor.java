package collection;

import collection.flat.Flat;
import collection.flat.Furnish;
import collection.flat.Transport;
import collection.flat.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementConstructor {
    private ArrayList<String> objectElementsArrayList = new ArrayList<>();
    ServerCollection serverCollection = new ServerCollection();
    Flat flat;

    public Flat elementConstructor(Integer id, String value) {
        flat = construct(value);
        flat.setId(id);
        flat.creationDateGenerator();

        return flat;
    }
    public Flat elementConstructor(String value) {
        flat = construct(value);
        flat.idGenerator();
        flat.creationDateGenerator();

        return flat;
    }

    public Flat construct(String value) {
        Flat flat = new Flat();

        objectElementsArrayList.clear();
        objectElementsArrayListFromStringParser(value);

        flat.setName(objectElementsArrayList.get(0));
        flat.setCoordinatesX(Float.valueOf(objectElementsArrayList.get(1)));
        flat.setCoordinatesY(Float.valueOf(objectElementsArrayList.get(2)));
        flat.setArea(Float.parseFloat(objectElementsArrayList.get(3)));
        flat.setNumberOfRooms(Long.parseLong(objectElementsArrayList.get(4)));

        if(Integer.parseInt(objectElementsArrayList.get(5)) == 1) flat.setFurnish(Furnish.DESIGNER);
        if(Integer.parseInt(objectElementsArrayList.get(5)) == 2) flat.setFurnish(Furnish.NONE);
        if(Integer.parseInt(objectElementsArrayList.get(5)) == 3) flat.setFurnish(Furnish.FINE);
        if(Integer.parseInt(objectElementsArrayList.get(5)) == 4) flat.setFurnish(Furnish.BAD);
        if(Integer.parseInt(objectElementsArrayList.get(5)) == 5) flat.setFurnish(Furnish.LITTLE);

        if(Integer.parseInt(objectElementsArrayList.get(6)) == 1) flat.setView(View.YARD);
        if(Integer.parseInt(objectElementsArrayList.get(6)) == 2) flat.setView(View.PARK);
        if(Integer.parseInt(objectElementsArrayList.get(6)) == 3) flat.setView(View.BAD);
        if(Integer.parseInt(objectElementsArrayList.get(6)) == 4) flat.setView(View.GOOD);

        if(Integer.parseInt(objectElementsArrayList.get(7)) == 1) flat.setTransport(Transport.FEW);
        if(Integer.parseInt(objectElementsArrayList.get(7)) == 2) flat.setTransport(Transport.NONE);
        if(Integer.parseInt(objectElementsArrayList.get(7)) == 3) flat.setTransport(Transport.ENOUGH);

        flat.setHouseName(objectElementsArrayList.get(8));
        flat.setHouseYear(Integer.parseInt(objectElementsArrayList.get(9)));
        flat.setHouseNumberOfLifts(Integer.valueOf(objectElementsArrayList.get(10)));

        return flat;
    }

    private void objectElementsArrayListFromStringParser(String value) {
        String item = "";
        System.out.println("To parse: " + value);

        value = value.substring(1, value.length());
        value = value.substring(0, value.length()-1);

        List<String> list = Arrays.asList(value.split(","));
        for(String element : list) {
            objectElementsArrayList.add(element);
        }
        System.out.println("Size of object fields arraylist: " + objectElementsArrayList.size());
    }
}
