# IMDB Test Automation - Test Scenario Report

## Test Information
- **Test Name:** `testImdbSearch`
- **Test Framework:** TestNG + Selenide + Allure
- **Browser:** Chrome 141.0.7390.123
- **Date:** October 30, 2025
- **Duration:** 57.316 seconds
- **Status:** ✅ **PASSED** (100% Success Rate)

---

## Test Scenario

### Objective
Automate IMDB search functionality, verify navigation, and validate cast member information.

### Pre-conditions
- IMDB website is accessible
- Chrome browser is installed
- Internet connection is available

---

## Test Steps & Results

### **Step 1: Open IMDB Website** ✅
- **Action:** Navigate to https://www.imdb.com
- **Expected:** Website loads successfully
- **Actual:** ✅ Website loaded successfully

---

### **Step 2: Search for "QA"** ✅
- **Action:** Enter "QA" in the search bar
- **Expected:** Search dropdown appears with results
- **Actual:** ✅ Dropdown displayed with 13 results

---

### **Step 3: Find First Movie/Show with Cast Section** ✅
- **Action:** Iterate through dropdown results to find the first title with a cast section (more than 3 cast members)
- **Expected:** Find a movie/show with cast members
- **Actual:** 
  - ✅ Checked "Halloween Family Fun" - 0 cast members (skipped)
  - ✅ Checked "Stranger Things" - 18 cast members (selected)
  - ✅ Successfully identified "Stranger Things" as first result with cast

---

### **Step 4: Navigate to Selected Title** ✅
- **Action:** Click on "Stranger Things" from dropdown
- **Expected:** Navigate to "Stranger Things" page
- **Actual:** ✅ Successfully navigated to page with title "Stranger Things"

---

### **Step 5: Verify Page Title** ✅
- **Action:** Verify the page title matches the selected dropdown item
- **Expected:** Page title should be "Stranger Things"
- **Actual:** ✅ Page title confirmed as "Stranger Things"

---

### **Step 6: Verify Cast Section Has More Than 3 Members** ✅
- **Action:** Count cast members in the "Top Cast" section
- **Expected:** More than 3 cast members should be present
- **Actual:** ✅ Found 18 cast members (exceeds requirement)

---

### **Step 7: Click on 3rd Cast Member** ✅
- **Action:** 
  - Save the name of the 3rd cast member
  - Extract profile URL
  - Navigate to profile page
- **Expected:** Successfully navigate to 3rd cast member's profile
- **Actual:** 
  - ✅ 3rd cast member identified: "Finn Wolfhard"
  - ✅ Profile URL extracted: https://www.imdb.com/name/nm6016511/?ref_=tt_cst_i_3
  - ✅ Successfully navigated to profile page

---

### **Step 8: Verify Correct Profile Opened** ✅
- **Action:** Verify the profile page name matches the 3rd cast member's name
- **Expected:** Profile name should be "Finn Wolfhard"
- **Actual:** ✅ Profile page name confirmed as "Finn Wolfhard"

---

## Test Summary

### ✅ All Test Steps Passed

| Step | Description | Status |
|------|-------------|--------|
| 1 | Open IMDB | ✅ PASS |
| 2 | Search for "QA" | ✅ PASS |
| 3 | Find movie with cast | ✅ PASS |
| 4 | Navigate to title | ✅ PASS |
| 5 | Verify page title | ✅ PASS |
| 6 | Verify cast count > 3 | ✅ PASS |
| 7 | Click 3rd cast member | ✅ PASS |
| 8 | Verify profile page | ✅ PASS |

---

## Technical Implementation Details

### Technologies Used
- **Java 17**
- **Gradle 8.5** (Build tool)
- **Selenide 7.0.4** (Web automation framework)
- **TestNG 7.8.0** (Test framework)
- **Allure 2.24.0** (Reporting framework)
- **Chrome WebDriver 141.0.7390.123**

### Key Features Implemented
1. **Smart Dropdown Navigation:** Iterates through search results to find movies with cast sections
2. **Multiple Selector Fallbacks:** Uses various CSS selectors to handle different IMDB page structures
3. **Robust Error Handling:** Handles cases where pages don't have cast sections
4. **Direct URL Navigation:** Extracts and navigates to profile URLs for reliability
5. **Flexible Text Matching:** Supports partial matches for profile name verification
6. **Detailed Logging:** Console output at each step for debugging and verification

### Challenges Resolved
1. ✅ **Search Results Without Cast:** Initial "QA" results led to collection pages - resolved by iterating to find first movie with cast
2. ✅ **Element Click Interception:** Cast member links were intercepted by overlays - resolved by extracting URL and navigating directly
3. ✅ **Dynamic Selectors:** IMDB's varying page structures - resolved with multiple selector fallbacks
4. ✅ **Page Load Timing:** Asynchronous content loading - resolved with strategic wait times
5. ✅ **Profile Name Selectors:** Inconsistent H1 structure - resolved with conditional selector logic

---

## Test Data

### Search Query
- **Input:** "QA"
- **Results Found:** 13 dropdown items

### Selected Content
- **Title:** Stranger Things
- **Type:** TV Series
- **Cast Count:** 18 members
- **3rd Cast Member:** Finn Wolfhard
- **Profile URL:** https://www.imdb.com/name/nm6016511/?ref_=tt_cst_i_3

---

## Console Output Logs
```
Total dropdown items: 13
Checking dropdown item 0: Halloween Family Fun
Cast count for 'Halloween Family Fun': 0
Checking dropdown item 1: Stranger Things
Cast count for 'Stranger Things': 18
Found movie with cast: Stranger Things
Selected dropdown title: Stranger Things
Page title: Stranger Things
Test completed: Clicked on 'Stranger Things' and navigated to page with title 'Stranger Things'
Top cast count: 18
Third cast member name: Finn Wolfhard
Profile URL: https://www.imdb.com/name/nm6016511/?ref_=tt_cst_i_3
Profile page name: Finn Wolfhard
Successfully verified cast member profile: Finn Wolfhard
```

---

## Conclusion

✅ **Test Execution: SUCCESSFUL**

All 8 test steps completed successfully. The automation successfully:
- Searched IMDB for "QA"
- Intelligently selected a movie with cast members
- Verified page navigation and titles
- Validated cast member count
- Navigated to a specific cast member's profile
- Confirmed the correct profile was loaded

The test is **production-ready** and can be integrated into CI/CD pipelines.

---

## How to Run the Test

```powershell
# Run all tests
.\gradlew.bat clean test

# Generate Allure report
.\gradlew.bat allureReport

# View Allure report in browser
.\gradlew.bat allureServe
```

---

## Report Locations
- **HTML Report:** `build/reports/tests/test/index.html`
- **XML Results:** `build/test-results/test/TEST-tests.SampleTest.xml`
- **Allure Results:** `build/allure-results/`
- **Screenshots:** `build/reports/tests/` (on failure)
