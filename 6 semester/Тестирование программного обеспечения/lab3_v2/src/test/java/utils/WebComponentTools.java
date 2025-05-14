package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebComponentTools {

    public static WebElement getViaXPath(WebDriver driver, By selector) {
        try {
            WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return driverWait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isElementPresent(WebDriver driver, By selector) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            return !driver.findElements(selector).isEmpty();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public static void gotoMainPage(WebDriver driver) {
        driver.get(ConfLoader.getInstance().getProperty("MAIN_PAGE"));
        new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public static void gotoPage(WebDriver driver, String url) {
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public static void clickButton(WebDriver driver, By selector) {
        getViaXPath(driver, selector).click();
    }

    public static void fillInputField(WebDriver driver, By selector, String text) {
        WebElement inputField = getViaXPath(driver, selector);
        inputField.clear();
        inputField.sendKeys(text);
    }

}
