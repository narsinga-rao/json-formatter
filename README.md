# JSON Formatter

A desktop application to format JSON payloads and display them as collapsible and expandable nodes.

## Tech Stack
- **UI:** JavaFX
- **Backend:** Java with Spring Boot
- **JSON Processing:** Jackson Library
- **Build Tool:** Maven

## Features
- **Left Panel:** JSON editor for pasting or editing JSON payloads with monospaced font
- **Right Panel:** Structured, collapsible/expandable tree view of the formatted JSON
- **Format Button:** Trigger JSON parsing and display the structured view
- **Clear Button:** Clear all content from both panels
- **Error Handling:** User-friendly alerts for invalid JSON with detailed error messages
- **Real-time Validation:** Validates JSON structure and displays errors

## Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

## Build Instructions

### Building the Application
```bash
# Clean and package the application
mvn clean package

# This will create a JAR file in the target/ directory
```

### Running the Application

#### Option 1: Using Maven
```bash
mvn spring-boot:run
```

#### Option 2: Using the JAR file
```bash
java -jar target/json-formatter-1.0.0.jar
```

#### Option 3: Using JavaFX Maven Plugin
```bash
mvn javafx:run
```

## Usage

1. **Launch the Application:** Use one of the run commands above
2. **Input JSON:** Paste or type your JSON content in the left text area
3. **Format:** Click the "Format JSON" button to parse and display the JSON
4. **View Structure:** The right panel will display the JSON as an expandable tree structure
   - JSON objects are shown with `{ }` and can be expanded to view key-value pairs
   - JSON arrays are shown with `[ ]` and can be expanded to view elements by index
   - Primitive values (strings, numbers, booleans, null) are displayed as leaf nodes
5. **Clear:** Click the "Clear" button to reset both panels

## Example JSON

Try pasting this sample JSON:
```json
{
  "name": "John Doe",
  "age": 30,
  "isEmployed": true,
  "address": {
    "street": "123 Main St",
    "city": "New York",
    "zipCode": "10001"
  },
  "phoneNumbers": [
    "+1-555-1234",
    "+1-555-5678"
  ],
  "skills": ["Java", "Spring Boot", "JavaFX"]
}
```

## Project Structure
```
json-formatter/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── jsonformatter/
│                   ├── JsonFormatterApplication.java  # Spring Boot main class
│                   ├── JavaFxApplication.java         # JavaFX application
│                   ├── controller/
│                   │   └── MainController.java        # UI controller
│                   └── service/
│                       └── JsonParserService.java     # JSON parsing service
├── pom.xml                                            # Maven configuration
└── README.md                                          # This file
```

## Dependencies
- Spring Boot Starter 2.7.14
- Jackson Databind (for JSON processing)
- JavaFX Controls 17.0.2
- JavaFX FXML 17.0.2

## Error Handling
- Invalid JSON syntax triggers an alert dialog with specific error details
- Status bar at the bottom shows current operation status and errors
- Error messages are displayed in red in the status bar

## License
This project is open source and available under the MIT License.