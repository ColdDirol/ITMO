package utils;

import com.vladimir.ConfLoader;
import com.vladimir.DriverTools;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverToolsTest {

    @Test
    void testConfigureChrome() {
        try (MockedStatic<ConfLoader> mockedConf = mockStatic(ConfLoader.class);
             MockedStatic<DriverTools> mockedDriverTools = mockStatic(DriverTools.class, CALLS_REAL_METHODS)) {

            ConfLoader conf = mock(ConfLoader.class);
            when(conf.getProperty("DRIVER")).thenReturn("chrome");
            mockedConf.when(ConfLoader::getInstance).thenReturn(conf);

            WebDriver mockDriver = mock(WebDriver.class);
            mockedDriverTools.when(DriverTools::createChromeDriver).thenReturn(mockDriver);

            WebDriver driver = DriverTools.createChromeDriver();
            assertEquals(mockDriver, driver);
        }
    }

    @Test
    void testConfigureFirefox() {
        try (MockedStatic<ConfLoader> mockedConf = mockStatic(ConfLoader.class);
             MockedStatic<DriverTools> mockedDriverTools = mockStatic(DriverTools.class, CALLS_REAL_METHODS)) {

            ConfLoader conf = mock(ConfLoader.class);
            when(conf.getProperty("DRIVER")).thenReturn("firefox");
            mockedConf.when(ConfLoader::getInstance).thenReturn(conf);

            WebDriver mockDriver = mock(WebDriver.class);
            mockedDriverTools.when(DriverTools::createFirefoxDriver).thenReturn(mockDriver);

            WebDriver driver = DriverTools.createFirefoxDriver();
            assertEquals(mockDriver, driver);
        }
    }

    @Test
    void testConfigureUnknownDriver() {
        try (MockedStatic<ConfLoader> mockedConf = mockStatic(ConfLoader.class)) {
            ConfLoader conf = mock(ConfLoader.class);
            when(conf.getProperty("DRIVER")).thenReturn("unknown");
            mockedConf.when(ConfLoader::getInstance).thenReturn(conf);

            RuntimeException ex = assertThrows(RuntimeException.class, DriverTools::configure);
            assertEquals("Web driver is not configured", ex.getMessage());
        }
    }
}