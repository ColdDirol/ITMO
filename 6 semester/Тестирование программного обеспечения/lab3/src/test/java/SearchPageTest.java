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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchPageTest {

    private static final String MAIN_PAGE_URL = "https://noodlemagazine.com/new-video";
    private static final String MAIN_PAGE_TITLE = "Noodlemagazine | The best search engine of HD videos";

    private static final By HEADER_SEARCH_FORM = By.xpath("//input[@class='s_text']");
    private static final By HEADER_SEARCH_BUTTON = By.xpath("//button[@class='s_btn']");

    private static final By POPULAR_VIDEOS_CATEGORY_BY_MONTH_BUTTON = By.xpath("//a[@href='/popular/month' and contains(text(), 'Popular')]");
    private static final By WATCHING_NOW_VIDEOS_CATEGORY_BUTTON = By.xpath("//a[@href='/now' and contains(text(), 'Watching now')]");
    private static final By RANDOM_CATEGORIES = By.xpath("//div[contains(@class, 'tags-scroll')]//a[contains(@href, '/video/')]");
    private static final By VIDEO_LIST = By.xpath("//div[@id='list_videos']");


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
    void getPopularVideos() {
        drivers.forEach(driver -> {
            driver.get(MAIN_PAGE_URL);
            assertEquals(MAIN_PAGE_TITLE, driver.getTitle());

            WebElement searchInput = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_FORM);
            searchInput.clear();
            searchInput.sendKeys("jav");

            WebElement searchButton = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_BUTTON);
            searchButton.click();

            assertEquals("Jav - found videos", driver.getTitle());

            WebElement popularVideosButton = WebComponentTools.getViaXPath(driver, POPULAR_VIDEOS_CATEGORY_BY_MONTH_BUTTON);
            popularVideosButton.click();

            WebElement videoList = WebComponentTools.getViaXPath(driver, VIDEO_LIST);
            assertNotNull(videoList);
        });
    }

    @Test
    void getWatchingNowVideos() {
        drivers.forEach(driver -> {
            driver.get(MAIN_PAGE_URL);
            assertEquals(MAIN_PAGE_TITLE, driver.getTitle());

            WebElement searchInput = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_FORM);
            searchInput.clear();
            searchInput.sendKeys("jav");

            WebElement searchButton = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_BUTTON);
            searchButton.click();

            assertEquals("Jav - found videos", driver.getTitle());

            String originalWindow = driver.getWindowHandle();

            WebElement watchingNowVideosButton = WebComponentTools.getViaXPath(driver, WATCHING_NOW_VIDEOS_CATEGORY_BUTTON);
            watchingNowVideosButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> d.getWindowHandles().size() > 1);

            Set<String> allWindows = driver.getWindowHandles();
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            WebElement videoList = WebComponentTools.getViaXPath(driver, VIDEO_LIST);
            assertNotNull(videoList);
        });
    }

    @Test
    void getVideosByRandomPopularCategory() {
        drivers.forEach(driver -> {
            driver.get(MAIN_PAGE_URL);
            assertEquals(MAIN_PAGE_TITLE, driver.getTitle());

            WebElement searchInput = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_FORM);
            searchInput.clear();
            searchInput.sendKeys("jav");

            WebElement searchButton = WebComponentTools.getViaXPath(driver, HEADER_SEARCH_BUTTON);
            searchButton.click();

            assertEquals("Jav - found videos", driver.getTitle());

            List<WebElement> categoryElements = driver.findElements(RANDOM_CATEGORIES);
            List<String> categoryTexts = categoryElements.stream()
                    .map(WebElement::getText)
                    .toList();
            assertNotNull(categoryTexts);

            String categoryText = categoryTexts.stream().findFirst().orElse(null);

            String originalWindow = driver.getWindowHandle();

            WebElement category = driver.findElement(
                    By.xpath("//div[contains(@class, 'tags-scroll')]//a[contains(@href, '/video/') and normalize-space(text())='" + categoryText + "']")
            );
            category.click();

            WebElement watchingNowVideosButton = WebComponentTools.getViaXPath(driver, WATCHING_NOW_VIDEOS_CATEGORY_BUTTON);
            watchingNowVideosButton.click();
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> d.getWindowHandles().size() > 1);
            Set<String> allWindows = driver.getWindowHandles();
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            WebElement videoList = driver.findElement(VIDEO_LIST);
            assertNotNull(videoList);
        });
    }
}
