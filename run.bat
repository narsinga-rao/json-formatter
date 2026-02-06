@echo off
REM Script to run the JSON Formatter application on Windows

echo Starting JSON Formatter application...
echo Make sure you have Java 11 or higher installed.
echo.

REM Build the application if needed
if not exist "target\json-formatter-1.0.0.jar" (
    echo JAR file not found. Building the application...
    call mvn clean package -DskipTests
)

REM Run the application
echo Launching application...
java -jar target\json-formatter-1.0.0.jar

pause
