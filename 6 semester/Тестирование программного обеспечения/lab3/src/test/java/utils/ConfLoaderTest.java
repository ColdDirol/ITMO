package utils;

import com.vladimir.ConfLoader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConfLoaderTest {

    private static final String TEST_CONFIG_PATH = "test-conf.conf";

    @BeforeAll
    static void setup() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_CONFIG_PATH))) {
            writer.write("DRIVER=chrome\n");
            writer.write("DRIVER_PATH=/path/to/chromedriver\n");
        }
    }

    @AfterAll
    static void cleanup() {
        new File(TEST_CONFIG_PATH).delete();
    }

    @Test
    void testGetProperty() throws Exception {
        ConfLoader loader = getNewInstanceForTest(TEST_CONFIG_PATH);
        assertEquals("chrome", loader.getProperty("DRIVER"));
        assertEquals("/path/to/chromedriver", loader.getProperty("DRIVER_PATH"));
        assertNull(loader.getProperty("UNKNOWN_KEY"));
    }

    private ConfLoader getNewInstanceForTest(String filePath) throws Exception {
        ConfLoader instance = ConfLoader.getInstance();
        Field mapField = ConfLoader.class.getDeclaredField("propertiesMap");
        mapField.setAccessible(true);
        Map<String, String> testMap = (Map<String, String>) mapField.get(instance);
        testMap.clear();

        Method method = ConfLoader.class.getDeclaredMethod("loadProperties", String.class);
        method.setAccessible(true);
        method.invoke(instance, filePath);

        return instance;
    }
}
