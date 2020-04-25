package br.com.adriano.swde.infrastructure.dynamo.model.amazon;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import java.util.List;

public class AmazonTableStructure {
    public final List<AttributeDefinition> attributeDefinitions;
    public final List<KeySchemaElement> keySchemaElements;

    public AmazonTableStructure(
            List<AttributeDefinition> attributeDefinitions,
            List<KeySchemaElement> keySchemaElements) {
        this.attributeDefinitions = attributeDefinitions;
        this.keySchemaElements = keySchemaElements;
    }
}
