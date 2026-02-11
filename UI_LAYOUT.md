# Application UI Layout

```
┌─────────────────────────────────────────────────────────────────────────┐
│ JSON Formatter                                                      [_][□][X]│
├─────────────────────────────────────────────────────────────────────────┤
│ [Format JSON]  [Clear]                                                  │
├──────────────────────────────┬──────────────────────────────────────────┤
│                              │                                          │
│ JSON Input:                  │ Formatted JSON Tree:                     │
│ ┌──────────────────────────┐ │ ┌──────────────────────────────────────┐ │
│ │ {                        │ │ │ ▼ root { }                           │ │
│ │   "name": "John Doe",    │ │ │   ▼ name: "John Doe"                 │ │
│ │   "age": 30,             │ │ │   ▼ age: 30                          │ │
│ │   "isEmployed": true,    │ │ │   ▼ isEmployed: true                 │ │
│ │   "address": {           │ │ │   ▼ address { }                      │ │
│ │     "street": "123 Main",│ │ │     ▼ street: "123 Main St"          │ │
│ │     "city": "New York",  │ │ │     ▼ city: "New York"               │ │
│ │     "zipCode": "10001"   │ │ │     ▼ zipCode: "10001"               │ │
│ │   },                     │ │ │   ▼ phoneNumbers [ ]                 │ │
│ │   "phoneNumbers": [      │ │ │     ▼ [0]: "+1-555-1234"             │ │
│ │     "+1-555-1234",       │ │ │     ▼ [1]: "+1-555-5678"             │ │
│ │     "+1-555-5678"        │ │ │   ▼ skills [ ]                       │ │
│ │   ],                     │ │ │     ▼ [0]: "Java"                    │ │
│ │   "skills": [            │ │ │     ▼ [1]: "Spring Boot"             │ │
│ │     "Java",              │ │ │     ▼ [2]: "JavaFX"                  │ │
│ │     "Spring Boot",       │ │ │                                      │ │
│ │     "JavaFX"             │ │ │                                      │ │
│ │   ]                      │ │ │                                      │ │
│ │ }                        │ │ │                                      │ │
│ │                          │ │ │                                      │ │
│ │                          │ │ │                                      │ │
│ │                          │ │ │                                      │ │
│ └──────────────────────────┘ │ └──────────────────────────────────────┘ │
│                              │                                          │
├──────────────────────────────┴──────────────────────────────────────────┤
│ Status: JSON formatted successfully                                     │
└─────────────────────────────────────────────────────────────────────────┘

Features:
- Left Panel: Editable text area with monospaced font for JSON input
- Right Panel: Tree view with expandable/collapsible nodes
- Format Button: Parses and displays JSON structure
- Clear Button: Resets both panels
- Status Bar: Shows operation status and errors
- Objects shown with { } notation
- Arrays shown with [ ] notation
- Primitive values (strings, numbers, booleans, null) as leaf nodes
```

## Key UI Elements

### Left Section (JSON Input)
- **Type**: TextArea
- **Font**: Courier New (monospaced)
- **Features**:
  - Paste or type JSON
  - Editable
  - Scrollable
  - Syntax-friendly font

### Right Section (TreeView)
- **Type**: TreeView
- **Font**: Courier New (monospaced)
- **Features**:
  - Collapsible/expandable nodes
  - Objects displayed with { }
  - Arrays displayed with [ ]
  - Key-value pairs for objects
  - Indexed elements for arrays
  - Leaf nodes for primitive values

### Top Panel (Actions)
- **Format JSON Button**: Triggers parsing and display
- **Clear Button**: Resets both panels

### Bottom Panel (Status)
- **Status Label**: Shows success (green) or error (red) messages
- **Error Details**: Displays JSON parsing errors

## User Workflow

1. User pastes or types JSON in the left panel
2. User clicks "Format JSON" button
3. Application parses the JSON using Jackson
4. If valid:
   - Formatted JSON displayed in left panel
   - Tree structure displayed in right panel
   - Success message in status bar
5. If invalid:
   - Error alert dialog appears
   - Error message in status bar (red)
6. User can expand/collapse tree nodes to explore structure
7. User can click "Clear" to reset and start over
