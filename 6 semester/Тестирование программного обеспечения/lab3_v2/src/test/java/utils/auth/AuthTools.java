package utils.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebComponentTools;

public class AuthTools {

    public static void login(WebDriver driver, String login, String password) {
        WebComponentTools.gotoMainPage(driver);
        WebComponentTools.getViaXPath(driver, AuthLocators.AUTH_BUTTON).click();

        WebComponentTools.fillInputField(driver, AuthLocators.LOGIN_INPUT, login);
        WebComponentTools.fillInputField(driver, AuthLocators.PASSWORD_INPUT, password);

        WebComponentTools.getViaXPath(driver, AuthLocators.LOGIN_BUTTON).click();
    }

    public static void logout(WebDriver driver, String login) {
        WebComponentTools.gotoMainPage(driver);

        By accountMenuDropdownPath = getAccountMenuDropdownLocator(login);
        WebElement accountMenuDropdown = WebComponentTools.getViaXPath(driver, accountMenuDropdownPath);
        assert accountMenuDropdown != null;
        accountMenuDropdown.click();

        WebElement logoutButton = WebComponentTools.getViaXPath(driver, AuthLocators.LOGOUT_BUTTON);
        logoutButton.click();
    }

    public static By getAccountMenuDropdownLocator(String login) {
        return By.xpath("//td[@id='private_menu_name']//b[contains(text(), '" + login + "')]");
    }

}
