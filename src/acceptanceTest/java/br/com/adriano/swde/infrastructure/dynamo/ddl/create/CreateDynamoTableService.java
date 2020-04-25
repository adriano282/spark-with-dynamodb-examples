package br.com.adriano.swde.infrastructure.dynamo.ddl.create;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CreateDynamoTableService {

    private static Logger LOGGER = LogManager.getLogger(CreateDynamoTableService.class);

    private AmazonDynamoDB dynamoDB;

    public CreateDynamoTableService(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    public void createAllTables(Class ... classes) {
        LOGGER.info("m=createAllTables, message=Received {} classes to create.");

        for (Class aClass : classes) {
            createTable(aClass);
        }
    }

    public void createTable(Class clazz) {

        CreateTableRequest request = createCreateTableRequest(clazz);

        request.setProvisionedThroughput(new ProvisionedThroughput(1000L,1000L));

        if (existsTable(request.getTableName())) {
            LOGGER.info("m=createTable, message=Table {} already created.", request.getTableName());
            return;
        }

        CreateTableResult createTableResult = dynamoDB.createTable(request);

        LOGGER.info("m=createTable, message=Table {} successfully created.", createTableResult.getTableDescription().getTableName());
    }

    private CreateTableRequest createCreateTableRequest(Class clazz) {
        return new DynamoDBMapper(dynamoDB).generateCreateTableRequest(clazz);
    }

    private boolean existsTable(String tableName) {
        return dynamoDB.listTables().getTableNames().contains(tableName);
    }
}
