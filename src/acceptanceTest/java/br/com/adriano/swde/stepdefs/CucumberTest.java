package br.com.adriano.swde.stepdefs;

import br.com.adriano.swde.infrastructure.dynamo.DynamoDBInstance;
import br.com.adriano.swde.infrastructure.dynamo.ddl.create.CreateDynamoTableService;
import br.com.adriano.swde.model.SharePeakPriceMonth;
import br.com.adriano.swde.model.StockShare;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/acceptanceTest/resources/feature")
public class CucumberTest {

    private static DynamoDBProxyServer server;

    private static Log LOGGER = LogFactory.getLog(CucumberTest.class);

    private static Class[] classes = {StockShare.class, SharePeakPriceMonth.class};

    @BeforeClass
    public static void setup() throws Exception {
        LOGGER.info("m=setup, message=Starting @BeforeClass annotated method.");

        System.setProperty("sqlite4java.library.path", "src/acceptanceTest/resources/libs/");

        server = ServerRunner.createServerFromCommandLineArgs(
                new String[] {"-inMemory", "-port", "8000", "-sharedDb", "true"}
        );

        server.start();

        CreateDynamoTableService createDynamoTableService =
                new CreateDynamoTableService(DynamoDBInstance.instance().getOrCreate());

        createDynamoTableService.createAllTables(classes);

        LOGGER.info("m=setup, message=Ending @BeforeClass annotated method.");
    }

    @AfterClass
    public static void cleanup() throws Exception {
        LOGGER.info("m=cleanup, message=Starting @AfterClass annotated method.");

        DynamoDBInstance.instance().shutdown();

        server.stop();

        LOGGER.info("m=cleanup, message=Ending @AfterClass annotated method.");
    }
}
