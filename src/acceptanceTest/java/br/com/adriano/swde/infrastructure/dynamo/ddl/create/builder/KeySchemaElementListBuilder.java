package br.com.adriano.swde.infrastructure.dynamo.ddl.create.builder;

import br.com.adriano.swde.infrastructure.dynamo.model.local.TableStructure;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import java.util.ArrayList;
import java.util.List;

public class KeySchemaElementListBuilder {
    public static List<KeySchemaElement> build(TableStructure tableStructure) {

        List<KeySchemaElement> keySchemaElements = new ArrayList<>();
        keySchemaElements.add(new KeySchemaElement(tableStructure.hashKeyName.name, KeyType.HASH));

        if (tableStructure.hasSortKey()) {
            keySchemaElements.add(
                    new KeySchemaElement(tableStructure.sortKeyName.name, KeyType.RANGE));
        }

        return keySchemaElements;
    }
}
