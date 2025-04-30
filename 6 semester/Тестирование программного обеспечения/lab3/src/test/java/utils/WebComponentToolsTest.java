package utils;

import com.vladimir.WebComponentTools;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebComponentToolsTest {

    @Test
    void testGetViaXPathSuccess() {
        WebElement mockElement = mock(WebElement.class);
        By selector = By.xpath("//div");

        WebDriverWait wait = mock(WebDriverWait.class);
        when(wait.until(ExpectedConditions.visibilityOfElementLocated(selector)))
                .thenReturn(mockElement);
    }

    @Test
    void testGetViaXPathTimeout() {
        WebDriver mockDriver = mock(WebDriver.class);
        By selector = By.xpath("//div[@id='not-found']");

        WebElement result = WebComponentTools.getViaXPath(mockDriver, selector);
        assertNull(result);
    }
}
