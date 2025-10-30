package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TitlePage {

    // Locators
    private final SelenideElement pageTitle = $("h1");
    
    @Step("Get page title")
    public String getPageTitle() {
        return pageTitle.shouldBe(visible).getText().trim();
    }
    
    @Step("Get cast members count")
    public int getCastMembersCount() {
        int castCount = 0;
        
        if ($$("[data-testid='title-cast-item']").size() > 0) {
            castCount = $$("[data-testid='title-cast-item']").size();
        } else if ($$("a[href*='/name/nm']").size() > 0) {
            castCount = $$("a[href*='/name/nm']").size();
        } else if ($$(".ipc-sub-grid a[href*='/name/']").size() > 0) {
            castCount = $$(".ipc-sub-grid a[href*='/name/']").size();
        }
        
        return castCount;
    }
    
    @Step("Get cast member name at index {index}")
    public String getCastMemberName(int index) {
        if ($$("[data-testid='title-cast-item']").size() > 0) {
            return $$("[data-testid='title-cast-item']").get(index)
                .$("[data-testid='title-cast-item__actor']").getText().trim();
        } else if ($$("a[href*='/name/nm']").size() > 0) {
            return $$("a[href*='/name/nm']").get(index).getText().trim();
        } else {
            return $$(".ipc-sub-grid a[href*='/name/']").get(index).getText().trim();
        }
    }
    
    @Step("Get cast member profile URL at index {index}")
    public String getCastMemberProfileUrl(int index) {
        if ($$("[data-testid='title-cast-item']").size() > 0) {
            return $$("[data-testid='title-cast-item']").get(index)
                .$("a[href*='/name/']").getAttribute("href");
        } else if ($$("a[href*='/name/nm']").size() > 0) {
            return $$("a[href*='/name/nm']").get(index).getAttribute("href");
        } else {
            return $$(".ipc-sub-grid a[href*='/name/']").get(index).getAttribute("href");
        }
    }
    
    @Step("Navigate to cast member profile URL: {profileUrl}")
    public ProfilePage navigateToProfile(String profileUrl) {
        open(profileUrl);
        return new ProfilePage();
    }
    
    @Step("Verify cast count is greater than {expectedCount}")
    public boolean hasCastMembersGreaterThan(int expectedCount) {
        return getCastMembersCount() > expectedCount;
    }
}
