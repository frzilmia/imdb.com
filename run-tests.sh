# Run all tests
./gradlew clean test

# Generate Allure report
./gradlew allureReport

echo ""
echo "Tests completed!"
echo "To view the Allure report, run: ./gradlew allureServe"
echo ""
