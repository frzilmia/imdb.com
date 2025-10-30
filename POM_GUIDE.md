# Page Object Model (POM) Implementation Guide

## 📁 Project Structure

```
src/test/java/
├── pages/                          # Page Object classes
│   ├── IMDBHomePage.java          # Home page with search functionality
│   ├── TitlePage.java             # Movie/Show page with cast section
│   └── ProfilePage.java           # Actor profile page
├── tests/                          # Test classes
│   ├── BaseTest.java              # Base test configuration
│   ├── SampleTest.java            # Original test (legacy)
│   └── IMDBSearchTest.java        # Refactored test using POM
└── resources/
    ├── testng.xml                 # TestNG configuration
    └── allure.properties          # Allure configuration
```

---

## 🏗️ Page Object Classes

### 1. **IMDBHomePage.java**
Handles all interactions with the IMDB home page.

**Responsibilities:**
- Opening IMDB website
- Search functionality
- Dropdown interactions
- Navigation

**Key Methods:**
- `open()` - Navigate to IMDB home page
- `searchFor(String query)` - Enter search query
- `getDropdownItemsCount()` - Count dropdown results
- `clickDropdownItem(int index)` - Click on specific dropdown item
- `navigateBack()` - Browser back navigation
- `reSearch(String query)` - Clear and re-enter search

**Example Usage:**
```java
IMDBHomePage homePage = new IMDBHomePage();
homePage.open()
        .searchFor("Inception");
int resultsCount = homePage.getDropdownItemsCount();
homePage.clickDropdownItem(0);
```

---

### 2. **TitlePage.java**
Handles interactions on movie/TV show pages.

**Responsibilities:**
- Page title verification
- Cast section interactions
- Cast member information extraction

**Key Methods:**
- `getPageTitle()` - Get the movie/show title
- `getCastMembersCount()` - Count cast members
- `getCastMemberName(int index)` - Get cast member name
- `getCastMemberProfileUrl(int index)` - Get profile URL
- `navigateToProfile(String url)` - Navigate to actor profile
- `hasCastMembersGreaterThan(int count)` - Verify cast count

**Example Usage:**
```java
TitlePage titlePage = new TitlePage();
String title = titlePage.getPageTitle();
int castCount = titlePage.getCastMembersCount();
String actorName = titlePage.getCastMemberName(2);
String profileUrl = titlePage.getCastMemberProfileUrl(2);
ProfilePage profile = titlePage.navigateToProfile(profileUrl);
```

---

### 3. **ProfilePage.java**
Handles interactions on actor/actress profile pages.

**Responsibilities:**
- Profile name extraction
- Profile verification

**Key Methods:**
- `getProfileName()` - Extract actor/actress name
- `verifyProfileName(String expected)` - Verify profile matches expected name

**Example Usage:**
```java
ProfilePage profilePage = new ProfilePage();
String name = profilePage.getProfileName();
boolean isCorrect = profilePage.verifyProfileName("Leonardo DiCaprio");
```

---

## 🧪 Test Implementation

### Before (Without POM)
```java
// All selectors and logic mixed in test
open("https://www.imdb.com");
$("#suggestion-search").setValue("QA");
$$(".react-autosuggest__suggestions-list li").first().click();
String title = $("h1").getText();
```

### After (With POM)
```java
// Clean, readable test using page objects
IMDBHomePage homePage = new IMDBHomePage();
homePage.open().searchFor("QA");
homePage.clickDropdownItem(0);

TitlePage titlePage = new TitlePage();
String title = titlePage.getPageTitle();
```

---

## ✅ Benefits Achieved

### 1. **Separation of Concerns**
- Tests focus on **WHAT** to test
- Pages focus on **HOW** to interact with UI

### 2. **Reusability**
- Page objects can be used across multiple tests
- Common actions defined once

### 3. **Maintainability**
- UI changes? Update only the page object
- All tests using that page object automatically benefit

### 4. **Readability**
- Tests read like business scenarios
- Technical details hidden in page objects

### 5. **Allure Integration**
- `@Step` annotations on page methods
- Detailed step-by-step reporting

---

## 🚀 Running Tests

### Run All Tests
```powershell
.\gradlew.bat clean test
```

### Run Specific Test
```powershell
.\gradlew.bat clean test --tests IMDBSearchTest
```

### Generate Allure Report
```powershell
.\gradlew.bat allureReport
.\gradlew.bat allureServe
```

---

## 📊 Comparison: Old vs New

| Aspect | Without POM (SampleTest) | With POM (IMDBSearchTest) |
|--------|--------------------------|---------------------------|
| **Lines of Code** | ~160 lines | ~100 lines (test only) |
| **Readability** | Mixed selectors & logic | Clean, business-focused |
| **Maintainability** | Hard to update selectors | Easy - one place to update |
| **Reusability** | None | High - use in multiple tests |
| **Testability** | Hard to test page logic | Page objects can be unit tested |

---

## 🔄 Migration Path

You can run **both** tests simultaneously:
- `SampleTest.java` - Original implementation (legacy)
- `IMDBSearchTest.java` - New POM implementation

**Recommended Next Steps:**
1. ✅ Validate POM test works correctly
2. ✅ Create additional tests using page objects
3. ✅ Deprecate/remove SampleTest.java
4. ✅ Expand page objects as needed

---

## 📝 Best Practices Implemented

### 1. **Page Objects Return `this`**
Enables method chaining:
```java
homePage.open()
        .searchFor("QA")
        .clickDropdownItem(0);
```

### 2. **Meaningful Method Names**
- `getCastMemberName()` instead of `getText()`
- `navigateToProfile()` instead of `click()`

### 3. **Encapsulation**
- Locators are private
- Only expose actions, not implementation

### 4. **Wait Strategies**
- Waits handled in page objects
- Tests don't worry about timing

### 5. **Allure Steps**
- Each page method annotated with `@Step`
- Automatic detailed reporting

---

## 🎯 Example: Adding a New Test

```java
@Test
public void testSearchForMovie() {
    IMDBHomePage homePage = new IMDBHomePage();
    TitlePage titlePage = new TitlePage();
    
    homePage.open()
            .searchFor("The Matrix");
    
    homePage.clickDropdownItem(0);
    
    String title = titlePage.getPageTitle();
    assertEquals(title, "The Matrix");
    
    assertTrue(titlePage.hasCastMembersGreaterThan(5));
}
```

**That's it!** No need to worry about selectors, waits, or implementation details.

---

## 🔧 Extending the Framework

### Add New Page Object
1. Create class in `pages/` package
2. Define locators as private fields
3. Create public methods for actions
4. Annotate with `@Step` for Allure
5. Return `this` or next page object

### Add New Test
1. Create test in `tests/` package
2. Extend `BaseTest`
3. Use page objects
4. Focus on business logic
5. Add Allure annotations

---

## 📚 Summary

The Page Object Model implementation provides:
- ✅ **Clean separation** between test logic and UI interactions
- ✅ **Easy maintenance** - update selectors in one place
- ✅ **Reusable components** - use across multiple tests
- ✅ **Better readability** - tests read like user scenarios
- ✅ **Professional structure** - industry best practice
- ✅ **Scalable architecture** - ready for growth

**Your project is now production-ready and follows industry best practices!** 🎉
