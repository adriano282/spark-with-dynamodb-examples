package br.com.adriano.swde.stepdefs;

import br.com.adriano.swde.infrastructure.dynamo.DynamoDBInstance;
import br.com.adriano.swde.infrastructure.dynamo.ddl.create.CreateDynamoTableService;
import br.com.adriano.swde.model.SharePeakPriceMonth;
import br.com.adriano.swde.model.StockShare;
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

    private static Log LOGGER = LogFactory.getLog(CucumberTest.class);

    private static Class[] classes = {StockShare.class, SharePeakPriceMonth.class};

    @BeforeClass
    public static void setup() {
        LOGGER.info("m=setup, message=Starting @BeforeClass annotated method.");

        System.setProperty("sqlite4java.library.path", "src/acceptanceTest/resources/libs/");

        CreateDynamoTableService createDynamoTableService =
                new CreateDynamoTableService(DynamoDBInstance.instance().getOrCreate());

        createDynamoTableService.createAllTables(classes);

        LOGGER.info("m=setup, message=Ending @BeforeClass annotated method.");
    }

    @AfterClass
    public static void cleanup() {
        LOGGER.info("m=cleanup, message=Starting @AfterClass annotated method.");

        DynamoDBInstance.instance().shutdown();

        LOGGER.info("m=cleanup, message=Ending @AfterClass annotated method.");
    }
}
