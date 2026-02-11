# Bug Fix: JavaFX Toolkit Initialization Exception

## Issue Description
When running the application, users encountered the following exception:
```
java.lang.IllegalStateException: Toolkit not initialized
	at com.sun.javafx.application.PlatformImpl.runLater(PlatformImpl.java:437)
	...
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.jsonformatter.controller.MainController]
```

## Root Cause
The issue occurred because Spring Boot was trying to create the `MainController` bean during application context initialization, before JavaFX's Application.launch() was called. This caused JavaFX UI components to be instantiated before the JavaFX toolkit was initialized, resulting in an IllegalStateException.

**Sequence of events that caused the issue:**
1. Spring Boot application starts
2. Spring Boot scans for @Component beans and finds MainController
3. Spring tries to instantiate MainController bean
4. MainController constructor calls initializeUI()
5. initializeUI() creates JavaFX components (BorderPane, TextArea, TreeView, etc.)
6. JavaFX toolkit is not yet initialized → Exception thrown
7. Application fails to start

## Solution
Changed the architecture to defer MainController creation until after JavaFX toolkit initialization:

### Changes Made

**1. MainController.java**
- Removed `@Component` annotation (no longer a Spring bean)
- Removed `@Autowired` annotation from constructor
- Made constructor public
- Added comment explaining why it's not a Spring component

**2. JavaFxApplication.java**
- Modified `start()` method to get JsonParserService from Spring context
- Create MainController manually after JavaFX toolkit is initialized
- Pass JsonParserService to MainController via constructor

**Correct sequence after fix:**
1. Spring Boot application starts
2. Spring Boot context initialization completes (no MainController bean)
3. Application.launch(JavaFxApplication.class) is called
4. JavaFX toolkit initializes
5. JavaFxApplication.start() is called
6. MainController is created manually (toolkit is now initialized)
7. JavaFX UI components are created successfully
8. Application runs correctly

## Benefits
- ✅ Fixes the toolkit initialization exception
- ✅ Maintains Spring dependency injection for services
- ✅ Follows proper JavaFX application lifecycle
- ✅ All existing tests continue to pass
- ✅ No changes to application functionality

## Testing
- Application starts successfully without exceptions
- All 9 unit tests pass
- Spring Boot context loads correctly
- JavaFX application launches properly (on systems with display)

## Technical Details
JavaFX requires its toolkit to be initialized before any UI components can be created. The initialization happens when `Application.launch()` is called. Creating UI components before this initialization throws `IllegalStateException: Toolkit not initialized`.

The fix ensures that:
1. Spring-managed services (like JsonParserService) are still managed by Spring
2. UI controller (MainController) is created after toolkit initialization
3. Services can be injected into the controller via manual dependency injection
