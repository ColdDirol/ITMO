package fanfic;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfLoader;
import utils.DriverTools;
import utils.WebComponentTools;
import utils.auth.AuthTools;
import utils.fanfic.FanficCommentingTools;
import utils.fanfic.FanficPublicationTools;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FanficReportTest {

    private List<WebDriver> drivers;
    private Faker faker;

    @BeforeEach
    void setup() {
        drivers = DriverTools.configure();
        faker = new Faker();
    }

    @AfterEach
    void tearDown() {
        drivers.forEach(WebDriver::quit);
    }

    @Test
    void testReport() {
        drivers.forEach(driver -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            AuthTools.login(driver, ConfLoader.getInstance().getProperty("LOGIN_ACC1"), ConfLoader.getInstance().getProperty("PASSWORD_ACC1"));
            driver.get(FanficPublicationTools.publicate(driver));

            WebComponentTools.clickButton(driver, FanficReportLocators.SEND_REPOST_BUTTON);
            WebComponentTools.fillInputField(driver, FanficReportLocators.SEND_REPORT_INPUT, faker.lorem().paragraph());
            WebComponentTools.clickButton(driver, FanficReportLocators.SEND_REPORT_DONE_BUTTON);

            assertTrue(
                    wait.until(ExpectedConditions.visibilityOfElementLocated(FanficReportLocators.SEND_REPOST_COMPARATIVE_MESSAGE))
                    .isDisplayed()
            );
        });
    }
}
