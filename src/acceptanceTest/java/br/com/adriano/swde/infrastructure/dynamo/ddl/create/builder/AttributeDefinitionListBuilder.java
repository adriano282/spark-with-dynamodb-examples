package br.com.adriano.swde.infrastructure.dynamo.ddl.create.builder;

import br.com.adriano.swde.infrastructure.dynamo.model.local.TableStructure;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import java.util.ArrayList;
import java.util.List;

public class AttributeDefinitionListBuilder {

    public static List<AttributeDefinition> build(TableStructure tableStructure) {

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(
                new AttributeDefinition(
                        tableStructure.hashKeyName.name,
                        tableStructure.hashKeyName.columnType.convertToAmazonType()));

        if (tableStructure.hasSortKey()) {
            attributeDefinitions.add(
                    new AttributeDefinition(
                            tableStructure.sortKeyName.name,
                            tableStructure.sortKeyName.columnType.convertToAmazonType()));
        }

        return attributeDefinitions;
    }
}
