package br.com.adriano.swde.infrastructure.dynamo.model.local;

public class TableStructure {
    public final String tableName;
    public final ColumnKey hashKeyName;
    public final ColumnKey sortKeyName;

    public TableStructure(String tableName, ColumnKey hashKeyName, ColumnKey sortKeyName) {
        this.hashKeyName = hashKeyName;
        this.sortKeyName = sortKeyName;
        this.tableName = tableName;
    }

    public boolean hasSortKey() {
        return sortKeyName != null;
    }
}
