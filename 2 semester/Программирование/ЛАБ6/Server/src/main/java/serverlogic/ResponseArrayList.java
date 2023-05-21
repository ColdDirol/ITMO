package serverlogic;

import java.util.ArrayList;

public class ResponseArrayList {
    private static ArrayList<String> responseArrayList = new ArrayList<>();

    public void addElementToTheResponseArrayList(String element) {
        responseArrayList.add(element + "\r\n");
    }

    public void clearResponseArrayList() {
        responseArrayList.clear();
    }

    public void printResponseArrayList() {
        System.out.println("ResponseArrayList {");
        for(String value : responseArrayList) {
            System.out.println(value);
        }
        System.out.println("}");
    }

    public ArrayList<String> getResponseArrayList() {
        return responseArrayList;
    }
    public void setResponseArrayList(ArrayList<String> responseArrayList) {
        this.responseArrayList = responseArrayList;
    }

    public Integer size() {
        int size = 0;

        for(String value : responseArrayList) {
            size += value.length();
        }

        return size;
    }
}
