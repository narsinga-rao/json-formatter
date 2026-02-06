#!/bin/bash
# Script to run the JSON Formatter application

echo "Starting JSON Formatter application..."
echo "Make sure you have Java 11 or higher installed."
echo ""

# Build the application if needed
if [ ! -f "target/json-formatter-1.0.0.jar" ]; then
    echo "JAR file not found. Building the application..."
    mvn clean package -DskipTests
fi

# Run the application
echo "Launching application..."
java -jar target/json-formatter-1.0.0.jar
