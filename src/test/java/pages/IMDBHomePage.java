package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import utils.TestDataReader;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class IMDBHomePage {

    // Locators
    private final SelenideElement searchInput = $("#suggestion-search");
    
    @Step("Open IMDB home page")
    public IMDBHomePage open() {
        com.codeborne.selenide.Selenide.open(TestDataReader.getImdbBaseUrl());
        return this;
    }
    
    @Step("Search for '{query}'")
    public IMDBHomePage searchFor(String query) {
        searchInput.shouldBe(visible).setValue(query);
        sleep(2000); // Wait for dropdown to populate
        return this;
    }
    
    @Step("Get total dropdown items count")
    public int getDropdownItemsCount() {
        return $$(".react-autosuggest__suggestions-list li").size();
    }
    
    @Step("Get dropdown item text at index {index}")
    public String getDropdownItemText(int index) {
        return $$(".react-autosuggest__suggestions-list li").get(index).getText();
    }
    
    @Step("Click on dropdown item at index {index}")
    public void clickDropdownItem(int index) {
        $$(".react-autosuggest__suggestions-list li").get(index).click();
        sleep(3000); // Wait for page to load
    }
    
    @Step("Navigate back to previous page")
    public IMDBHomePage navigateBack() {
        back();
        sleep(1000);
        return this;
    }
    
    @Step("Clear and re-enter search query '{query}'")
    public IMDBHomePage reSearch(String query) {
        searchInput.shouldBe(visible).clear();
        searchInput.setValue(query);
        sleep(2000);
        return this;
    }
}
