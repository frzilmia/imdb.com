# IMDB Test Automation Project

This project uses Gradle, Selenide, Java 17, TestNG, and Allure for test automation with **Page Object Model (POM)** design pattern.

## Prerequisites

- Java 17 installed
- Gradle installed (or use the Gradle wrapper)
- Chrome browser

## Project Structure

```
imdb.com/
├── src/test/
│   ├── java/
│   │   ├── pages/                    # Page Object Model classes
│   │   │   ├── IMDBHomePage.java    # Home page - search & dropdown
│   │   │   ├── TitlePage.java       # Movie/Show page - cast section
│   │   │   └── ProfilePage.java     # Actor profile page
│   │   ├── tests/                    # Test classes
│   │   │   ├── BaseTest.java        # Base test configuration
│   │   │   ├── SampleTest.java      # Legacy test (deprecated)
│   │   │   └── IMDBSearchTest.java  # POM-based test
│   │   └── utils/                    # Utility classes
│   │       └── TestDataReader.java  # Test data configuration reader
│   └── resources/
│       ├── testng.xml               # TestNG suite configuration
│       ├── allure.properties        # Allure configuration
│       └── testdata.properties      # Test data (URLs, search terms)
├── build.gradle                      # Project dependencies
├── settings.gradle
├── gradle.properties
├── README.md                         # This file
├── POM_GUIDE.md                     # Page Object Model guide
└── TEST_SCENARIO.md                 # Detailed test scenario
```

## Running Tests

### Run all tests
```bash
.\gradlew.bat clean test
```

### Run specific test
```bash
.\gradlew.bat clean test --tests IMDBSearchTest
```

### Generate Allure report
```bash
.\gradlew.bat allureReport
```

### Serve Allure report (opens in browser)
```bash
.\gradlew.bat allureServe
```

### Quick test execution script
```bash
.\run-tests.bat
```

## GitHub Actions CI/CD

### ✅ Major GitHub Actions Improvements Applied!

This project includes a comprehensive GitHub Actions workflow that automatically runs tests on every push and pull request. The workflow has been optimized for reliability and provides detailed error reporting.

#### **Key Improvements Made:**

**1. Enhanced Chrome Configuration:**
- Proper W3C WebDriver standards compliance
- Optimized headless mode for CI environments
- Memory optimization and stability flags
- Remote debugging capabilities

**2. Improved Test Reliability:**
- Increased timeouts for CI environments (15s page load, 10s element wait)
- Duration-based timeouts instead of hard-coded sleeps
- Better error handling with `continue-on-error` for debugging
- Simplified test logic to reduce complexity

**3. Better Error Reporting:**
- Detailed test failure information extraction
- XML result parsing to show actual failure causes
- Enhanced logging and stack traces
- Flexible artifact collection with comprehensive reporting

**4. CI Environment Optimization:**
- Java 17 environment setup matching project requirements
- Xvfb virtual display for headless testing
- Chrome and ChromeDriver automatic installation
- Gradle caching for faster builds

#### **Workflow Features:**
- **Triggers**: Push to master/main, Pull requests, Manual dispatch
- **Environment**: Ubuntu Latest with Java 17
- **Browser**: Chrome (headless mode in CI)
- **Reporting**: Allure reports with 30-day retention
- **Artifacts**: Test results, screenshots on failure, detailed logs

