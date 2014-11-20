package pt.rikmartins.sqlitehelper;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreateTableStatementHelperTest {

    @Test
    public void testIsReadyShouldReturnFalseIfNoTableNameIsSet() throws Exception {
        CreateTableStatementHelper ctsh1 = new CreateTableStatementHelper();
        assertFalse(ctsh1.isReady());
        ctsh1.setTableName("test");
        assertFalse(ctsh1.isReady());
        ctsh1.addColumnDefinition(new ColumnDefinition("column1"));
        assertTrue(ctsh1.isReady());
    }

    @Test
    public void testIsReadyShouldReturnFalseIfNoTableNameIsSet() throws Exception {
        CreateTableStatementHelper ctsh1 = new CreateTableStatementHelper();
        assertFalse(ctsh1.isReady());
        ctsh1.setTableName("test");
        assertFalse(ctsh1.isReady());
        ctsh1.addColumnDefinition(new ColumnDefinition("column1"));
        assertTrue(ctsh1.isReady());
    }

    @Test
    public void testGetCreateTable() throws Exception {
        CreateTableStatementHelper ctsh1 = new CreateTableStatementHelper();
        assertFalse(ctsh1.isReady());
        ctsh1.setTableName("test");
        assertFalse(ctsh1.isReady());
        ctsh1.addColumnDefinition(new ColumnDefinition("column1"));
        assertTrue(ctsh1.isReady());
    }
}