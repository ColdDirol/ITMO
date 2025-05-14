package message;

import com.github.javafaker.Faker;
import discussion.FandomCreationLocators;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfLoader;
import utils.DriverTools;
import utils.WebComponentTools;
import utils.auth.AuthTools;
import utils.fandom.FandomCreationTools;
import utils.message.SendMessageTools;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SendMessageTest {

    private List<WebDriver> drivers;
    private Faker faker;

    @BeforeEach
    void setup() {
        drivers = DriverTools.configure();
        faker = new Faker();
    }

    @AfterEach
    void tearDown() {
//        drivers.forEach(WebDriver::quit);
    }

    @Test
    void testMessageSending() {
        drivers.forEach(driver -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String firstUserLogin = ConfLoader.getInstance().getProperty("LOGIN_ACC1");
            String secondUserLogin = ConfLoader.getInstance().getProperty("LOGIN_ACC2");

            AuthTools.login(driver, firstUserLogin, ConfLoader.getInstance().getProperty("PASSWORD_ACC1"));

            WebComponentTools.clickButton(driver, SendMessageLocators.MY_MESSAGES_BUTTON);
            WebComponentTools.clickButton(driver, SendMessageLocators.NEW_MESSAGE_BUTTON);

            WebElement receiverInput = WebComponentTools.getViaXPath(driver, SendMessageLocators.RECEIVER_INPUT);
            receiverInput.sendKeys(secondUserLogin);
            receiverInput.sendKeys(Keys.ENTER);

            wait.until(
                    ExpectedConditions.elementToBeClickable(SendMessageLocators.SEARCHING_RECEIVER_RESULT_FIRST)
            ).click();

            String title = faker.funnyName().name();
            WebComponentTools.fillInputField(driver, SendMessageLocators.MESSAGE_TITLE_INPUT, title);
            String text = faker.lorem().paragraph();
            WebComponentTools.fillInputField(driver, SendMessageLocators.MESSAGE_TEXT_INPUT, text);
            WebComponentTools.clickButton(driver, SendMessageLocators.NEW_MESSAGE_BUTTON);

            AuthTools.logout(driver, firstUserLogin);
            AuthTools.login(driver, secondUserLogin, ConfLoader.getInstance().getProperty("PASSWORD_ACC2"));
            WebComponentTools.clickButton(driver, SendMessageLocators.MY_MESSAGES_BUTTON);
            WebComponentTools.clickButton(driver, SendMessageTools.getReceivedMessagePageByAuthorLocator(title, firstUserLogin));
            wait.until(
                    ExpectedConditions.elementToBeClickable(SendMessageTools.getMessageByTextLocator(text))
            ).click();
        });
    }
}
