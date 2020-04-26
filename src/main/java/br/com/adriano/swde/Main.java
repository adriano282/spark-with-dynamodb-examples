package br.com.adriano.swde;

import br.com.adriano.swde.model.SharePeakPriceMonth;
import br.com.adriano.swde.model.StockShare;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.hadoop.dynamodb.DynamoDBConstants;
import org.apache.hadoop.dynamodb.DynamoDBItemWritable;
import org.apache.hadoop.dynamodb.read.DynamoDBInputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Function1;
import scala.Tuple2;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String ... args) {

        SparkSession sparkSession =
                SparkSession.builder().master("local[1]").appName("job").getOrCreate();

        JobConf jobConf = new JobConf(sparkSession.sparkContext().hadoopConfiguration());

        String dynamoTableName = StockShare.class.getAnnotation(DynamoDBTable.class).tableName();
        String peakPriceDynamoTableName = SharePeakPriceMonth.class.getAnnotation(DynamoDBTable.class).tableName();

        String endpoint = "http://localhost:8000/";
        String region = "eu-west-1";

        jobConf.set("dynamodb.output.tableName", peakPriceDynamoTableName);
        jobConf.set("dynamodb.input.tableName", dynamoTableName);
        jobConf.set("dynamodb.endpoint", endpoint);
        jobConf.set("dynamodb.regionid", region);
        jobConf.set(DynamoDBConstants.DYNAMODB_ACCESS_KEY_CONF, "teste123");
        jobConf.set(DynamoDBConstants.DYNAMODB_SECRET_KEY_CONF, "teste123");
        jobConf.set("dynamodb.servicename", "dynamodb");
        jobConf.set("dynamodb.throughput.write", "1");
        jobConf.set("dynamodb.throughput.read", "1");
        jobConf.set("dynamodb.throughtput.write.percent", "1");
        jobConf.set("dynamodb.throughtput.read.percent", "1");
        jobConf.set("mapred.input.format.class", "org.apache.haddop.dynamodb.read.DynamoDBInputFormat");
        jobConf.set("mapred.output.format.class", "org.apache.hadoop.dynamodb.write.DynamoDBOutputFormat");

        Function1<Tuple2<Text, DynamoDBItemWritable>, Map<String, AttributeValue>> f = new Function1<Tuple2<Text, DynamoDBItemWritable>, Map<String, AttributeValue>>() {
            @Override
            public Map<String, AttributeValue> apply(Tuple2<Text, DynamoDBItemWritable> textDynamoDBItemWritableTuple2) {
                return textDynamoDBItemWritableTuple2._2.getItem();
            }

            @Override
            public <A> Function1<A, Map<String, AttributeValue>> compose(Function1<A, Tuple2<Text, DynamoDBItemWritable>> function1) {
                return null;
            }

            @Override
            public <A> Function1<Tuple2<Text, DynamoDBItemWritable>, A> andThen(Function1<Map<String, AttributeValue>, A> function1) {
                return null;
            }

            @Override
            public boolean apply$mcZD$sp(double v) {
                return false;
            }

            @Override
            public double apply$mcDD$sp(double v) {
                return 0;
            }

            @Override
            public float apply$mcFD$sp(double v) {
                return 0;
            }

            @Override
            public int apply$mcID$sp(double v) {
                return 0;
            }

            @Override
            public long apply$mcJD$sp(double v) {
                return 0;
            }

            @Override
            public void apply$mcVD$sp(double v) {

            }

            @Override
            public boolean apply$mcZF$sp(float v) {
                return false;
            }

            @Override
            public double apply$mcDF$sp(float v) {
                return 0;
            }

            @Override
            public float apply$mcFF$sp(float v) {
                return 0;
            }

            @Override
            public int apply$mcIF$sp(float v) {
                return 0;
            }

            @Override
            public long apply$mcJF$sp(float v) {
                return 0;
            }

            @Override
            public void apply$mcVF$sp(float v) {

            }

            @Override
            public boolean apply$mcZI$sp(int i) {
                return false;
            }

            @Override
            public double apply$mcDI$sp(int i) {
                return 0;
            }

            @Override
            public float apply$mcFI$sp(int i) {
                return 0;
            }

            @Override
            public int apply$mcII$sp(int i) {
                return 0;
            }

            @Override
            public long apply$mcJI$sp(int i) {
                return 0;
            }

            @Override
            public void apply$mcVI$sp(int i) {

            }

            @Override
            public boolean apply$mcZJ$sp(long l) {
                return false;
            }

            @Override
            public double apply$mcDJ$sp(long l) {
                return 0;
            }

            @Override
            public float apply$mcFJ$sp(long l) {
                return 0;
            }

            @Override
            public int apply$mcIJ$sp(long l) {
                return 0;
            }

            @Override
            public long apply$mcJJ$sp(long l) {
                return 0;
            }

            @Override
            public void apply$mcVJ$sp(long l) {

            }
        };


        RDD<Tuple2<Text, DynamoDBItemWritable>> tuple2RDD = sparkSession
                .sparkContext()
                .hadoopRDD(jobConf, DynamoDBInputFormat.class, Text.class, DynamoDBItemWritable.class, 10);


        JavaRDD<StockShare> stockShareJavaRDD = tuple2RDD
                .toJavaRDD()
                .map(x -> x._2.getItem())
                .map(x -> new StockShare(
                        x.get("exchange").getS(),
                        x.get("stockSymbol").getS(),
                        x.get("date").getS(),
                        x.get("stockPriceOpen").getS(),
                        x.get("stockPriceHigh").getS(),
                        x.get("stockPriceLow").getS(),
                        x.get("stockPriceClose").getS(),
                        x.get("stockVolume").getS(),
                        x.get("stockPriceAdjClose").getS())
                );

        Dataset<Row> datasetStockShare = sparkSession.createDataFrame(stockShareJavaRDD, StockShare.class);

        datasetStockShare.createOrReplaceTempView("NYSE");

        Dataset<Row> shareHighestPriceMonth = sparkSession.sql(
                "select " +
                            "substring(date, 1, 7) as month, " +
                            "stockSymbol as stockShareSymbol, " +
                            "max(stockPriceHigh) as peakPrice " +
                        "from nyse " +
                        "group by substring(date, 1, 7), stockSymbol");

        final String[] columns = shareHighestPriceMonth.columns();


        shareHighestPriceMonth
        .javaRDD()
        .mapToPair((PairFunction<Row, Text, DynamoDBItemWritable>) row -> {

                HashMap<String, AttributeValue> attributeValueMap = new HashMap<>();

                for (String c : columns) {
                    Object value = row.get(row.fieldIndex(c));

                    if (value != null) {
                        AttributeValue av = new AttributeValue();
                        if (value instanceof String || value instanceof Boolean)
                            av.setS(value.toString());
                        else
                            av.setN(value.toString());

                        attributeValueMap.put(c, av);
                    }
                }

                DynamoDBItemWritable dwt = new DynamoDBItemWritable();
                dwt.setItem(attributeValueMap);

            return new Tuple2<>(new Text(""), dwt);
        }).saveAsHadoopDataset(jobConf);



    }
}
