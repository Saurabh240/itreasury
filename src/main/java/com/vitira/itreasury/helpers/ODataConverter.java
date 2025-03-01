package com.vitira.itreasury.helpers;

import java.util.List;
import java.util.ArrayList;

import org.apache.olingo.client.api.domain.ClientComplexValue;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientEntitySetIterator;
import org.apache.olingo.client.api.domain.ClientProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ODataConverter<T> {

    private ObjectMapper objectMapper;
	
	
    private final Class<T> clazz;

    public ODataConverter(Class<T> clazz) {
        this.clazz = clazz;
        
        // TODO: Currently we are facing issues to @AutoWired ObjectMapper from JacksonConfig class(Global init)
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public T clientEntityToPojo(ClientEntity clientEntity) throws Exception {
    	
    	// Step 1: Client Entity to JSON Object node
    	ObjectNode jsonObjNode = clientEntityToObjectNode(clientEntity);
        
        // Step 2: Serialize POJO to JSON string
        String jsonString = objectMapper.writeValueAsString(jsonObjNode);

        // Step 3: Deserialize JSON string to POJO
        return objectMapper.readValue(jsonString, clazz);
    }
    
    public String clientEntityToJsonString(ClientEntity clientEntity) throws Exception {
    	
    	ObjectNode jsonObjNode = clientEntityToObjectNode(clientEntity);
        return objectMapper.writeValueAsString(jsonObjNode);
        
    }
    
	public String clientEntitySetIteratorToJson(ClientEntitySetIterator<ClientEntitySet, ClientEntity> iterator) throws Exception {
        List<ObjectNode> entities = new ArrayList<>();

        while (iterator.hasNext()) {
            ClientEntity clientEntity = iterator.next();
            // Create a JSON node for the entity
            ObjectNode entityNode = clientEntityToObjectNode(clientEntity);

            entities.add(entityNode);
        }

        // Convert the list of JSON nodes to a JSON string
        return objectMapper.writeValueAsString(entities);
    }
    
    
    private ObjectNode clientEntityToObjectNode(ClientEntity clientEntity) {
    	// Create a JSON node for the entity
        ObjectNode entityNode = objectMapper.createObjectNode();

        // Extract properties from the ClientEntity and add to the JSON node
        for (ClientProperty property : clientEntity.getProperties()) {
            Object value = getPropertyValue(property);
            if (value instanceof String) {
                entityNode.put(property.getName(), (String) value);
            } else if (value instanceof Integer) {
                entityNode.put(property.getName(), (Integer) value);
            } else if (value instanceof Double) {
                entityNode.put(property.getName(), (Double) value);
            } else if (value instanceof ObjectNode) {
                entityNode.set(property.getName(), (ObjectNode) value);
            }
        }
        
        return entityNode;
    }
    
    private Object getPropertyValue(ClientProperty property) {
        if (property.hasPrimitiveValue()) {
            return property.getPrimitiveValue().toValue();
        } else if (property.hasComplexValue()) {
            return getComplexValueAsJson(property.getComplexValue());
        }
        return null;
    }

    private ObjectNode getComplexValueAsJson(ClientComplexValue complexValue) {
        ObjectNode node = objectMapper.createObjectNode();
        for (ClientProperty property : complexValue) {
            Object value = getPropertyValue(property);
            if (value instanceof String) {
                node.put(property.getName(), (String) value);
            } else if (value instanceof Double) {
                node.put(property.getName(), (Double) value);
            } else if (value instanceof ObjectNode) {
                node.set(property.getName(), (ObjectNode) value);
            }
        }
        return node;
    }
}
