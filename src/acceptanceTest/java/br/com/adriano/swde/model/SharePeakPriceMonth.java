package br.com.adriano.swde.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "SharePeakPriceMonth")
public class SharePeakPriceMonth implements Comparable<SharePeakPriceMonth> {

    @DynamoDBHashKey(attributeName = "stockShareSymbol")
    private String stockShareSymbol;

    public SharePeakPriceMonth(
            String stockShareSymbol,
            String month,
            double peakPrice) {
        this.stockShareSymbol = stockShareSymbol;
        this.month = month;
        this.peakPrice = peakPrice;
    }

    @DynamoDBRangeKey(attributeName = "month")
    private String month;

    private double peakPrice;

    public String getStockShareSymbol() {
        return stockShareSymbol;
    }

    public void setStockShareSymbol(String stockShareSymbol) {
        this.stockShareSymbol = stockShareSymbol;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getPeakPrice() {
        return peakPrice;
    }

    public void setPeakPrice(double peakPrice) {
        this.peakPrice = peakPrice;
    }

    @Override
    public int compareTo(SharePeakPriceMonth o) {
        return this.identity().compareTo(o.identity());
    }

    private String identity() {
        return this.getStockShareSymbol()
                .concat(this.getMonth())
                .concat(String.valueOf(this.getPeakPrice()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SharePeakPriceMonth)) return false;

        SharePeakPriceMonth that = (SharePeakPriceMonth) o;

        if (Double.compare(that.getPeakPrice(), getPeakPrice()) != 0) return false;
        if (getStockShareSymbol() != null ? !getStockShareSymbol().equals(that.getStockShareSymbol()) : that.getStockShareSymbol() != null)
            return false;
        return getMonth() != null ? getMonth().equals(that.getMonth()) : that.getMonth() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getStockShareSymbol() != null ? getStockShareSymbol().hashCode() : 0;
        result = 31 * result + (getMonth() != null ? getMonth().hashCode() : 0);
        temp = Double.doubleToLongBits(getPeakPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
