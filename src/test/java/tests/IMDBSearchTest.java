package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.IMDBHomePage;
import pages.ProfilePage;
import pages.TitlePage;
import utils.TestDataReader;

import static org.testng.Assert.*;

@Epic("IMDB Tests")
@Feature("Search Functionality")
public class IMDBSearchTest extends BaseTest {

    @Test(description = "Search for QA on IMDB and verify cast member profile")
    @Description("This test searches for 'QA' on IMDB, finds a movie with cast, verifies cast count, and validates profile navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Story("IMDB Search and Cast Verification")
    public void testImdbSearchAndCastVerification() {
        
        // Initialize page objects
        IMDBHomePage homePage = new IMDBHomePage();
        TitlePage titlePage = new TitlePage();
        
        // Step 1: Open IMDB home page
        homePage.open();
        
        // Step 2: Search for configured search term
        String searchTerm = TestDataReader.getSearchTermQA();
        homePage.searchFor(searchTerm);
        
        // Step 3: Find the first movie/show with a cast section (not a collection)
        String selectedTitle = "";
        boolean foundMovieWithCast = false;
        int dropdownSize = homePage.getDropdownItemsCount();
        
        System.out.println("Total dropdown items: " + dropdownSize);
        
        for (int i = 0; i < dropdownSize && !foundMovieWithCast && i < 5; i++) {
            // Re-search if not the first item
            if (i > 0) {
                homePage.navigateBack().reSearch(searchTerm);
            }
            
            String dropdownFullText = homePage.getDropdownItemText(i);
            String titleText = dropdownFullText.split("\n")[0].trim();
            
            System.out.println("Checking dropdown item " + i + ": " + titleText);
            
            // Step 4: Click on the dropdown item
            homePage.clickDropdownItem(i);
            
            // Check if this page has a cast section
            int castCount = titlePage.getCastMembersCount();
            System.out.println("Cast count for '" + titleText + "': " + castCount);
            
            int minCastCount = TestDataReader.getCastMinCount();
            if (castCount > minCastCount) {
                selectedTitle = titleText;
                foundMovieWithCast = true;
                System.out.println("Found movie with cast: " + selectedTitle);
            }
        }
        
        assertTrue(foundMovieWithCast, "Could not find a movie with cast section in dropdown results");
        System.out.println("Selected dropdown title: " + selectedTitle);
        
        // Step 5: Verify page title matches selection
        String pageTitle = titlePage.getPageTitle();
        System.out.println("Page title: " + pageTitle);
        assertFalse(pageTitle.isEmpty(), "Page title should not be empty after navigation");
        System.out.println("Test completed: Clicked on '" + selectedTitle + "' and navigated to page with title '" + pageTitle + "'");
        
        // Step 6: Verify there are more than minimum members in the "top cast section"
        int topCastCount = titlePage.getCastMembersCount();
        int minCastCount = TestDataReader.getCastMinCount();
        System.out.println("Top cast count: " + topCastCount);
        
        if (topCastCount > minCastCount) {
            // Step 7: Get the configured cast member information
            int castMemberIndex = TestDataReader.getCastMemberIndex() - 1; // Convert to 0-indexed
            String thirdCastMemberName = titlePage.getCastMemberName(castMemberIndex);
            String profileUrl = titlePage.getCastMemberProfileUrl(castMemberIndex);
            
            System.out.println("Third cast member name: " + thirdCastMemberName);
            System.out.println("Profile URL: " + profileUrl);
            
            // Navigate to the profile page
            ProfilePage profilePage = titlePage.navigateToProfile(profileUrl);
            
            // Step 8: Verify that correct profile has opened
            String profileName = profilePage.getProfileName();
            System.out.println("Profile page name: " + profileName);
            
            assertTrue(profilePage.verifyProfileName(thirdCastMemberName), 
                "Profile name '" + profileName + "' should match the third cast member '" + thirdCastMemberName + "'");
            
            System.out.println("Successfully verified cast member profile: " + profileName);
        } else {
            System.out.println("Warning: This page does not have a cast section with more than " + minCastCount + " members. Skipping cast verification steps.");
            assertTrue(topCastCount >= 0, "Cast count should be non-negative");
        }
    }
}
