package serverlogic.outputchannel;

import database.actions.UserActions;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import serverlogic.ClientCuncurrentHashMap;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;

public class ResponseLogic {
    ClientCuncurrentHashMap clientCuncurrentHashMap = new ClientCuncurrentHashMap();
    UserActions userActions = new UserActions();
    private Logger logger = Logger.getLogger(ResponseLogic.class.getSimpleName());

    public String getRequest(int clientId) throws IOException {
        try {
            BufferedReader bufferedReader = clientCuncurrentHashMap.getElementByKey(clientId).getBufferedReader();
            return bufferedReader.readLine();
        }
        catch (SocketException e) {
            System.out.println("Client has been disconnected!");
            clientCuncurrentHashMap.closeClientSockets(clientId);
            return null;
        }
    }

    public String getRequestFromCreds(int clientId, int userId) throws IOException, ParseException {
        try {
            // get Request
            String requestString = getRequest(clientId);

            // parse responseString to JSONObject
            JSONParser jsonParser = new JSONParser();
            JSONObject responseJSONObject = (JSONObject) jsonParser.parse(requestString);

            logger.info("getRequestFromCreds() - " + requestString);

            if (userId == userActions.getId((String) responseJSONObject.get("username"), (String) responseJSONObject.get("password"))) {
                return (String) responseJSONObject.get("command");
            } else {
                System.out.println("null");
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void sendResponseAsString(String response, int clientId) throws IOException {
        BufferedWriter bufferedWriter = clientCuncurrentHashMap.getElementByKey(clientId).getBufferedWriter();
        if (bufferedWriter != null) {
            bufferedWriter.write(response);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public void sendResponseAsArrayList(ArrayList<String> responseArrayList, int clientId) throws IOException {
        try {
            BufferedWriter bufferedWriter = clientCuncurrentHashMap.getElementByKey(clientId).getBufferedWriter();
            if (bufferedWriter != null) {
                for (String response : responseArrayList) {
                    bufferedWriter.write(response);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("wrong command");
        }
    }

    public void sendResponseArrayListAsJSONObject(int clientId) throws IOException {
        BufferedWriter bufferedWriter = clientCuncurrentHashMap.getElementByKey(clientId).getBufferedWriter();
        //System.out.println(ResponseArrayList.getResponseArrayList()); //содержимое
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(String response : ResponseArrayList.getResponseArrayList()) {
            jsonArray.add(response);
        }
        jsonObject.put("response", jsonArray);

        logger.info("sendResponseArrayListAsJSONObject() - " + jsonObject.toString());

        bufferedWriter.write(jsonObject.toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();
        ResponseArrayList.clearResponseArrayList();
        System.out.println("JSON отправлен");
    }
}
