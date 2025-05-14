package utils.fanfic;

import com.github.javafaker.Faker;
import fanfic.FanficPublicationLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfLoader;
import utils.WebComponentTools;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FanficPublicationTools {

    private static final Faker faker = new Faker();

    /**
     * allow publicate fanfic with test data
     *
     * @param driver
     * @return url
     */
    public static String publicate(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // goto fanfic creation
        WebComponentTools.clickButton(driver, FanficPublicationLocators.PUBLISH_FANFIC_PROPOSAL);
        WebComponentTools.fillInputField(driver, FanficPublicationLocators.FANFIC_NAME_INPUT, faker.funnyName().name());
        WebComponentTools.clickButton(driver, FanficPublicationLocators.FANFIC_NAME_ACCEPT_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.GOTO_CREATED_FANFIC_BUTTON);
        new WebDriverWait(driver, Duration.ofSeconds(5));

        // goto fanfic header
        WebComponentTools.clickButton(driver, FanficPublicationLocators.GOTO_HEADER_BUTTON);

        WebComponentTools.clickButton(driver, FanficPublicationLocators.CHARACTER_FILLING_EDIT_BUTTON);
        WebElement characterSelector = WebComponentTools.getViaXPath(driver, FanficPublicationLocators.CHARACTER_FILLING_SELECTION_INPUT);
        List.of("Неизвестный Персонаж", "Новый Женский Персонаж", "Новый Мужской Персонаж").forEach(character -> {
            characterSelector.click();
            characterSelector.sendKeys(character);
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(@class, 'select2-results__option') and contains(text(), '" + character + "')]")
            ));
            option.click();
        });
        WebComponentTools.clickButton(driver, FanficPublicationLocators.CHARACTER_FILLING_SELECTION_DONE);

        WebComponentTools.clickButton(driver, FanficPublicationLocators.CATEGORY_FILLING_EDIT_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.CATEGORY_FILLING_SELECTION_INPUT_BUTTON);

        WebComponentTools.clickButton(driver, FanficPublicationLocators.RATING_FILLING_EDIT_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.RATING_FILLING_SELECTION_INPUT_BUTTON);

        WebComponentTools.clickButton(driver, FanficPublicationLocators.GENRE_FILLING_EDIT_BUTTON);
        Select genreSelect = new Select(driver.findElement(FanficPublicationLocators.GENRE_FILLING_SELECTION_INPUT));
        genreSelect.selectByVisibleText(ConfLoader.getInstance().getProperty("NEW_FANFIC_GENRE_NAME"));
        WebComponentTools.clickButton(driver, FanficPublicationLocators.GENRE_FILLING_SELECTION_DONE);

        WebComponentTools.clickButton(driver, FanficPublicationLocators.SIZE_FILLING_EDIT_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.SIZE_FILLING_SELECTION_INPUT_BUTTON);

        WebComponentTools.clickButton(driver, FanficPublicationLocators.STATUS_FILLING_EDIT_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.STATUS_FILLING_SELECTION_INPUT_BUTTON);

        WebComponentTools.clickButton(driver, FanficPublicationLocators.SUMMARY_FILLING_EDIT_BUTTON);
        WebComponentTools.fillInputField(driver, FanficPublicationLocators.SUMMARY_FILLING_SELECTION_INPUT, faker.lorem().paragraph());
        WebComponentTools.clickButton(driver, FanficPublicationLocators.SUMMARY_FILLING_SELECTION_DONE);

//            new WebDriverWait(driver, Duration.ofSeconds(15));
        // goto fanfic chapters
        WebComponentTools.clickButton(driver, FanficPublicationLocators.GOTO_ADD_CHAPTER_BUTTON);
        WebComponentTools.fillInputField(driver, FanficPublicationLocators.CHAPTER_NAME_INPUT, faker.lorem().paragraph());
        WebComponentTools.fillInputField(driver, FanficPublicationLocators.CHAPTER_TEXT_INPUT, faker.lorem().paragraph());
        WebComponentTools.clickButton(driver, FanficPublicationLocators.CHAPTER_DONE_BUTTON);

        // publish fanfic
        WebComponentTools.clickButton(driver, FanficPublicationLocators.PUBLISH_FANFIC_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.PUBLISH_FANFIC_WITHOUT_CHECKING_OPTION_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.PUBLISH_FANFIC_CONFIRMATION_BUTTON);

        assertTrue(
                wait.until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//div[@class='ContentTable']/h4"),
                        "Ваше произведение успешно опубликовано"
                ))
        );

        WebComponentTools.clickButton(driver, FanficPublicationLocators.GOTO_FANFIC_SETTINGS_BUTTON);
        WebComponentTools.clickButton(driver, FanficPublicationLocators.WATCH_AS_READER_BUTTON_BUTTON);

        return driver.getCurrentUrl();
    }
}
