package pt.rikmartins.sqlitehelper;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ColumnDefinitionTest {

    private static final String TEST_COLUMN_NAME = "column";
    private ColumnDefinition columnDefinition;

    @Before
    public void setUp() throws Exception {
        columnDefinition = new ColumnDefinition(TEST_COLUMN_NAME);
    }

    @Test
    public void testGetColumnDefinition() throws Exception {
        assertThat(columnDefinition.getColumnDefinition(), containsString(TEST_COLUMN_NAME));
        columnDefinition.addTypeName(ColumnDefinition.TypeName.BOOL);
        assertThat(columnDefinition.getColumnDefinition(), containsString(" " + ColumnDefinition.TypeName.BOOL.getTypeName()));
        columnDefinition.addTypeName(ColumnDefinition.TypeName.INTEGER);
        assertThat(columnDefinition.getColumnDefinition(), containsString(" " + ColumnDefinition.TypeName.INTEGER.getTypeName()));
        columnDefinition.addColumnConstraint(ColumnDefinition.ColumnConstraint.NOT_NULL);
        assertThat(columnDefinition.getColumnDefinition(), containsString(" " + ColumnDefinition.ColumnConstraint.NOT_NULL.getConstraintName()));
        columnDefinition.addColumnConstraint(ColumnDefinition.ColumnConstraint.PRIMARY_KEY);
        assertThat(columnDefinition.getColumnDefinition(), containsString(" " + ColumnDefinition.ColumnConstraint.PRIMARY_KEY.getConstraintName()));
        columnDefinition.setDefault("5");
        assertThat(columnDefinition.getColumnDefinition(), containsString(" " + ColumnDefinition.DEFAULT_PSEUDO_CONSTRAINT + " 5"));

        assertThat(columnDefinition.getColumnDefinition(), allOf(startsWith(TEST_COLUMN_NAME), endsWith(ColumnDefinition.DEFAULT_PSEUDO_CONSTRAINT + " 5")));
    }

    @Test
    public void testGetColumnName() throws Exception {
        assertThat(columnDefinition.getColumnName(), containsString(TEST_COLUMN_NAME));
    }

    @Test
    public void testAddTypeName() throws Exception {

    }

    @Test
    public void testAddColumnConstraint() throws Exception {

    }

    @Test
    public void testSetDefault() throws Exception {

    }

    @Test
    public void testResetDefault() throws Exception {

    }

    @Test
    public void testGetDefault() throws Exception {

    }
}