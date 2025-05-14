package auth;

import com.github.javafaker.Faker;
import utils.auth.AuthTools;
import utils.ConfLoader;
import utils.DriverTools;
import utils.WebComponentTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest {

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
    void loginTest() {
        drivers.forEach(driver -> {
            String login = ConfLoader.getInstance().getProperty("LOGIN_ACC1");
            String password = ConfLoader.getInstance().getProperty("PASSWORD_ACC1");

            WebComponentTools.gotoMainPage(driver);
            assertFalse(WebComponentTools.isElementPresent(driver, AuthTools.getAccountMenuDropdownLocator(login)));

            AuthTools.login(driver, login, password);
            WebComponentTools.gotoMainPage(driver);
            assertTrue(WebComponentTools.isElementPresent(driver, AuthTools.getAccountMenuDropdownLocator(login)));
        });
    }

    @Test
    void FailedLoginTest() {
        Faker faker = new Faker();

        drivers.forEach(driver -> {
            String login = faker.name().username();
            String password = faker.internet().password(8, 16);

            driver.get(ConfLoader.getInstance().getProperty("MAIN_PAGE"));
            new WebDriverWait(driver, Duration.ofSeconds(5));
            assertFalse(WebComponentTools.isElementPresent(driver, AuthTools.getAccountMenuDropdownLocator(login)));

            AuthTools.login(driver, login, password);

            driver.get(ConfLoader.getInstance().getProperty("MAIN_PAGE"));
            new WebDriverWait(driver, Duration.ofSeconds(5));
            assertFalse(WebComponentTools.isElementPresent(driver, AuthTools.getAccountMenuDropdownLocator(login)));
        });
    }

    @Test
    void logoutTest() {
        drivers.forEach(driver -> {
            String login = ConfLoader.getInstance().getProperty("LOGIN_ACC1");
            String password = ConfLoader.getInstance().getProperty("PASSWORD_ACC1");

            WebComponentTools.gotoMainPage(driver);
            assertFalse(WebComponentTools.isElementPresent(driver, AuthTools.getAccountMenuDropdownLocator(login)));

            AuthTools.login(driver, login, password);
            WebComponentTools.gotoMainPage(driver);
            assertTrue(WebComponentTools.isElementPresent(driver, AuthTools.getAccountMenuDropdownLocator(login)));

            AuthTools.logout(driver, login);
            assertFalse(WebComponentTools.isElementPresent(driver, AuthTools.getAccountMenuDropdownLocator(login)));
        });
    }
}
