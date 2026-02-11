@echo off
REM Script to run the JSON Formatter application on Windows

echo ============================================================
echo   JSON Formatter - Building and Running Application
echo ============================================================
echo.

REM Check for --skip-build flag
set SKIP_BUILD=false
if "%1"=="--skip-build" set SKIP_BUILD=true

if "%SKIP_BUILD%"=="true" (
    echo WARNING: Skipping build (using existing JAR)
    echo.
    
    REM Check if JAR exists when skipping build
    if not exist "target\json-formatter-1.0.0.jar" (
        echo ERROR: JAR file not found! Cannot skip build.
        echo Please run without --skip-build flag.
        pause
        exit /b 1
    )
) else (
    REM Build the application
    echo Building application (this ensures you have the latest code)...
    echo Running: mvn clean package -DskipTests
    echo.
    
    call mvn clean package -DskipTests
    
    if errorlevel 1 (
        echo.
        echo ERROR: Build failed! Please check the errors above.
        pause
        exit /b 1
    )
    
    echo.
    echo Build successful!
    echo.
)

REM Run the application
echo Launching JSON Formatter application...
echo ============================================================
echo.
java -jar target\json-formatter-1.0.0.jar

pause
