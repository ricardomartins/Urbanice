package pt.rikmartins.sqlitehelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ricardo on 15-11-2014.
 */
public class ColumnDefinition {
    public static final int TEXT_TYPE = 1001;
    public static final int INTEGER_TYPE = 1002;
    public static final int DOUBLE_TYPE = 1003;
    public static final int BOOL_TYPE = 1004;

    public static final int NOT_NULL_CONSTRAINT = 2001;
    public static final int PRIMARY_KEY_CONSTRAINT = 2002;
    public static final int UNIQUE_CONSTRAINT = 2003;
    public static final int DEFAULT_CONSTRAINT = 2501;

    private static final String COMMA_SEP = ",";

    private static final Map<Integer, String> objects;
    static {
        objects = new HashMap<Integer, String>();

        objects.put(TEXT_TYPE, "TEXT");
        objects.put(INTEGER_TYPE, "INTEGER");
        objects.put(DOUBLE_TYPE, "DOUBLE");
        objects.put(BOOL_TYPE, "BOOL");

        objects.put(NOT_NULL_CONSTRAINT, "NOT NULL");
        objects.put(PRIMARY_KEY_CONSTRAINT, "PRIMARY KEY");
        objects.put(UNIQUE_CONSTRAINT, "UNIQUE");
        objects.put(DEFAULT_CONSTRAINT, "DEFAULT");
    }

    private Set<Integer> coiso; // TODO: Mudar o nome

    final String columnName;

    int type;

    public ColumnDefinition(String columnName, int type){
        this.columnName = columnName.trim();
    }

    public String getColumnDefinition(){
        return columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
