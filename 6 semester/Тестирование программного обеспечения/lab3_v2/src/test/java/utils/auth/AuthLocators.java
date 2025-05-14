package utils.auth;

import org.openqa.selenium.By;

public record AuthLocators() {
    public static final By AUTH_BUTTON = By.xpath("//a[text()='Вход' and contains(@onclick, 'HeaderSlideMenu1')]");
    public static final By LOGIN_INPUT = By.xpath("//input[@class='input_3' and @type='text']");
    public static final By PASSWORD_INPUT = By.xpath("//input[@class='input_3' and @type='password']");
    public static final By LOGIN_BUTTON = By.xpath("//input[@class='modern_button' and @type='submit' and @value='Войти']");
    public static final By LOGOUT_BUTTON = By.xpath("//a[contains(@href, '/autent.php?action=exit') and text()='Выход']");
}
