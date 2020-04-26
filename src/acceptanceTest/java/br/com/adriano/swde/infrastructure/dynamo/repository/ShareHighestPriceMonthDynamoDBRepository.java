package br.com.adriano.swde.infrastructure.dynamo.repository;

import br.com.adriano.swde.infrastructure.dynamo.DynamoDBInstance;
import br.com.adriano.swde.model.SharePeakPriceMonth;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.*;

import java.util.ArrayList;
import java.util.List;

public class ShareHighestPriceMonthDynamoDBRepository {

    public List<SharePeakPriceMonth> findAll() throws Exception {
        DynamoDB dynamoDB = new DynamoDB(DynamoDBInstance.instance().getOrCreate());

        TableKeysAndAttributes sharePeakPriceMonth
                = new TableKeysAndAttributes(getTableName());

        sharePeakPriceMonth.addHashAndRangePrimaryKey("stockShareSymbol", "ASP",
                "month", "2001-11");
        sharePeakPriceMonth.addHashAndRangePrimaryKey("stockShareSymbol", "ASP",
                "month", "2001-12");

        BatchGetItemOutcome outcome = dynamoDB.batchGetItem(sharePeakPriceMonth);

        List<Item> items = outcome.getTableItems().get(getTableName());

        List<SharePeakPriceMonth> result = new ArrayList<>();

        for (Item item : items) {
            result.add(new SharePeakPriceMonth(
                    item.getString("stockShareSymbol"),
                    item.getString("month"),
                    item.getDouble("peakPrice")));
        }

        return result;
    }

    private static String getTableName() {
        return SharePeakPriceMonth.class.getAnnotation(DynamoDBTable.class).tableName();
    }
}
