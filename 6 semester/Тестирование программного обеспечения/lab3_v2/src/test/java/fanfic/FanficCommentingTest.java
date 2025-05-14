package fanfic;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfLoader;
import utils.DriverTools;
import utils.fanfic.FanficCommentingTools;
import utils.fanfic.FanficPublicationTools;
import utils.WebComponentTools;
import utils.auth.AuthTools;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FanficCommentingTest {

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
    void testComment() {
        drivers.forEach(driver -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String login = ConfLoader.getInstance().getProperty("LOGIN_ACC1");
            AuthTools.login(driver, login, ConfLoader.getInstance().getProperty("PASSWORD_ACC1"));
            driver.get(FanficPublicationTools.publicate(driver));

            String comment = faker.lorem().paragraph();
            WebComponentTools.fillInputField(driver, FanficCommentingLocators.NEW_COMMENT_INPUT, comment);
            WebComponentTools.clickButton(driver, FanficCommentingLocators.NEW_COMMENT_BUTTON);

            assertTrue(
                    wait.until(ExpectedConditions.visibilityOfElementLocated(FanficCommentingTools.getCommentByAuthorAndTextLocator(login, comment)))
                            .isDisplayed()
            );
        });
    }

    static Stream<Integer> randomIntProvider() {
        Random random = new Random();
        return Stream.of(random.nextInt(10) + 1);
    }

    @ParameterizedTest
    @MethodSource("randomIntProvider")
    void testMultipleComments(int randomCommentCount) {
        drivers.forEach(driver -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String login = ConfLoader.getInstance().getProperty("LOGIN_ACC1");
            AuthTools.login(driver, login, ConfLoader.getInstance().getProperty("PASSWORD_ACC1"));
            driver.get(FanficPublicationTools.publicate(driver));

            List<String> comments = new ArrayList<>();
            for (int i = 0; i < randomCommentCount; i++) {
                comments.add(faker.lorem().sentence(10));
            }
            comments.forEach(comment -> {
                WebComponentTools.fillInputField(driver, FanficCommentingLocators.NEW_COMMENT_INPUT, comment);
                WebComponentTools.clickButton(driver, FanficCommentingLocators.NEW_COMMENT_BUTTON);
                assertTrue(
                        wait.until(ExpectedConditions.visibilityOfElementLocated(FanficCommentingTools.getCommentByAuthorAndTextLocator(login, comment)))
                                .isDisplayed()
                );
            });
        });
    }
}
