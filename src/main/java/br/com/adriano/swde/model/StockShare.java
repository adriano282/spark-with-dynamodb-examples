package br.com.adriano.swde.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "StockShare")
public class StockShare {

    // @DynamoDBAttribute
    public String exchange;

    @DynamoDBHashKey(attributeName = "stockSymbol")
    public String stockSymbol;

    @DynamoDBRangeKey(attributeName = "date")
    public String date;

    // @DynamoDBAttribute
    public String stockPriceOpen;

    // @DynamoDBAttribute
    public String stockPriceHigh;

    // @DynamoDBAttribute
    public String stockPriceLow;

    // @DynamoDBAttribute
    public String stockPriceClose;

    // @DynamoDBAttribute
    public String stockVolume;

    // @DynamoDBAttribute
    public String stockPriceAdjClose;

    public StockShare(
            String exchange,
            String stockSymbol,
            String date,
            String stockPriceOpen,
            String stockPriceHigh,
            String stockPriceLow,
            String stockPriceClose,
            String stockVolume,
            String stockPriceAdjClose) {
        this.exchange = exchange;
        this.stockSymbol = stockSymbol;
        this.date = date;
        this.stockPriceOpen = stockPriceOpen;
        this.stockPriceHigh = stockPriceHigh;
        this.stockPriceLow = stockPriceLow;
        this.stockPriceClose = stockPriceClose;
        this.stockVolume = stockVolume;
        this.stockPriceAdjClose = stockPriceAdjClose;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStockPriceOpen() {
        return stockPriceOpen;
    }

    public void setStockPriceOpen(String stockPriceOpen) {
        this.stockPriceOpen = stockPriceOpen;
    }

    public String getStockPriceHigh() {
        return stockPriceHigh;
    }

    public void setStockPriceHigh(String stockPriceHigh) {
        this.stockPriceHigh = stockPriceHigh;
    }

    public String getStockPriceLow() {
        return stockPriceLow;
    }

    public void setStockPriceLow(String stockPriceLow) {
        this.stockPriceLow = stockPriceLow;
    }

    public String getStockPriceClose() {
        return stockPriceClose;
    }

    public void setStockPriceClose(String stockPriceClose) {
        this.stockPriceClose = stockPriceClose;
    }

    public String getStockVolume() {
        return stockVolume;
    }

    public void setStockVolume(String stockVolume) {
        this.stockVolume = stockVolume;
    }

    public String getStockPriceAdjClose() {
        return stockPriceAdjClose;
    }

    public void setStockPriceAdjClose(String stockPriceAdjClose) {
        this.stockPriceAdjClose = stockPriceAdjClose;
    }
}
