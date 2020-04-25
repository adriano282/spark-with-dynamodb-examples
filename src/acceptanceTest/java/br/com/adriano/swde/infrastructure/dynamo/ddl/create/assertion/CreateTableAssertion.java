package br.com.adriano.swde.infrastructure.dynamo.ddl.create.assertion;

import static org.junit.Assert.assertEquals;

import br.com.adriano.swde.infrastructure.dynamo.model.local.TableStructure;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class CreateTableAssertion {

    private AmazonDynamoDB dynamoDB;
    private ProvisionedThroughput provisionedThroughput;

    public CreateTableAssertion(
            AmazonDynamoDB dynamoDB, ProvisionedThroughput provisionedThroughput) {
        this.dynamoDB = dynamoDB;
        this.provisionedThroughput = provisionedThroughput;
    }

    public void assertTableCreation(TableStructure tableStructure, TableDescription tableDesc) {
        assertTableName(tableStructure, tableDesc);

        assertKeySchema(tableStructure, tableDesc);

        assertAttributeDefinitions(tableStructure, tableDesc);

        assertProvisionedThroughput(tableDesc);

        assertStatusIsActive(tableDesc);

        assertAmazonResourceName(tableStructure, tableDesc);

        assertNumberTables();
    }

    private void assertNumberTables() {
        ListTablesResult tables = dynamoDB.listTables();
        assertEquals("The number of tables isn't as excpeected.", 1, tables.getTableNames().size());
    }

    private void assertAmazonResourceName(
            TableStructure tableStructure, TableDescription tableDesc) {
        assertEquals(
                "arn:aws:dynamodb:ddblocal:000000000000:table/"
                        + tableStructure.tableName.toLowerCase(),
                tableDesc.getTableArn());
    }

    private void assertStatusIsActive(TableDescription tableDesc) {
        assertEquals("ACTIVE", tableDesc.getTableStatus());
    }

    private void assertProvisionedThroughput(TableDescription tableDesc) {
        assertEquals(
                "The read provisioned throughput isn't as expected.",
                Long.valueOf(provisionedThroughput.getReadCapacityUnits()),
                tableDesc.getProvisionedThroughput().getReadCapacityUnits());
        assertEquals(
                "The write provisioned throughput isn't as expected.",
                Long.valueOf(provisionedThroughput.getWriteCapacityUnits()),
                tableDesc.getProvisionedThroughput().getWriteCapacityUnits());
    }

    private void assertAttributeDefinitions(
            TableStructure tableStructure, TableDescription tableDesc) {
        assertEquals(
                "The attribute definitions isn't as expected.",
                "[{AttributeName: "
                        + tableStructure.hashKeyName
                        + ",AttributeType: "
                        + tableStructure.hashKeyName.columnType.convertToAmazonType()
                        + "}]",
                tableDesc.getAttributeDefinitions().toString());

        if (tableStructure.hasSortKey()) {
            assertEquals(
                    "The attribute definitions isn't as expected.",
                    "[{AttributeName: "
                            + tableStructure.sortKeyName
                            + ",AttributeType: "
                            + tableStructure.sortKeyName.columnType.convertToAmazonType()
                            + "}]",
                    tableDesc.getAttributeDefinitions().toString());
        }
    }

    private void assertKeySchema(TableStructure tableStructure, TableDescription tableDesc) {

        assertEquals(
                "The key schema isn't as expected.",
                "[{AttributeName: " + tableStructure.hashKeyName + ",KeyType: HASH}]",
                tableDesc.getKeySchema().toString());

        if (tableStructure.hasSortKey()) {
            assertEquals(
                    "The key schema isn't as expected.",
                    "[{AttributeName: " + tableStructure.sortKeyName + ",KeyType: HASH}]",
                    tableDesc.getKeySchema().toString());
        }
    }

    private void assertTableName(TableStructure tableStructure, TableDescription tableDesc) {
        assertEquals(
                "The table name isn't as expected",
                tableStructure.tableName,
                tableDesc.getTableName());
    }
}
