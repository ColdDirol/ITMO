package com.itmo.client.clientlogic.inputlogic;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class ResponseArrayList {
    private static ArrayList<String> arrayList = new ArrayList<>();

    BufferedWriter bufferedWriter;

    public void addElementToTheRefuseString(String element) {
        arrayList.add(element);
    }

    public void readRefuseStringFromServer() {
        printRefuseString();
        clearRefuseString();
    }


    public static ArrayList<String> getArrayList() {
        return arrayList;
    }

    public static void setArrayList(ArrayList<String> arrayList) {
        ResponseArrayList.arrayList = arrayList;
    }

    private void printRefuseString() {
        System.out.println(arrayList);
    }

    public void clearRefuseString() {
        arrayList.clear();
    }
}
