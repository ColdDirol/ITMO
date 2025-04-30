package com.vladimir;

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
            return null;
        }
    }
}
