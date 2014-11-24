package pt.rikmartins.sqlitehelper;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreateTableStatementHelperTest {

    @Test
    public void testIsReadyShouldReturnFalseIfNoTableNameIsSet() throws Exception {
        CreateTableStatementHelper ctsh1 = new CreateTableStatementHelper();
        ctsh1.addColumnDefinition(new ColumnDefinition("column1", TEXT_TYPE));
        assertFalse(ctsh1.isReady());
        ctsh1.setTableName("test");
        assertTrue(ctsh1.isReady());
    }

    @Test
    public void testIsReadyShouldReturnFalseIfNoColumnsWereAdded() throws Exception {
        CreateTableStatementHelper ctsh1 = new CreateTableStatementHelper("test");
        assertFalse(ctsh1.isReady());
        ctsh1.addColumnDefinition(new ColumnDefinition("column1", TEXT_TYPE));
        assertTrue(ctsh1.isReady());
    }

    @Test
    public void testGetCreateTable() throws Exception {
        CreateTableStatementHelper ctsh1 = new CreateTableStatementHelper("test");
        ctsh1.addColumnDefinition(new ColumnDefinition("column1", TEXT_TYPE));
        assertEquals(ctsh1.getCreateTable(), "CREATE TABLE test (column1)");
    }
}