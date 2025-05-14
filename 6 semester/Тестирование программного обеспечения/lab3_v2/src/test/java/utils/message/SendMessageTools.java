package utils.message;

import org.openqa.selenium.By;

public class SendMessageTools {

    public static By getReceivedMessagePageByAuthorLocator(String title, String author) {
        return By.xpath("//div[contains(@class, 'DialogItem') and contains(@class, 'ajax-link') and .//div[contains(@class, 'DialogItem_Title')]//span[contains(text(), '" + title + "')]]");
    }

    public static By getMessageByTextLocator(String text) {
        return By.xpath("//div[contains(@class, 'DialogMsg') and .//div[@id='message_' and contains(text(), '" + text + "')]]");
    }
}
