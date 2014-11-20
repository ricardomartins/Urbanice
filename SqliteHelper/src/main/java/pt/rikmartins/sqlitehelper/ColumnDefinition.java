package pt.rikmartins.sqlitehelper;

/**
 * Created by ricardo on 15-11-2014.
 */
public class ColumnDefinition {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String BOOL_TYPE = " BOOL";
    private static final String NOT_NULL_CONSTRAINT = " NOT NULL";
    private static final String PRIMARY_KEY_CONSTRAINT = " PRIMARY KEY";
    private static final String UNIQUE_CONSTRAINT = " UNIQUE";
    private static final String DEFAULT_CONSTRAINT = " DEFAULT ";
    private static final String COMMA_SEP = ",";

    final String columnName;

    public ColumnDefinition(String columnName){
        this.columnName = columnName;
    }

    public String getColumnDefinition(){
        return columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
