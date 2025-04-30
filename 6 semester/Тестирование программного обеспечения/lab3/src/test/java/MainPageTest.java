import com.vladimir.DriverTools;
import com.vladimir.WebComponentTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {

    private static final String MAIN_PAGE_URL = "https://noodlemagazine.com/new-video";
    private static final String MAIN_PAGE_TITLE = "Noodlemagazine | The best search engine of HD videos";

    private static final By HEADER_SEARCH_FORM = By.xpath("//input[@class='s_text']");
    private static final By HEADER_SEARCH_BUTTON = By.xpath("//button[@class='s_btn']");

    private List<WebDriver> drivers;

    @BeforeEach
    void setup() {
        drivers = DriverTools.configure();
    }

    @AfterEach
    void tearDown() {
        drivers.forEach(WebDriver::quit);
    }

    @Test
    void searchTest() {
        drivers.forEach(driver -> {
            driver.get(MAIN_PAGE_URL);
            assertEquals(MAIN_PAGE_TITLE, driver.getTitle());

            WebElement searchInput = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_FORM);
            searchInput.clear();
            searchInput.sendKeys("jav");

            WebElement searchButton = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_BUTTON);
            searchButton.click();

            assertEquals("Jav - found videos", driver.getTitle());
        });
    }
}
