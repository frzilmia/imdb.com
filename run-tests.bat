@echo off
REM Run all tests
call gradlew.bat clean test

REM Generate Allure report
call gradlew.bat allureReport

echo.
echo Tests completed! 
echo To view the Allure report, run: gradlew.bat allureServe
echo.
pause
