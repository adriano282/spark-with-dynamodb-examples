package br.com.adriano.swde.infrastructure.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DynamoDBInstance {

    private static Logger LOGGER = LogManager.getLogger(DynamoDBInstance.class);

    private DynamoDBInstance() {}

    private static DynamoDBInstance instance = new DynamoDBInstance();

    private AmazonDynamoDB dynamoDBEmbedded;

    public static DynamoDBInstance instance() {
        return instance;
    }

    public AmazonDynamoDB getOrCreate() {
        if (dynamoDBEmbedded == null) {

            dynamoDBEmbedded = DynamoDBEmbedded.create().amazonDynamoDB();

            LOGGER.info("m=getOrCreate, message=Created new DynamoDB instance.");
        }


        return dynamoDBEmbedded;
    }

    public void shutdown() {
        dynamoDBEmbedded.shutdown();

        LOGGER.info("m=shutdown, message=DynamoDb were just shutdown.");
    }
}
