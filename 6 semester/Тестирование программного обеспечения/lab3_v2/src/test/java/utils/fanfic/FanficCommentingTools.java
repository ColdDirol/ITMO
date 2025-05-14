package utils.fanfic;

import org.openqa.selenium.By;

import java.util.Random;
import java.util.stream.Stream;

public class FanficCommentingTools {

    public static By getCommentByAuthorAndTextLocator(String author, String text) {
        return By.xpath(String.format(
                "//table[contains(@id,'MessageCommentTable_')]" +
                        "//a[normalize-space()='%s']" +
                        "/ancestor::tr/following-sibling::tr" +
                        "//div[contains(normalize-space(),'%s')]",
                author,
                text.replace("'", "\\'")
        ));
    }
}
