package pt.rikmartins.sqlitehelper;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ricardo on 15-11-2014.
 */
public class ColumnDefinition {
    public static final String DEFAULT_PSEUDO_CONSTRAINT = "DEFAULT";

    private static final Pattern columnNamePattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");

    public enum TypeName {
        TEXT("TEXT"),
        INTEGER("INTEGER"),
        DOUBLE("DOUBLE"),
        BOOL("BOOL");

        private String typeName;

        private TypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }

    }

    public enum ColumnConstraint {
        NOT_NULL("NOT NULL"),
        PRIMARY_KEY("PRIMARY KEY"),
        UNIQUE("UNIQUE");

        private String constraintName;

        private ColumnConstraint(String constraintName) {
            this.constraintName = constraintName;
        }

        public String getConstraintName() {
            return constraintName;
        }

    }

    final String columnName;

    private Set<TypeName> typeNames;

    private Set<ColumnConstraint> columnConstraints;

    private String defaultValue;

    public ColumnDefinition(String columnName) throws SqliteHelperException {
        Matcher m = columnNamePattern.matcher(columnName);
        if (!m.find())
            throw new SqliteHelperException("Malformed column name");
        this.columnName = m.group();

        typeNames = new HashSet<TypeName>();
        columnConstraints = new HashSet<ColumnConstraint>();
        resetDefault();
    }

    public void addTypeName(TypeName typeName) {
        typeNames.add(typeName);
    }

    public void addColumnConstraint(ColumnConstraint columnConstraint) {
        columnConstraints.add(columnConstraint);
    }

    public String setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
        return getDefault();
    }

    public String resetDefault() {
        defaultValue = null;
        return getDefault();
    }

    public String getDefault() {
        return (defaultValue == null) ? "" : (ColumnDefinition.DEFAULT_PSEUDO_CONSTRAINT + " " + defaultValue);
    }

    public String getColumnDefinition() {
        String result = columnName + " ";
        for (TypeName typeName : typeNames)
            result += typeName.getTypeName() + " ";
        for (ColumnConstraint columnConstraint : columnConstraints)
            result += columnConstraint.getConstraintName() + " ";
        if (defaultValue != null)
            result += getDefault();
        return result.trim();
    }

    public String getColumnName() {
        return columnName;
    }
}
