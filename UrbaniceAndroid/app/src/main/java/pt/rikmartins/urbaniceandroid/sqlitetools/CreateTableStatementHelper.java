package pt.rikmartins.urbaniceandroid.sqlitetools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ricardo on 15-11-2014.
 */
public class CreateTableStatementHelper {
    private final static String CREATE = "CREATE";
    private final static String TEMPORARY = "TEMPORARY";
    private final static String TABLE = "TABLE";
    private final static String IF_NOT_EXISTS = "IF NOT EXISTS";

    private final static String DOT_SEPARATOR = ".";
    private final static String COMMA_SEPARATOR = ", ";
    private final static String COLUMNS_DEFINITION_START = "(";
    private final static String COLUMNS_DEFINITION_END = ")";

    String tableName;
    String databaseName;

    boolean temporary;
    boolean ifNotExists;

    final List<ColumnDefinition> columnDefinitions;

    public CreateTableStatementHelper(String tableName, String databaseName, boolean temporary, boolean ifNotExists){
        this.tableName = tableName;
        this.databaseName = databaseName;
        this.temporary = temporary;
        this.ifNotExists = ifNotExists;
        columnDefinitions = new ArrayList<ColumnDefinition>();
    }

    public CreateTableStatementHelper(String tableName){
        this(tableName, null, false, false);
    }

    public CreateTableStatementHelper(){
        this(null, null, false, false);
    }

    public CreateTableStatementHelper(CreateTableStatementHelper createTableStatementHelper){
        this(createTableStatementHelper.tableName, createTableStatementHelper.databaseName, createTableStatementHelper.temporary,
                createTableStatementHelper.ifNotExists);
        columnDefinitions.addAll(createTableStatementHelper.columnDefinitions);
    }

    public boolean isReady(){
        if (tableName == null || tableName.trim().equals(""))
            return false;
        if (columnDefinitions.size() <= 0)
            return false;

        return true;
    }

    public CreateTableStatementHelper addColumnDefinition(ColumnDefinition columnDefinition){
        columnDefinitions.add(columnDefinition);
        return this;
    }

    public String getCreateTable(){
        String result = CREATE + " ";
        if (temporary)
            result += TEMPORARY + " ";
        result += TABLE + " ";
        if (ifNotExists)
            result += IF_NOT_EXISTS + " ";
        if (databaseName != null && !databaseName.trim().equals(""))
            result += databaseName + DOT_SEPARATOR;
        result += tableName + " ";

        result += COLUMNS_DEFINITION_START;
        Iterator<ColumnDefinition> columnDefinitionIterator = columnDefinitions.iterator();
        while (true){
            result += columnDefinitionIterator.next().getColumnDefinition();
            if (columnDefinitionIterator.hasNext())
                result += COMMA_SEPARATOR;
            else
                break;
        }
        result += COLUMNS_DEFINITION_END;

        return result;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public void setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
    }

    public List<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }
}
