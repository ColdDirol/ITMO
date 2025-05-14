package discussion;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfLoader;
import utils.DriverTools;
import utils.WebComponentTools;
import utils.auth.AuthTools;
import utils.fandom.FandomCreationTools;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FandomCreationTest {

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
    void testCreateDiscussion() {
        drivers.forEach(driver -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String login = ConfLoader.getInstance().getProperty("LOGIN_ACC1");
            AuthTools.login(driver, login, ConfLoader.getInstance().getProperty("PASSWORD_ACC1"));
            WebComponentTools.clickButton(driver, FandomCreationLocators.CREATE_NEW_FANDOM_BUTTON);
            WebComponentTools.clickButton(driver, FandomCreationLocators.CREATE_NEW_FANDOM_POLICY_AGREEMENT_BUTTON);
            WebComponentTools.fillInputField(driver, FandomCreationLocators.CREATE_NEW_FANDOM_NAME_INPUT, faker.funnyName().name());
            WebComponentTools.clickButton(driver, FandomCreationLocators.CREATE_NEW_FANDOM_NAME_DONE_BUTTON);

            assertTrue(
                    wait.until(ExpectedConditions.visibilityOfElementLocated(FandomCreationTools.getFandomAuthor(login)))
                            .isDisplayed()
            );

            WebComponentTools.clickButton(driver, FandomCreationLocators.FANDOM_DELETION_BUTTON);
            WebComponentTools.clickButton(driver, FandomCreationLocators.FANDOM_DELETION_CONFIRMATION);

            assertTrue(
                    wait.until(ExpectedConditions.visibilityOfElementLocated(FandomCreationLocators.FANDOM_DELETION_FINISHED_MESSAGE))
                            .isDisplayed()
            );
        });
    }
}
