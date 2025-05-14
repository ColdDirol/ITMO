package fanfic;

import org.openqa.selenium.By;

public record FanficPublicationLocators() {

    public static final By PUBLISH_FANFIC_PROPOSAL = By.xpath("//a[text()='Опубликовать свой фанфик']");
    public static final By FANFIC_NAME_INPUT = By.xpath("//input[@id='fic_name']");
    public static final By FANFIC_NAME_ACCEPT_BUTTON = By.xpath("//input[@type='submit' and @value='Создать фанфик']");
    public static final By GOTO_CREATED_FANFIC_BUTTON = By.xpath("//a[contains(@href, '/fic_write?action=edit_fic')]");

    public static final By GOTO_HEADER_BUTTON = By.xpath("//a[@class='ajax-link' and contains(@href, 'edit_fic') and contains(./b/text(), 'Шапка')]");

    // Персонажи
    public static final By CHARACTER_FILLING_EDIT_BUTTON = By.xpath("//td[@id='paring_edit']/a[contains(@onclick, \"paring_edit\")]");
    public static final By CHARACTER_FILLING_SELECTION_INPUT = By.xpath("//input[contains(@class, 'select2-search__field') and @placeholder='Выберите до 3-х персонажей']");
    public static final By CHARACTER_FILLING_SELECTION_DONE = By.xpath("//input[@value='Готово' and contains(@onclick, 'paring_edit_take')]");

    // Категория
    public static final By CATEGORY_FILLING_EDIT_BUTTON = By.xpath("//td[@id='het_slash_edit']/a[contains(@onclick, \"het_slash_edit\")]");
    public static final By CATEGORY_FILLING_SELECTION_INPUT_BUTTON = By.xpath("//div[@id='het_slash_value4']");

    // Рейтинг
    public static final By RATING_FILLING_EDIT_BUTTON = By.xpath("//td[@id='rating_edit']/a[contains(@onclick, \"rating_edit\")]");
    public static final By RATING_FILLING_SELECTION_INPUT_BUTTON = By.xpath("//div[@id='rating_R']");

    // Жанр
    public static final By GENRE_FILLING_EDIT_BUTTON = By.xpath("//td[@id='genre_edit']/a[contains(@onclick, \"genre_edit\")]");
    public static final By GENRE_FILLING_SELECTION_INPUT = By.xpath("//select[@name='genre[]' and @class='input_3']");
    public static final By GENRE_FILLING_SELECTION_DONE = By.xpath("//input[@value='Готово' and contains(@onclick, \"he_field_edit_take\")]");

    // Размер
    public static final By SIZE_FILLING_EDIT_BUTTON = By.xpath("//td[@id='size_edit']/a[contains(@onclick, \"size_edit\")]");
    public static final By SIZE_FILLING_SELECTION_INPUT_BUTTON = By.xpath("//div[@id='size_value1']");

    // Статус
    public static final By STATUS_FILLING_EDIT_BUTTON = By.xpath("//td[@id='status_edit']/a[contains(@onclick, \"status_edit\")]");
    public static final By STATUS_FILLING_SELECTION_INPUT_BUTTON = By.xpath("//div[@id='status_value1']");

    // Саммари
    public static final By SUMMARY_FILLING_EDIT_BUTTON = By.xpath("//td[@id='summary_edit']/a[contains(@onclick, \"textblock_edit\")]");
    public static final By SUMMARY_FILLING_SELECTION_INPUT = By.xpath("//form[@id='summary_form']/textarea[@name='value']");
    public static final By SUMMARY_FILLING_SELECTION_DONE = By.xpath("//input[@value='Готово' and contains(@onclick, \"textblock_take\")]");


    public static final By GOTO_ADD_CHAPTER_BUTTON = By.xpath("//a[contains(@class, 'IL_Add') and contains(text(), 'Добавить главу')]");
    public static final By CHAPTER_NAME_INPUT = By.xpath("//textarea[@id='chapterName']");
    public static final By CHAPTER_TEXT_INPUT = By.xpath("//textarea[@id='chapterText']");
    public static final By CHAPTER_DONE_BUTTON = By.xpath("//input[@type='button' and @value='Добавить главу']");

    public static final By PUBLISH_FANFIC_BUTTON = By.xpath("//a[contains(@class, 'IL_SendTp') and contains(@onclick, 'IL_SendT')]");
    public static final By PUBLISH_FANFIC_WITHOUT_CHECKING_OPTION_BUTTON = By.xpath("//a[contains(@class, 'modern_button') and contains(text(), 'Опубликовать без проверки')]");
    public static final By PUBLISH_FANFIC_CONFIRMATION_BUTTON = By.xpath("//input[@type='button' and @value='Опубликовать' and contains(@class, 'modern_button')]");

    public static final By REMOVE_PUBLICATION_STATUS_BUTTON = By.xpath("//a[contains(@class, 'IL_FromPubl') and contains(@onclick, 'IL_totrash_fic')]");
    public static final By REMOVE_PUBLICATION_STATUS_CONFIRMATION_BUTTON = By.xpath("//input[@type='button' and @value='Да, я хочу убрать фанфик']");
    public static final By GOTO_FANFIC_SETTINGS_BUTTON = By.xpath("//a[contains(@href, 'fic_write?action=edit_fic') and contains(text(), 'Вернуться')]");

    public static final By WATCH_AS_READER_BUTTON_BUTTON = By.xpath("//a[contains(@class, 'IL_View') and contains(@href, '/fic')]");
}
