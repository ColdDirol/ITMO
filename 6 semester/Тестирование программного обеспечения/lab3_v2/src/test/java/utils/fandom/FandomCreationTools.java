package utils.fandom;

import org.openqa.selenium.By;

public class FandomCreationTools {

    public static By getFandomAuthor(String author) {
        return By.xpath("//div[@class='CommunityRight_Members']//a[@class='user' and normalize-space()='" + author + "']");
    }

    public static By getFandomTitle(String title) {
        return By.xpath("//div[@class='FandomHead_Title']//h1[normalize-space()='" + title + "']");
    }
}
