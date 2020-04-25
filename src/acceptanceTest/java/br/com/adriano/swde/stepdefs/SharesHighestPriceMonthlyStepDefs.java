package br.com.adriano.swde.stepdefs;


import br.com.adriano.swde.Flow;
import br.com.adriano.swde.Steps;
import br.com.adriano.swde.infrastructure.dynamo.repository.ExchangeStockShareDynamoDBRepository;
import br.com.adriano.swde.infrastructure.dynamo.repository.ShareHighestPriceMonthDynamoDBRepository;
import br.com.adriano.swde.model.SharePeakPriceMonth;
import br.com.adriano.swde.model.StockShare;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SharesHighestPriceMonthlyStepDefs {

    private ExchangeStockShareDynamoDBRepository exchangeStockShareDynamoDBRepository
            = new ExchangeStockShareDynamoDBRepository();

    private ShareHighestPriceMonthDynamoDBRepository shareHighestPriceMonthDynamoDBRepository
            = new ShareHighestPriceMonthDynamoDBRepository();

    @DataTableType
    public StockShare defineStockShare(Map<String, String> entry) {
        return new StockShare(
                entry.get("exchange"),
                entry.get("stockSymbol"),
                entry.get("date"),
                entry.get("stockPriceOpen"),
                entry.get("stockPriceHigh"),
                entry.get("stockPriceLow"),
                entry.get("stockPriceClose"),
                entry.get("stockVolume"),
                entry.get("stockPriceAdjClose")
        );
    }

    @DataTableType
    public SharePeakPriceMonth defineSharePeakPriceMonth(Map<String, String> entry) {
        return new SharePeakPriceMonth(
                entry.get("stockSymbol"),
                entry.get("month"),
                Double.valueOf(entry.get("peakPrice"))
        );
    }


    @Given("there were registered these shares prices history:")
    public void registeredSharePricesHistory(final List<StockShare> stockShares) {
        exchangeStockShareDynamoDBRepository.saveAll(stockShares);
    }

    @When("the sharesHighestPriceMonthly job run")
    public void runSharesHighestPriceMonthly() {
        Flow.builder().addStep(Steps.SHARE_HIGHEST_PRICE_MONTHLY_STEP.get()).build().run();
    }

    @Then("should be saved the following result")
    public void shouldBeSaved(
            final List<SharePeakPriceMonth> expectedSharePeakPriceMonthList) {
        List<SharePeakPriceMonth> result = shareHighestPriceMonthDynamoDBRepository.findAll();

        assertResult(
                "The saved shares peak price by month list is not as expected",
                expectedSharePeakPriceMonthList,
                result);
    }

    private void assertResult(
            String failMessage,
            List<SharePeakPriceMonth> expectedSharePeakPriceMonthList,
            List<SharePeakPriceMonth> result) {

        assertEquals(
                "The size lists isn't as expected.", expectedSharePeakPriceMonthList.size(), result.size());

        sortLists(expectedSharePeakPriceMonthList, result);

        for (int i = 0; i < result.size(); i++) {
            assertSharePeakPriceMonth(expectedSharePeakPriceMonthList.get(i), result.get(i));
        }
    }

    private void sortLists(List<SharePeakPriceMonth>... lists) {
        for (List<SharePeakPriceMonth> list : lists) {
            Collections.sort(list);
        }
    }

    private void assertSharePeakPriceMonth(
            SharePeakPriceMonth expected, SharePeakPriceMonth returned) {
        assertEquals("The share peak price isn't as expected", expected, returned);
    }
}
