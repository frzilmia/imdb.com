package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import java.time.Duration;

@Epic("IMDB Tests")
@Feature("Search Functionality")
public class SampleTest extends BaseTest {

    @Test(description = "Search for QA on IMDB and verify first result")
    @Description("This test searches for 'QA' on IMDB and verifies search functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Story("IMDB Search")
    public void testImdbSearch() {
        try {
            // Step 1: Open imdb.com
            open("https://www.imdb.com");
            
            // Wait for page to load
            $("body").shouldBe(visible);
            
            // Step 2: Search for "QA" with the search bar
            $("#suggestion-search").shouldBe(visible, Duration.ofSeconds(10)).setValue("QA");
            
            // Wait for the search results dropdown to appear
            $(".react-autosuggest__suggestions-list").shouldBe(visible, Duration.ofSeconds(10));
            
            // Step 3: Click on the first search result
            $(".react-autosuggest__suggestions-list li").shouldBe(visible).click();
            
            // Step 4: Verify that we navigated to a page with a title
            $("h1").shouldBe(visible, Duration.ofSeconds(15));
            String pageTitle = $("h1").getText().trim();
            
            System.out.println("Navigated to page with title: " + pageTitle);
            
            // Verify navigation was successful
            assert !pageTitle.isEmpty() : "Page title should not be empty after navigation";
            
            // Simple verification that we're on an IMDB page
            $("body").shouldHave(text("IMDb"));
            
            System.out.println("Test completed successfully - navigation verified");
            
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
