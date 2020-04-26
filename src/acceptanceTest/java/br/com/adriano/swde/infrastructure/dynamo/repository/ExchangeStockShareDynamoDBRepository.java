package br.com.adriano.swde.infrastructure.dynamo.repository;

import br.com.adriano.swde.infrastructure.dynamo.DynamoDBInstance;
import br.com.adriano.swde.model.StockShare;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ExchangeStockShareDynamoDBRepository {

    private static final Logger LOGGER =
            LogManager.getLogger(ExchangeStockShareDynamoDBRepository.class);

    private final String TABLE_NAME = getTableName();

    public void saveAll(List<StockShare> stockShares) throws Exception {

        DynamoDB dynamoDB = new DynamoDB(DynamoDBInstance.instance().getOrCreate());

        Table table = dynamoDB.getTable(TABLE_NAME);

        LOGGER.info(
                "m=saveAll m=Going to save {} stockShares.",
                stockShares == null ? 0 : stockShares.size());

        for (StockShare ssi : stockShares) {
            Item stockShareItem = new Item();

            stockShareItem
                    .withPrimaryKey("stockSymbol", ssi.stockSymbol)
                    .withString("exchange", ssi.exchange)
                    .withString("date", ssi.date)
                    .withString("stockPriceOpen", ssi.stockPriceOpen)
                    .withString("stockPriceHigh", ssi.stockPriceHigh)
                    .withString("stockPriceLow", ssi.stockPriceLow)
                    .withString("stockPriceClose", ssi.stockPriceClose)
                    .withString("stockVolume", ssi.stockVolume)
                    .withString("stockPriceAdjClose", ssi.stockPriceAdjClose);

            table.putItem(stockShareItem);

            LOGGER.info("m=saveAll, m=Saved record: {}", stockShareItem);
        }
    }

    private static String getTableName() {
        return StockShare.class.getAnnotation(DynamoDBTable.class).tableName();
    }
}
