# Run Script Fix: Auto-Rebuild to Prevent Stale JAR Issues

## Problem

Users were reporting "Toolkit not initialized" errors when running the application using `run.sh` or `run.bat`, even after the toolkit initialization issue was fixed in the code.

## Root Cause

The original run scripts had a conditional build check:

```bash
# OLD VERSION - PROBLEMATIC
if [ ! -f "target/json-formatter-1.0.0.jar" ]; then
    echo "JAR file not found. Building the application..."
    mvn clean package -DskipTests
fi
```

**Problem**: This only built the application if the JAR didn't exist. If users had an old JAR file from before the fix was applied, the script would NOT rebuild it, and they would continue to get the "Toolkit not initialized" error.

## Solution

Updated both `run.sh` and `run.bat` to **always rebuild** the application by default:

```bash
# NEW VERSION - FIXED
echo "🔨 Building application (this ensures you have the latest code)..."
mvn clean package -DskipTests
```

### Added --skip-build Flag

For users who haven't made code changes and want faster startup:

```bash
./run.sh --skip-build     # Linux/Mac
run.bat --skip-build      # Windows
```

## Benefits

1. **No Stale JARs**: Users always get the latest compiled code
2. **Automatic Clean Build**: Uses `mvn clean package` to remove old classes
3. **User-Friendly**: Clear messages and progress indicators
4. **Optional Skip**: Advanced users can skip rebuild when needed
5. **Consistency**: Both Unix and Windows scripts work the same way

## Usage

### Default Behavior (Recommended)
```bash
# Always rebuilds to ensure latest code
./run.sh
```

### Skip Build (Advanced)
```bash
# Skips rebuild, uses existing JAR
./run.sh --skip-build
```

**Note**: The --skip-build option will fail with an error if no JAR exists.

## Why This Fixes the "Toolkit Not Initialized" Error

The toolkit initialization issue was already fixed in the code by:
1. Removing @Component from MainController
2. Creating MainController manually after JavaFX toolkit initialization
3. Adding explicit component scan exclusion

However, users with old JAR files compiled before these fixes would still get the error. By forcing a rebuild every time the run script is executed, we ensure users always have the latest fixed code.

## Script Features

### Enhanced User Experience

**Before:**
```
Starting JSON Formatter application...
Launching application...
```

**After:**
```
════════════════════════════════════════════════════════
  JSON Formatter - Building and Running Application
════════════════════════════════════════════════════════

🔨 Building application (this ensures you have the latest code)...
   Running: mvn clean package -DskipTests

✅ Build successful!

🚀 Launching JSON Formatter application...
════════════════════════════════════════════════════════
```

### Error Handling

- Build failures are caught and reported
- Script exits with error code if build fails
- --skip-build validates JAR exists before trying to run

## Testing

Both scripts have been tested and verified to:
- ✅ Build successfully on first run
- ✅ Rebuild on subsequent runs
- ✅ Skip build when --skip-build is specified
- ✅ Fail gracefully when JAR is missing in --skip-build mode
- ✅ Run the application without "Toolkit not initialized" error

## Impact

This change ensures that users running the application via the run scripts will:
1. Always get a fresh build
2. Never encounter stale JAR issues
3. See clear progress and status messages
4. Have the toolkit initialization fix applied automatically

No more "Toolkit not initialized" errors due to stale JARs! 🎉
