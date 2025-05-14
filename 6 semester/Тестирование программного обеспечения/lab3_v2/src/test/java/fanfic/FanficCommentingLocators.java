package fanfic;

import org.openqa.selenium.By;

public record FanficCommentingLocators() {

    public static final By NEW_COMMENT_INPUT = By.xpath("//textarea[contains(@id, 'MessageCommentNewTextarea_')]");
    public static final By NEW_COMMENT_BUTTON = By.xpath("//input[@type='submit' and @value='Отправить']");
}
