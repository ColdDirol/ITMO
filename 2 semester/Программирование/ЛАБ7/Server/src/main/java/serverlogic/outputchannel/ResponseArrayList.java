package serverlogic.outputchannel;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseArrayList implements Serializable {
    private static ArrayList<String> responseArrayList = new ArrayList();

    public void addStringToResponseArrayList(String string) {
        if(responseArrayList.size() == 0) responseArrayList.add(string);
        else responseArrayList.add("\n" + string);
    }

    public static ArrayList<String> getResponseArrayList() {
        return responseArrayList;
    }

    public void setResponseArrayList(ArrayList<String> responseArrayList) {
        this.responseArrayList = responseArrayList;
    }

    public static void clearResponseArrayList() {
        responseArrayList.clear();
    }
}
