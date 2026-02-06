package com.jsonformatter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonParserService.
 */
class JsonParserServiceTest {

    private JsonParserService jsonParserService;

    @BeforeEach
    void setUp() {
        jsonParserService = new JsonParserService();
    }

    @Test
    void testParseValidJsonObject() throws JsonProcessingException {
        String json = "{\"name\":\"John\",\"age\":30}";
        JsonNode jsonNode = jsonParserService.parseJson(json);
        
        assertNotNull(jsonNode);
        assertTrue(jsonNode.isObject());
        assertEquals("John", jsonNode.get("name").asText());
        assertEquals(30, jsonNode.get("age").asInt());
    }

    @Test
    void testParseValidJsonArray() throws JsonProcessingException {
        String json = "[1,2,3,4,5]";
        JsonNode jsonNode = jsonParserService.parseJson(json);
        
        assertNotNull(jsonNode);
        assertTrue(jsonNode.isArray());
        assertEquals(5, jsonNode.size());
        assertEquals(1, jsonNode.get(0).asInt());
    }

    @Test
    void testParseInvalidJson() {
        String invalidJson = "{invalid json}";
        
        assertThrows(JsonProcessingException.class, () -> {
            jsonParserService.parseJson(invalidJson);
        });
    }

    @Test
    void testFormatJson() throws JsonProcessingException {
        String compactJson = "{\"name\":\"John\",\"age\":30}";
        String formattedJson = jsonParserService.formatJson(compactJson);
        
        assertNotNull(formattedJson);
        assertTrue(formattedJson.contains("\n"));
        assertTrue(formattedJson.contains("  "));
    }

    @Test
    void testBuildTreeFromSimpleObject() throws JsonProcessingException {
        String json = "{\"name\":\"John\",\"age\":30}";
        JsonNode jsonNode = jsonParserService.parseJson(json);
        
        TreeItem<String> treeItem = jsonParserService.buildTreeFromJson(jsonNode);
        
        assertNotNull(treeItem);
        assertEquals("root { }", treeItem.getValue());
        assertEquals(2, treeItem.getChildren().size());
    }

    @Test
    void testBuildTreeFromArray() throws JsonProcessingException {
        String json = "[1,2,3]";
        JsonNode jsonNode = jsonParserService.parseJson(json);
        
        TreeItem<String> treeItem = jsonParserService.buildTreeFromJson(jsonNode);
        
        assertNotNull(treeItem);
        assertEquals("root [ ]", treeItem.getValue());
        assertEquals(3, treeItem.getChildren().size());
        assertEquals("[0]: 1", treeItem.getChildren().get(0).getValue());
        assertEquals("[1]: 2", treeItem.getChildren().get(1).getValue());
        assertEquals("[2]: 3", treeItem.getChildren().get(2).getValue());
    }

    @Test
    void testBuildTreeFromNestedObject() throws JsonProcessingException {
        String json = "{\"person\":{\"name\":\"John\",\"age\":30}}";
        JsonNode jsonNode = jsonParserService.parseJson(json);
        
        TreeItem<String> treeItem = jsonParserService.buildTreeFromJson(jsonNode);
        
        assertNotNull(treeItem);
        assertEquals("root { }", treeItem.getValue());
        assertEquals(1, treeItem.getChildren().size());
        
        TreeItem<String> personItem = treeItem.getChildren().get(0);
        assertEquals("person { }", personItem.getValue());
        assertEquals(2, personItem.getChildren().size());
    }

    @Test
    void testBuildTreeFromPrimitiveTypes() throws JsonProcessingException {
        String json = "{\"string\":\"hello\",\"number\":42,\"boolean\":true,\"nullValue\":null}";
        JsonNode jsonNode = jsonParserService.parseJson(json);
        
        TreeItem<String> treeItem = jsonParserService.buildTreeFromJson(jsonNode);
        
        assertNotNull(treeItem);
        assertEquals(4, treeItem.getChildren().size());
        
        // Check each primitive type
        boolean foundString = false;
        boolean foundNumber = false;
        boolean foundBoolean = false;
        boolean foundNull = false;
        
        for (TreeItem<String> child : treeItem.getChildren()) {
            String value = child.getValue();
            if (value.contains("string: \"hello\"")) foundString = true;
            if (value.contains("number: 42")) foundNumber = true;
            if (value.contains("boolean: true")) foundBoolean = true;
            if (value.contains("nullValue: null")) foundNull = true;
        }
        
        assertTrue(foundString, "String value not found");
        assertTrue(foundNumber, "Number value not found");
        assertTrue(foundBoolean, "Boolean value not found");
        assertTrue(foundNull, "Null value not found");
    }

    @Test
    void testBuildTreeFromComplexStructure() throws JsonProcessingException {
        String json = "{\"users\":[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"age\":25}]}";
        JsonNode jsonNode = jsonParserService.parseJson(json);
        
        TreeItem<String> treeItem = jsonParserService.buildTreeFromJson(jsonNode);
        
        assertNotNull(treeItem);
        assertEquals("root { }", treeItem.getValue());
        assertEquals(1, treeItem.getChildren().size());
        
        TreeItem<String> usersItem = treeItem.getChildren().get(0);
        assertEquals("users [ ]", usersItem.getValue());
        assertEquals(2, usersItem.getChildren().size());
        
        TreeItem<String> firstUser = usersItem.getChildren().get(0);
        assertEquals("[0] { }", firstUser.getValue());
        assertEquals(2, firstUser.getChildren().size());
    }
}
