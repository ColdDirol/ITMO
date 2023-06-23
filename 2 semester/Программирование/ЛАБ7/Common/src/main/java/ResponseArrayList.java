import java.io.Serializable;
import java.util.ArrayList;

public class ResponseArrayList implements Serializable {
    private ArrayList<String> responseArrayList = new ArrayList();

    public void addStringToResponseArrayList (String string) {
        responseArrayList.add(string);
    }

    public ArrayList<String> getResponseArrayList() {
        return responseArrayList;
    }

    public void setResponseArrayList(ArrayList<String> responseArrayList) {
        this.responseArrayList = responseArrayList;
    }

    public void clearResponseArrayList() {
        responseArrayList.clear();
    }
}
