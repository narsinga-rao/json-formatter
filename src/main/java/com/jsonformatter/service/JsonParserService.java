package com.jsonformatter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

/**
 * Service for parsing JSON and converting it to JavaFX TreeView structure.
 */
@Service
public class JsonParserService {

    private final ObjectMapper objectMapper;

    public JsonParserService() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Parses JSON string and validates its structure.
     *
     * @param jsonString the JSON string to parse
     * @return JsonNode representing the parsed JSON
     * @throws JsonProcessingException if the JSON is invalid
     */
    public JsonNode parseJson(String jsonString) throws JsonProcessingException {
        return objectMapper.readTree(jsonString);
    }

    /**
     * Formats a JSON string with proper indentation.
     *
     * @param jsonString the JSON string to format
     * @return formatted JSON string
     * @throws JsonProcessingException if the JSON is invalid
     */
    public String formatJson(String jsonString) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }

    /**
     * Converts JsonNode to TreeItem structure for JavaFX TreeView.
     *
     * @param jsonNode the JsonNode to convert
     * @return TreeItem root for the TreeView
     */
    public TreeItem<String> buildTreeFromJson(JsonNode jsonNode) {
        return buildTreeFromJson(jsonNode, "root");
    }

    /**
     * Recursively builds a TreeItem structure from JsonNode.
     *
     * @param jsonNode the JsonNode to convert
     * @param name     the name/key for this node
     * @return TreeItem representing this node and its children
     */
    private TreeItem<String> buildTreeFromJson(JsonNode jsonNode, String name) {
        TreeItem<String> treeItem;

        if (jsonNode.isObject()) {
            // Object node - display key-value pairs within { }
            ObjectNode objectNode = (ObjectNode) jsonNode;
            int fieldCount = objectNode.size();
            String expandedLabel = name;
            String collapsedLabel = name + ": { " + fieldCount + " props }";
            treeItem = new TreeItem<>(expandedLabel);
            treeItem.setExpanded(true);

            // Toggle label on expand/collapse
            treeItem.expandedProperty().addListener((obs, wasExpanded, isExpanded) -> {
                treeItem.setValue(isExpanded ? expandedLabel : collapsedLabel);
            });

            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                JsonNode value = field.getValue();
                TreeItem<String> childItem = buildTreeFromJson(value, key);
                treeItem.getChildren().add(childItem);
            }
        } else if (jsonNode.isArray()) {
            // Array node - display within [ ]
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            int elementCount = arrayNode.size();
            String expandedLabel = name;
            String collapsedLabel = name + ": [ " + elementCount + " items ]";
            treeItem = new TreeItem<>(expandedLabel);
            treeItem.setExpanded(true);

            // Toggle label on expand/collapse
            treeItem.expandedProperty().addListener((obs, wasExpanded, isExpanded) -> {
                treeItem.setValue(isExpanded ? expandedLabel : collapsedLabel);
            });

            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode element = arrayNode.get(i);
                TreeItem<String> childItem = buildTreeFromJson(element, "[" + i + "]");
                treeItem.getChildren().add(childItem);
            }
        } else if (jsonNode.isNull()) {
            // Null value
            treeItem = new TreeItem<>(name + ": null");
        } else if (jsonNode.isBoolean()) {
            // Boolean value
            treeItem = new TreeItem<>(name + ": " + jsonNode.asBoolean());
        } else if (jsonNode.isNumber()) {
            // Number value
            treeItem = new TreeItem<>(name + ": " + jsonNode.asText());
        } else {
            // String value
            treeItem = new TreeItem<>(name + ": \"" + jsonNode.asText() + "\"");
        }

        return treeItem;
    }
}
