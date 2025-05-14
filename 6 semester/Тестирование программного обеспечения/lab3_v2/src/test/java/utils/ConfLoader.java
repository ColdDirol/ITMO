package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ConfLoader {

    private static final ConfLoader INSTANCE = new ConfLoader();

    private final Map<String, String> propertiesMap;

    private ConfLoader() {
        this.propertiesMap = new HashMap<>();
        loadProperties("fanficsme.conf");
    }

    public static ConfLoader getInstance() {
        return INSTANCE;
    }

    private void loadProperties(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    propertiesMap.put(key, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties from: " + filePath, e);
        }
    }

    public String getProperty(String key) {
        return propertiesMap.get(key);
    }
}
