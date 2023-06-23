package clientlogic.outputlogic;

import java.util.AbstractMap;
import java.util.Map;

public class CredentialsMapEntry {
    private static Map.Entry<String, String> credentials = null;

    public CredentialsMapEntry() {

    }
    public CredentialsMapEntry(String username, String password) {
        credentials = new AbstractMap.SimpleEntry<>(username, password);
    }

    public String getUsername() {
        return credentials.getKey();
    }

    public String getPassword() {
        return credentials.getValue();
    }

    public void print() {
        System.out.println(credentials.getKey() + " " + credentials.getValue());
    }
}
