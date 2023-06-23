import java.io.BufferedWriter;

public class ResponseStringChannel {
    private static String bufferString = "";

    BufferedWriter bufferedWriter;

    public void addElementToTheRefuseString(String element) {
        if(bufferString.equals("")) bufferString += element;
        else bufferString += "\n" + element;
    }

    public void readRefuseStringFromServer() {
        printRefuseString();
        clearRefuseString();
    }


    private void printRefuseString() {
        System.out.print(bufferString);
    }

    public void clearRefuseString() {
        bufferString = "";
    }
}
