package discussion;

import org.openqa.selenium.By;

public record FandomCreationLocators() {

    public static final By CREATE_NEW_FANDOM_BUTTON = By.xpath("//a[@class=\"red\" and contains(@href, \"section=fandoms\")]/b[text()=\"Создать новый фандом\"]");
    public static final By CREATE_NEW_FANDOM_POLICY_AGREEMENT_BUTTON = By.xpath("//a[@class=\"normal_link dashed\" and contains(@onclick, \"FandomAddContainer\")]");
    public static final By CREATE_NEW_FANDOM_NAME_INPUT = By.xpath("//input[@name=\"title\" and @class=\"input_3\" and @type=\"text\"]");
    public static final By CREATE_NEW_FANDOM_NAME_DONE_BUTTON = By.xpath("//input[@value=\"Создать фандом\"]");

    public static final By FANDOM_DELETION_BUTTON = By.xpath("//a[@class='normal_link dashed AjaxToModal' and contains(@data-action, 'fandom_del_form') and text()='Удалить фандом']");
    public static final By FANDOM_DELETION_CONFIRMATION = By.xpath("//input[@class='modern_button' and @value='Уверен, удаляйте!']");
    public static final By FANDOM_DELETION_FINISHED_MESSAGE = By.xpath("//div[@id='site-content-center']//div[contains(@class, 'nodata') and contains(text(), 'Фандом удалён')]");
}
