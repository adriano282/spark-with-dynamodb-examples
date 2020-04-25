package br.com.adriano.swde.infrastructure.dynamo.model.local;

import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class ColumnKey {

    public ColumnKey(String name, ColumnType columnType) {
        this.name = name;
        this.columnType = columnType;
    }

    public final String name;
    public final ColumnType columnType;

    public enum ColumnType {
        STRING,
        NUMBER,
        BINARY;

        public ScalarAttributeType convertToAmazonType() {
            switch (this) {
                case STRING:
                    return ScalarAttributeType.S;

                case NUMBER:
                    return ScalarAttributeType.N;

                case BINARY:
                    return ScalarAttributeType.B;
            }

            throw new IllegalStateException("The ColumnType enum structure is invalid");
        }
    }
}
