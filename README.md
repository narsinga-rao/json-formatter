# JSON Formatter

A desktop application to format JSON payloads and display them as collapsible and expandable nodes.

> **⚠️ IMPORTANT:** If you're getting "Toolkit not initialized" error, you must rebuild the application!
> See [REBUILD_INSTRUCTIONS.md](REBUILD_INSTRUCTIONS.md) for details.

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
# IMPORTANT: Always use 'clean' to remove old compiled classes
mvn clean package

# This will create a JAR file in the target/ directory
```

> **⚠️ Why 'clean' is important:** If you previously built an older version of the code,
> old compiled classes may remain in the `target/` directory. Always use `mvn clean package`
> to ensure a fresh build, especially after pulling new changes.
```

### Running the Application

#### Quick Start (Recommended)
The run scripts now automatically rebuild the application to ensure you always have the latest code:

```bash
# On Linux/Mac
./run.sh

# On Windows
run.bat
```

> **✨ New Feature**: The run scripts now **always rebuild** the application by default.
> This ensures you're running the latest code and prevents stale JAR issues.
> 
> **Performance Tip**: If you haven't made any code changes and want to skip the rebuild:
> ```bash
> ./run.sh --skip-build     # Linux/Mac
> run.bat --skip-build      # Windows
> ```

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

Try pasting this sample JSON (also available in `example.json`):
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

For more complex examples, see the included `example.json` file.

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
│                   │   └── MainController.java        # UI controller (created after JavaFX init)
│                   └── service/
│                       └── JsonParserService.java     # JSON parsing service (@Service)
├── pom.xml                                            # Maven configuration
└── README.md                                          # This file
```

## Architecture Notes
- **JsonParserService**: Spring-managed service (@Service) for JSON processing
- **MainController**: Regular Java class (not a Spring bean) created after JavaFX toolkit initialization
- **JavaFX Integration**: MainController is instantiated manually in JavaFxApplication.start() to ensure JavaFX toolkit is initialized before creating UI components

## Dependencies
- Spring Boot Starter 2.7.14
- Jackson Databind (for JSON processing)
- JavaFX Controls 17.0.2
- JavaFX FXML 17.0.2

## Error Handling
- Invalid JSON syntax triggers an alert dialog with specific error details
- Status bar at the bottom shows current operation status and errors
- Error messages are displayed in red in the status bar

## Troubleshooting
- If you encounter "Toolkit not initialized" error, ensure you're using the latest version
- The application requires a graphical display to run
- For headless environments, JavaFX will report "Unable to open DISPLAY" (expected behavior)

## License
This project is open source and available under the MIT License.