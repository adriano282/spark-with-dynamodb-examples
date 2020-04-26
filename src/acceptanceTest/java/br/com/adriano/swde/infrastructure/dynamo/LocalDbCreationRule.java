package br.com.adriano.swde.infrastructure.dynamo;

import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;

public class LocalDbCreationRule extends ExternalResource {

    private DynamoDBProxyServer server;

    public LocalDbCreationRule() {
        System.setProperty("sqlite4java.library.path", "src/acceptanceTest/resources/libs/");
    }
}
