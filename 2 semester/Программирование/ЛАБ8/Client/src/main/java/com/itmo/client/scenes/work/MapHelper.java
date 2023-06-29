package com.itmo.client.scenes.work;

import com.itmo.client.clientlogic.inputlogic.CollectionFromServer;
import com.itmo.client.construct.RootContainer;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

public class MapHelper {
    private static RootContainer rootContainer = new RootContainer();
    private static Hashtable<Integer, Color> usersColorHashtable = new Hashtable<>();
    private static Random random = new Random();


    private static Hashtable<String, String> coordinates = new Hashtable<>();

    public MapHelper() {
        updateColors();
    }

    public static void updateColors() {
        usersColorHashtable.clear();

        Hashtable<Integer, Color> tempHashtable = new Hashtable<>();
        for (int i = 1; i <= 100; i++) {
            Color color = getRandomColor();
            tempHashtable.put(i, color);
        }

        setUsersColorHashtable(tempHashtable);
    }

    private static Color getRandomColor() {
        double r = Math.random();
        double g = Math.random();
        double b = Math.random();
        return new Color(r, g, b, 1.0);
    }

    public static Color getColor(Integer id) {
        return usersColorHashtable.get(id);
    }





    public static Hashtable<Integer, Color> getUsersColorHashtable() {
        return usersColorHashtable;
    }

    public static void setUsersColorHashtable(Hashtable<Integer, Color> usersColorHashtable) {
        MapHelper.usersColorHashtable = usersColorHashtable;
    }


    public boolean coordinatesContains(String x, String y) {
        if(coordinates.containsKey(x) && coordinates.get(x).equals(y)) {
            return true;
        } else {
            coordinates.put(x, y);
            return false;
        }
    }
}
