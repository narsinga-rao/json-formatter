package com.jsonformatter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.jsonformatter.service.JsonParserService;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Main controller for the JSON Formatter UI.
 */
@Component
public class MainController {

    private final JsonParserService jsonParserService;
    private BorderPane rootPane;
    private TextArea jsonInputArea;
    private TreeView<String> jsonTreeView;
    private Label statusLabel;

    @Autowired
    public MainController(JsonParserService jsonParserService) {
        this.jsonParserService = jsonParserService;
        initializeUI();
    }

    /**
     * Initializes the JavaFX UI components.
     */
    private void initializeUI() {
        rootPane = new BorderPane();
        rootPane.setPadding(new Insets(10));

        // Create the main split pane
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(0.5);

        // Left panel - JSON input
        VBox leftPanel = createLeftPanel();
        
        // Right panel - Tree view
        VBox rightPanel = createRightPanel();

        splitPane.getItems().addAll(leftPanel, rightPanel);
        
        // Top panel with buttons
        HBox topPanel = createTopPanel();
        
        // Bottom panel with status
        HBox bottomPanel = createBottomPanel();

        rootPane.setTop(topPanel);
        rootPane.setCenter(splitPane);
        rootPane.setBottom(bottomPanel);
    }

    /**
     * Creates the top panel with action buttons.
     */
    private HBox createTopPanel() {
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(0, 0, 10, 0));

        Button formatButton = new Button("Format JSON");
        formatButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 16;");
        formatButton.setOnAction(e -> formatJson());

        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 16;");
        clearButton.setOnAction(e -> clearAll());

        topPanel.getChildren().addAll(formatButton, clearButton);
        return topPanel;
    }

    /**
     * Creates the bottom panel with status label.
     */
    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox();
        bottomPanel.setPadding(new Insets(10, 0, 0, 0));

        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-text-fill: #666666;");
        
        bottomPanel.getChildren().add(statusLabel);
        return bottomPanel;
    }

    /**
     * Creates the left panel with JSON input area.
     */
    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(5);
        
        Label inputLabel = new Label("JSON Input:");
        inputLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        jsonInputArea = new TextArea();
        jsonInputArea.setPromptText("Paste or type JSON here...");
        jsonInputArea.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-size: 13px;");
        jsonInputArea.setWrapText(true);
        
        VBox.setVgrow(jsonInputArea, Priority.ALWAYS);

        leftPanel.getChildren().addAll(inputLabel, jsonInputArea);
        return leftPanel;
    }

    /**
     * Creates the right panel with tree view for formatted JSON.
     */
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(5);
        
        Label treeLabel = new Label("Formatted JSON Tree:");
        treeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        jsonTreeView = new TreeView<>();
        jsonTreeView.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-size: 13px;");
        jsonTreeView.setShowRoot(true);
        
        VBox.setVgrow(jsonTreeView, Priority.ALWAYS);

        rightPanel.getChildren().addAll(treeLabel, jsonTreeView);
        return rightPanel;
    }

    /**
     * Formats and displays the JSON from the input area.
     */
    private void formatJson() {
        String jsonInput = jsonInputArea.getText().trim();
        
        if (jsonInput.isEmpty()) {
            showError("Please enter JSON content to format.");
            return;
        }

        try {
            // Parse the JSON
            JsonNode jsonNode = jsonParserService.parseJson(jsonInput);
            
            // Format and display in the input area
            String formattedJson = jsonParserService.formatJson(jsonInput);
            jsonInputArea.setText(formattedJson);
            
            // Build and display the tree view
            TreeItem<String> rootItem = jsonParserService.buildTreeFromJson(jsonNode);
            jsonTreeView.setRoot(rootItem);
            
            statusLabel.setText("JSON formatted successfully");
            statusLabel.setStyle("-fx-text-fill: green;");
            
        } catch (JsonProcessingException e) {
            showError("Invalid JSON: " + e.getOriginalMessage());
        } catch (Exception e) {
            showError("Error processing JSON: " + e.getMessage());
        }
    }

    /**
     * Clears all content from the UI.
     */
    private void clearAll() {
        jsonInputArea.clear();
        jsonTreeView.setRoot(null);
        statusLabel.setText("Ready");
        statusLabel.setStyle("-fx-text-fill: #666666;");
    }

    /**
     * Shows an error message to the user.
     */
    private void showError(String message) {
        statusLabel.setText("Error: " + message);
        statusLabel.setStyle("-fx-text-fill: red;");
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("JSON Processing Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Gets the root view for this controller.
     */
    public BorderPane getView() {
        return rootPane;
    }
}
