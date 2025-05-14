package fanfic;

import org.openqa.selenium.By;

public record FanficReportLocators() {

    public static final By SEND_REPOST_BUTTON = By.xpath("//a[contains(@class, 'normal_link') and contains(@class, 'light') and contains(@class, 'AbusButton') and contains(@class, 'AjaxToModal')]");
    public static final By SEND_REPORT_INPUT = By.xpath("//textarea[@class=\"input_3\" and @name=\"complaint_text\"]");
    public static final By SEND_REPORT_DONE_BUTTON = By.xpath("//input[@type=\"submit\" and @class=\"modern_button\" and @value=\"Отправить\"]");
    public static final By SEND_REPOST_COMPARATIVE_MESSAGE = By.xpath("//div[@class='nodata' and contains(text(), 'Жалоба отправлена. Спасибо!')]");
}
