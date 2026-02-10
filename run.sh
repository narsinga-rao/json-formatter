#!/bin/bash
# Script to run the JSON Formatter application

echo "════════════════════════════════════════════════════════"
echo "  JSON Formatter - Building and Running Application"
echo "════════════════════════════════════════════════════════"
echo ""

# Check for --skip-build flag
SKIP_BUILD=false
if [ "$1" == "--skip-build" ]; then
    SKIP_BUILD=true
    echo "⚠️  Skipping build (using existing JAR)"
fi

# Build the application (unless --skip-build is specified)
if [ "$SKIP_BUILD" == "false" ]; then
    echo "🔨 Building application (this ensures you have the latest code)..."
    echo "   Running: mvn clean package -DskipTests"
    echo ""
    mvn clean package -DskipTests
    
    if [ $? -ne 0 ]; then
        echo ""
        echo "❌ Build failed! Please check the errors above."
        exit 1
    fi
    echo ""
    echo "✅ Build successful!"
    echo ""
else
    # Check if JAR exists when skipping build
    if [ ! -f "target/json-formatter-1.0.0.jar" ]; then
        echo "❌ JAR file not found! Cannot skip build."
        echo "   Please run without --skip-build flag."
        exit 1
    fi
fi

# Run the application
echo "🚀 Launching JSON Formatter application..."
echo "════════════════════════════════════════════════════════"
echo ""
java -jar target/json-formatter-1.0.0.jar
