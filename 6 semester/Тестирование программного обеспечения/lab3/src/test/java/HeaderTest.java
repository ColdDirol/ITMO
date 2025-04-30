import com.vladimir.DriverTools;
import com.vladimir.WebComponentTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderTest {

    private static final String MAIN_PAGE_TITLE = "Noodlemagazine | The best search engine of HD videos";
    private static final String NOT_MAIN_PAGE_URL = "https://noodlemagazine.com/video/porn+porno";
    private static final String NOT_MAIN_PAGE_TITLE = "Porn porno - found videos";
    private static final By HEADER_HOME_BUTTON = By.xpath("//a[@class='h_btn' and contains(text(), 'noodlemagazine')]");
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
    void goHomeTest() {
        drivers.forEach(driver -> {
            driver.get(NOT_MAIN_PAGE_URL);
            assertEquals(NOT_MAIN_PAGE_TITLE, driver.getTitle());

            WebElement homeButton = WebComponentTools.getViaXPath(driver, HEADER_HOME_BUTTON);

            String originalWindow = driver.getWindowHandle();

            homeButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> d.getWindowHandles().size() > 1);

            Set<String> allWindows = driver.getWindowHandles();
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            assertEquals(MAIN_PAGE_TITLE, driver.getTitle());
            driver.close();
        });
    }

    @Test
    void searchTest() {
        drivers.forEach(driver -> {
            driver.get(NOT_MAIN_PAGE_URL);
            assertEquals(NOT_MAIN_PAGE_TITLE, driver.getTitle());

            WebElement searchInput = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_FORM);
            searchInput.clear();
            searchInput.sendKeys("jav");

            WebElement searchButton = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_BUTTON);
            searchButton.click();

            assertEquals("Jav - found videos", driver.getTitle());
        });
    }
}