#### **Monitor Your Workflow:**
Access your automated test results and reports at:
**🔗 [GitHub Actions Dashboard](https://github.com/frzilmia/imdb.com/actions)**

**Available Artifacts After Each Run:**
- 📊 **Allure Report** - Comprehensive test reporting with steps and screenshots
- 📋 **Test Results** - Detailed XML and HTML test reports
- 📸 **Screenshots** - Captured on test failures (7-day retention)
- 📝 **Build Logs** - Complete execution logs with stack traces

#### **Workflow Status:**
- ✅ **Chrome Capabilities**: W3C compliant, no deprecated warnings
- ✅ **Test Execution**: Simplified logic for better CI reliability
- ✅ **Error Reporting**: Detailed failure analysis and debugging info
- ✅ **Artifact Generation**: Comprehensive reporting and evidence collection

The workflow runs automatically on code changes and provides immediate feedback on test results, making it easy to catch issues early in the development process.

## Technologies Used

- **Java 17**: Programming language
- **Gradle 8.5**: Build automation tool
- **Selenide 7.0.4**: Web testing framework with built-in waits
- **TestNG 7.8.0**: Testing framework for test execution
- **Allure 2.24.0**: Advanced test reporting framework
- **Page Object Model**: Design pattern for maintainable tests

## Architecture

### Page Object Model (POM)
This project follows the **Page Object Model** design pattern for better:
- **Maintainability**: Update UI selectors in one place
- **Reusability**: Share page objects across multiple tests
- **Readability**: Tests focus on business logic, not implementation
- **Scalability**: Easy to add new tests and page objects

### Test Data Externalization
All test data is externalized using a **properties file approach**:
- **Separation of Concerns**: Test data separated from test logic
- **Easy Maintenance**: Update URLs, search terms, or thresholds without code changes
- **Environment Flexibility**: Can create multiple property files (dev, staging, prod)
- **Type Safety**: `TestDataReader` utility provides typed access to properties
- **Single Source of Truth**: All test data in one location

### Utility Classes

#### TestDataReader
Centralized test data management:
- `getImdbBaseUrl()` - Get base URL for IMDB
- `getSearchTermQA()` - Get search term
- `getCastMinCount()` - Get minimum cast threshold
- `getCastMemberIndex()` - Get cast member to verify
- `getDropdownWait()` / `getPageLoadWait()` - Get timeout values

### Page Objects

#### IMDBHomePage
Handles IMDB home page interactions:
- `open()` - Navigate to IMDB (uses externalized URL)
- `searchFor(query)` - Enter search term
- `clickDropdownItem(index)` - Click dropdown result
- `getDropdownItemsCount()` - Count search results

#### TitlePage
Handles movie/TV show page interactions:
- `getPageTitle()` - Get title name
- `getCastMembersCount()` - Count cast members
- `getCastMemberName(index)` - Get actor name
- `navigateToProfile(url)` - Go to actor profile

#### ProfilePage
Handles actor profile page interactions:
- `getProfileName()` - Get actor/actress name
- `verifyProfileName(expected)` - Verify profile

## Test Scenarios

### IMDBSearchTest
**Objective**: Search IMDB, verify cast information, and validate profile navigation

**Steps**:
1. Open IMDB home page
2. Search for configured term (from `testdata.properties`)
3. Find first movie/show with cast section (threshold from config)
4. Verify page navigation
5. Validate cast count against configured minimum
6. Navigate to configured cast member profile
7. Verify correct profile loaded

**Configuration Used**:
- Base URL: `imdb.base.url`
- Search Term: `search.term.qa` (default: "QA")
- Minimum Cast: `cast.min.count` (default: 3)
- Cast Member Index: `cast.member.index` (default: 3rd member)

**Status**: ✅ PASSED (100% success rate)

See [TEST_SCENARIO.md](TEST_SCENARIO.md) for detailed results.

## Configuration

### Test Data Configuration (`testdata.properties`)
Test data is externalized in `src/test/resources/testdata.properties`:

```properties
# Base URLs
imdb.base.url=https://www.imdb.com

# Search Terms
search.term.qa=QA

# Test Thresholds
cast.min.count=3
cast.member.index=3

# Timeouts (milliseconds)
dropdown.wait=2000
page.load.wait=3000
```

**Usage in Tests:**
```java
// In page objects
String url = TestDataReader.getImdbBaseUrl();
homePage.open(); // Uses externalized URL

// In test classes
String searchTerm = TestDataReader.getSearchTermQA();
homePage.searchFor(searchTerm); // Uses externalized search term

int minCast = TestDataReader.getCastMinCount();
if (castCount > minCast) { ... } // Uses externalized threshold
```

**Benefits:**
- ✅ Change test data without modifying code
- ✅ Support multiple environments (dev, staging, prod)
- ✅ Easy to add new search terms or thresholds
- ✅ Centralized configuration management
- ✅ Type-safe access through utility class

### Other Configuration Files
- **Browser settings**: `BaseTest.java`
- **TestNG suite**: `src/test/resources/testng.xml`
- **Allure config**: `src/test/resources/allure.properties`
- **Dependencies**: `build.gradle`

## Documentation

- **README.md** - This file (project overview)
- **POM_GUIDE.md** - Detailed Page Object Model guide
- **TEST_SCENARIO.md** - Complete test execution report

## Adding New Tests

### Using Existing Page Objects and Test Data

1. Create test class in `tests/` extending `BaseTest`
2. Use existing page objects from `pages/` package
3. Use `TestDataReader` for externalized data
4. Add `@Step` annotations for Allure reporting
5. Run and verify with `.\gradlew.bat test`

**Example:**
```java
@Test
public void testNewScenario() {
    // Use page objects
    IMDBHomePage homePage = new IMDBHomePage();
    homePage.open(); // Uses TestDataReader.getImdbBaseUrl()
    
    // Use externalized test data
    String searchTerm = TestDataReader.getSearchTermQA();
    homePage.searchFor(searchTerm);
    
    // Rest of test logic
}
```

### Adding New Test Data

1. Add new property to `testdata.properties`:
```properties
search.term.thriller=Thriller
movie.title.inception=Inception
```

2. Add getter method to `TestDataReader.java`:
```java
public static String getSearchTermThriller() {
    return getProperty("search.term.thriller");
}
```

3. Use in tests:
```java
String term = TestDataReader.getSearchTermThriller();
homePage.searchFor(term);
```
