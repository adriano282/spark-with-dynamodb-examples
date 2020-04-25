package br.com.adriano.swde.infrastructure.dynamo.ddl.create.builder;

import br.com.adriano.swde.infrastructure.dynamo.model.amazon.AmazonTableStructure;
import br.com.adriano.swde.infrastructure.dynamo.model.local.TableStructure;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class AmazonCreateTableRequestBuilder {
    public static CreateTableRequest build(
            TableStructure tableStructure,
            AmazonTableStructure amazonTableStructure,
            ProvisionedThroughput provisionedthroughput) {

        return new CreateTableRequest()
                .withTableName(tableStructure.tableName)
                .withAttributeDefinitions(amazonTableStructure.attributeDefinitions)
                .withKeySchema(amazonTableStructure.keySchemaElements)
                .withProvisionedThroughput(provisionedthroughput);
    }
}
