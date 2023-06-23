package serverlogic;

import serverlogic.outputchannel.IOHelperClass;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCuncurrentHashMap {
    private static ConcurrentHashMap<Integer, IOHelperClass> clientConcurrentHashMap = new ConcurrentHashMap<>();

    public void closeClientSockets(Integer clientId) throws IOException {
        clientConcurrentHashMap.get(clientId).closeSockets();
//        System.out.println("BufferedWriter has been closed!");
//        System.out.println("BufferedReader has been closed!");
    }

    public void closeAllSockets() {
        clientConcurrentHashMap.values().stream().forEach(IOHelperClass::closeSockets);
    }

    public ConcurrentHashMap<Integer, IOHelperClass> getClientConcurrentHashMap() {
        return clientConcurrentHashMap;
    }
    public void addElementToClientConcurrentHashMap(Integer clientId, IOHelperClass ioHelperClass) {
        clientConcurrentHashMap.put(clientId, ioHelperClass);
    }
    public boolean containsKey(Integer clientId) {
        return clientConcurrentHashMap.containsKey(clientId);
    }
    public IOHelperClass getElementByKey(Integer clientId) {
        return clientConcurrentHashMap.get(clientId);
    }
}
