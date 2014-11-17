package pt.rikmartins.urbaniceandroid.sqlitetools;

/**
 * Created by ricardo on 15-11-2014.
 */
public class CreateTableStatementHelper {
    private String tableName;
    private String databaseName;

    private boolean temporary;
    private boolean ifNotExists;

    public CreateTableStatementHelper(String tableName){
        this.tableName = tableName;
        this.databaseName = null;
        this.temporary = false;
        this.ifNotExists = false;
    }

    public CreateTableStatementHelper(String tableName, String databaseName, boolean temporary, boolean ifNotExists){
        this.tableName = tableName;
        this.databaseName = databaseName;
        this.temporary = temporary;
        this.ifNotExists = ifNotExists;
    }

    public CreateTableStatementHelper addColumnDefinition(String columnName, ){
        return this;
    }
}
