package serverlogic.authorization;

import java.util.AbstractMap;
import java.util.Map;

public class ClientCredentialsMapEntry {
    private static Map.Entry<String, String> credentials = null;

    public ClientCredentialsMapEntry(String username, String password) {
        credentials = new AbstractMap.SimpleEntry<>(username, password);
    }

    public void print() {
        System.out.println(credentials.getKey() + " " + credentials.getValue());
    }
}
