package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {

    // Locators
    private final SelenideElement pageTitle = $("h1");
    
    @Step("Get profile name from page")
    public String getProfileName() {
        pageTitle.shouldBe(visible);
        
        // Try different selectors for the profile name
        if ($("h1 span").exists()) {
            return $("h1 span").getText().trim();
        } else if ($("h1").exists()) {
            return $("h1").getText().trim();
        }
        
        return "";
    }
    
    @Step("Verify profile name matches expected: {expectedName}")
    public boolean verifyProfileName(String expectedName) {
        String actualName = getProfileName();
        return actualName.equals(expectedName) || 
               expectedName.contains(actualName) || 
               actualName.contains(expectedName);
    }
}
