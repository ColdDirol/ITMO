package com.itmo.client.construct;

import com.itmo.client.clientlogic.inputlogic.ResponseArrayList;
import com.itmo.client.clientlogic.outputlogic.CredentialsMapEntry;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class RootContainer {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static Stage stage;
    ResponseArrayList responseArrayList = new ResponseArrayList();
    CredentialsMapEntry credentialsMapEntry = new CredentialsMapEntry();


    private static ArrayList<String[]> stringArrayList;



    public void connect() throws IOException, InterruptedException {
        socket = new Socket(SERVER_HOST, SERVER_PORT);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        System.out.println("Client has been connected to the server");
        Thread.sleep(2000);
    }

    public void checkConnect() throws IOException {
        Socket checkSocket = new Socket(SERVER_HOST, SERVER_PORT);
        checkSocket.close();
    }


    public String readAsString() throws IOException {
        return bufferedReader.readLine();
    }


    public ArrayList<String> readAsArrayList() throws IOException, InterruptedException {
        if (bufferedReader.ready()) {
            while (bufferedReader.ready()) {
                checkConnect();
                responseArrayList.addElementToTheRefuseString(bufferedReader.readLine());
//                System.out.println(responseArrayList.getArrayList());
                Thread.sleep(20);
            }
            checkConnect();
            return responseArrayList.getArrayList();
        }
        else return null;
    }

    public void writeString(String string) throws IOException {
        bufferedWriter.write(string);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    //to get collection JSONArray
//    public JSONArray getJSONArray() throws IOException, ParseException {
//        String responseString = bufferedReader.readLine();
//        System.out.println(responseString);
//
//        // parse responseString to JSONObject
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseJSONObject = (JSONObject) jsonParser.parse(responseString);
//
//        // parse JSONObject to JSONArray
//        JSONArray jsonArray = (JSONArray) responseJSONObject.get("response");
//        return jsonArray;
//    }

    public ArrayList<String[]> getJSONArrayList() throws IOException, ParseException, InterruptedException {
        String responseString = bufferedReader.readLine();
//        System.out.println("*" + responseString);

        // parse responseString to JSONObject
        JSONParser jsonParser = new JSONParser();
        JSONObject responseJSONObject = (JSONObject) jsonParser.parse(responseString);

        // parse JSONObject to JSONArray
        JSONArray jsonArray = (JSONArray) responseJSONObject.get("response");

        ArrayList<String[]> arrayList = new ArrayList<>();
        for(Object obj : jsonArray) {
            JSONArray subArray = (JSONArray) obj;
            String[] arr = new String[subArray.size()];
            for(int i=0; i<subArray.size(); i++) {
                arr[i] = (String) subArray.get(i);
            }
            arrayList.add(arr);
        }

        Thread.sleep(100);

        while (bufferedReader.ready()) {
            System.out.println(bufferedReader.readLine());
        }

        return arrayList;
    }

    // to get ResponseArrayList
    public String getResponseArrayListAsString() throws IOException, ParseException {
        String responseJSONString = bufferedReader.readLine();
        String responseString = null;

        // parse responseString to JSONObject
        JSONParser jsonParser = new JSONParser();
        JSONObject responseJSONObject = (JSONObject) jsonParser.parse(responseJSONString);

        // parse JSONObject to JSONArray
        JSONArray jsonArray = (JSONArray) responseJSONObject.get("response");
        for (Object object : jsonArray) {
            responseString += object;
        }

        return responseString;
    }

    // to send commands
    public void sendCommandJSONArray(String command) throws IOException {
        JSONObject requestJSONObject = new JSONObject();
        requestJSONObject.put("command", command);
        requestJSONObject.put("username", credentialsMapEntry.getUsername());
        requestJSONObject.put("password", credentialsMapEntry.getPassword());

        String requestString = requestJSONObject.toString();

        writeString(requestString);
    }



    public static Socket getSocket() {
        return socket;
    }
    public static void setSocket(Socket socket) {
        RootContainer.socket = socket;
    }


    public static BufferedReader getBufferedReader() {
        return bufferedReader;
    }
    public static void setBufferedReader(BufferedReader bufferedReader) {
        RootContainer.bufferedReader = bufferedReader;
    }


    public static BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
    public static void setBufferedWriter(BufferedWriter bufferedWriter) {
        RootContainer.bufferedWriter = bufferedWriter;
    }


    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage stage) {
        RootContainer.stage = stage;
    }

    public ArrayList<String[]> getStringArrayList() {
        return stringArrayList;
    }
    public void setStringArrayList(ArrayList<String[]> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }
}
