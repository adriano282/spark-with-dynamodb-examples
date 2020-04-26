package br.com.adriano.swde.infrastructure.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
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

    public AmazonDynamoDB getOrCreate() throws Exception {

        if (dynamoDBEmbedded == null) {


            dynamoDBEmbedded = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                    .build();


            LOGGER.info("m=getOrCreate, message=Created new DynamoDB instance.");
        }

        LOGGER.info("m=getOrCreate, message=Returned a dynamoDB instance...");

        return dynamoDBEmbedded;
    }

    public void shutdown() throws Exception {
        dynamoDBEmbedded.shutdown();

        LOGGER.info("m=shutdown, message=DynamoDb were just shutdown.");
    }
}
