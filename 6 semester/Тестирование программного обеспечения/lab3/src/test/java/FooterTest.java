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

import static org.junit.jupiter.api.Assertions.*;

public class FooterTest {

    private static final String MAIN_PAGE_TITLE = "Noodlemagazine | The best search engine of HD videos";
    private static final String NOT_MAIN_PAGE_URL = "https://noodlemagazine.com/video/porn+porno";
    private static final String NOT_MAIN_PAGE_TITLE = "Porn porno - found videos";
    private static final By FOOTER_HOME_BUTTON = By.xpath("//a[text()='noodlemagazine' and @href='/new-video']");
    private static final By FOOTER_PRIVACY_POLICY_BUTTON = By.xpath("//div[@class='f_menu']/a[@href='/privacy']");
    private static final String PRIVACY_POLICY_TITLE = "Privacy policy";
    private static final By FOOTER_CONTENT_REMOVAL_BUTTON = By.xpath("//div[@class='f_menu']/a[@href='/removal']");
    private static final String CONTENT_REMOVAL_TITLE = "Content removal";
    private static final By FOOTER_CONTACT_US_BUTTON = By.xpath("//div[@class='f_menu']/a[@href='/contact']");
    private static final String CONTACT_US_TITLE = "Contact us";
    private static final By ADMINISTRATION_PAGE_CONTENT = By.xpath("//div[@class='static']");

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

            WebElement homeButton = WebComponentTools.getViaXPath(driver, FOOTER_HOME_BUTTON);

            String originalWindow = driver.getWindowHandle();

            homeButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.getWindowHandles().size() > 1);

            driver.getWindowHandles().stream()
                    .filter(handle -> !handle.equals(originalWindow))
                    .findFirst()
                    .ifPresent(driver.switchTo()::window);

            assertEquals(MAIN_PAGE_TITLE, driver.getTitle());
            driver.close();
        });
    }

    @Test
    void goPrivacyPolicyTest() {
        drivers.forEach(driver -> {
            driver.get(NOT_MAIN_PAGE_URL);
            assertEquals(NOT_MAIN_PAGE_TITLE, driver.getTitle());

            WebElement privacyPolicyButton = WebComponentTools.getViaXPath(driver, FOOTER_PRIVACY_POLICY_BUTTON);

            String originalWindow = driver.getWindowHandle();

            privacyPolicyButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.getWindowHandles().size() > 1);

            driver.getWindowHandles().stream()
                    .filter(handle -> !handle.equals(originalWindow))
                    .findFirst()
                    .ifPresent(driver.switchTo()::window);

            assertEquals(PRIVACY_POLICY_TITLE, driver.getTitle());

            WebElement contentDiv = WebComponentTools.getViaXPath(driver, ADMINISTRATION_PAGE_CONTENT);
            assertFalse(contentDiv.getText().trim().isEmpty());

            driver.close();
        });
    }

    @Test
    void goContentRemovalTest() {
        drivers.forEach(driver -> {
            driver.get(NOT_MAIN_PAGE_URL);
            assertEquals(NOT_MAIN_PAGE_TITLE, driver.getTitle());

            WebElement privacyPolicyButton = WebComponentTools.getViaXPath(driver, FOOTER_CONTENT_REMOVAL_BUTTON);

            String originalWindow = driver.getWindowHandle();

            privacyPolicyButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.getWindowHandles().size() > 1);

            driver.getWindowHandles().stream()
                    .filter(handle -> !handle.equals(originalWindow))
                    .findFirst()
                    .ifPresent(driver.switchTo()::window);

            assertEquals(CONTENT_REMOVAL_TITLE, driver.getTitle());

            WebElement contentDiv = WebComponentTools.getViaXPath(driver, ADMINISTRATION_PAGE_CONTENT);
            assertFalse(contentDiv.getText().trim().isEmpty());

            driver.close();
        });
    }

    @Test
    void goContactUsTest() {
        drivers.forEach(driver -> {
            driver.get(NOT_MAIN_PAGE_URL);
            assertEquals(NOT_MAIN_PAGE_TITLE, driver.getTitle());

            WebElement privacyPolicyButton = WebComponentTools.getViaXPath(driver, FOOTER_CONTACT_US_BUTTON);

            String originalWindow = driver.getWindowHandle();

            privacyPolicyButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.getWindowHandles().size() > 1);

            driver.getWindowHandles().stream()
                    .filter(handle -> !handle.equals(originalWindow))
                    .findFirst()
                    .ifPresent(driver.switchTo()::window);

            assertEquals(CONTACT_US_TITLE, driver.getTitle());

            WebElement contentDiv = WebComponentTools.getViaXPath(driver, ADMINISTRATION_PAGE_CONTENT);
            assertFalse(contentDiv.getText().trim().isEmpty());

            driver.close();
        });
    }
}
