package message;

import org.openqa.selenium.By;

public record SendMessageLocators() {

    public static final By MY_MESSAGES_BUTTON = By.xpath("//ul[@class='main_menu insert_main_menu']/li[3]/a");
    public static final By NEW_MESSAGE_BUTTON = By.xpath("//input[@type='button' and @value='Создать беседу']");

    public static final By RECEIVER_INPUT = By.xpath("//input[@id='dialognm-search']");
    public static final By SEARCHING_RECEIVER_RESULT_FIRST = By.xpath("//div[@id='dialognm-search-answ']//li[1]");
    public static final By MESSAGE_TITLE_INPUT = By.xpath("//input[@name='title' and @placeholder='Название беседы']");
    public static final By MESSAGE_TEXT_INPUT = By.xpath("//textarea[@id='DialogNewMsgTextarea']");
    public static final By SEND_MESSAGE_BUTTON = By.xpath("//input[@type='button' and @value='Создать беседу']");
}
