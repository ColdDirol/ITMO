package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class DriverTools {

    public static List<WebDriver> configure() {
        System.setProperty("webdriver.firefox.logfile", "geckodriver.log");
        List<WebDriver> drivers = new ArrayList<>();
        switch (ConfLoader.getInstance().getProperty("DRIVER")) {
            case "firefox" ->
                drivers.add(new FirefoxDriver());
            case "chrome" ->
                drivers.add(new ChromeDriver());
            case "all" -> {
                drivers.add(new FirefoxDriver());
                drivers.add(new ChromeDriver());
            }
            default -> throw new RuntimeException("Web driver is not configured");
        };
        return drivers;
    }

    public static WebDriver createChromeDriver() {
        return new ChromeDriver();
    }

    public static WebDriver createFirefoxDriver() {
        return new FirefoxDriver();
    }
}
