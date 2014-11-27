package pt.rikmartins.urbaniceandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.HashMap;

import pt.rikmartins.sqlitehelper.ColumnDefinition;
import pt.rikmartins.sqlitehelper.CreateTableStatementHelper;
import pt.rikmartins.sqlitehelper.SqliteHelperException;

import static pt.rikmartins.sqlitehelper.ColumnDefinition.ColumnConstraint;
import static pt.rikmartins.sqlitehelper.ColumnDefinition.TypeName;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Corrida;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Estacao;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.EstacaoDaLinha;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Linha;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Paragem;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Plataforma;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Ponto;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.PontoDoTracado;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Tracado;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.TracadoDaLinha;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract.Versao;

/**
 * Created by ricardo on 05-11-2014.
 */
public class UrbaniceDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "urbanice.db";

    private static CreateTableStatementHelper baseCreateTable = null;

    private static HashMap<String, String> linhaProjectionMap;
    private static HashMap<String, String> corridaProjectionMap;
    private static HashMap<String, String> tracadoProjectionMap;
    private static HashMap<String, String> estacaoProjectionMap;
    private static HashMap<String, String> plataformaProjectionMap;
    private static HashMap<String, String> paragemProjectionMap;
    private static HashMap<String, String> pontoProjectionMap;
    static {
        linhaProjectionMap = new HashMap<String, String>();
        linhaProjectionMap.put(Linha._ID, Linha._ID);
        linhaProjectionMap.put(Linha.COLUMN_NAME_NOME, Linha.COLUMN_NAME_NOME);

        corridaProjectionMap = new HashMap<String, String>();
        corridaProjectionMap.put(Corrida._ID, Corrida._ID);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_ID_LINHA, Corrida.COLUMN_NAME_ID_LINHA);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_ACTIVA, Corrida.COLUMN_NAME_ACTIVA);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_NO_DOMINGO, Corrida.COLUMN_NAME_NO_DOMINGO);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_NA_SEGUNDA, Corrida.COLUMN_NAME_NA_SEGUNDA);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_NA_TERCA, Corrida.COLUMN_NAME_NA_TERCA);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_NA_QUARTA, Corrida.COLUMN_NAME_NA_QUARTA);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_NA_QUINTA, Corrida.COLUMN_NAME_NA_QUINTA);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_NA_SEXTA, Corrida.COLUMN_NAME_NA_SEXTA);
        corridaProjectionMap.put(Corrida.COLUMN_NAME_NO_SABADO, Corrida.COLUMN_NAME_NO_SABADO);

        tracadoProjectionMap = new HashMap<String, String>();
        tracadoProjectionMap.put(Tracado._ID, Tracado._ID);

        estacaoProjectionMap = new HashMap<String, String>();
        estacaoProjectionMap.put(Estacao._ID, Estacao._ID);
        estacaoProjectionMap.put(Estacao.COLUMN_NAME_ID_PONTO, Estacao.COLUMN_NAME_ID_PONTO);
        estacaoProjectionMap.put(Estacao.COLUMN_NAME_NOME, Estacao.COLUMN_NAME_NOME);

        plataformaProjectionMap = new HashMap<String, String>();
        plataformaProjectionMap.put(Plataforma._ID, Plataforma._ID);
        plataformaProjectionMap.put(Plataforma.COLUMN_NAME_ID_ESTACAO, Plataforma.COLUMN_NAME_ID_ESTACAO);
        plataformaProjectionMap.put(Plataforma.COLUMN_NAME_ID_PONTO, Plataforma.COLUMN_NAME_ID_PONTO);

        paragemProjectionMap = new HashMap<String, String>();
        paragemProjectionMap.put(Paragem._ID, Paragem._ID);
        paragemProjectionMap.put(Paragem.COLUMN_NAME_ID_CORRIDA, Paragem.COLUMN_NAME_ID_CORRIDA);
        paragemProjectionMap.put(Paragem.COLUMN_NAME_ID_PLATAFORMA, Paragem.COLUMN_NAME_ID_PLATAFORMA);
        paragemProjectionMap.put(Paragem.COLUMN_NAME_HORA_DO_DIA, Paragem.COLUMN_NAME_HORA_DO_DIA);
        paragemProjectionMap.put(Paragem.COLUMN_NAME_MINUTO_DA_HORA, Paragem.COLUMN_NAME_MINUTO_DA_HORA);

        pontoProjectionMap = new HashMap<String, String>();
        pontoProjectionMap.put(Ponto._ID, Ponto._ID);
        pontoProjectionMap.put(Ponto.COLUMN_NAME_LATITUDE, Ponto.COLUMN_NAME_LATITUDE);
        pontoProjectionMap.put(Ponto.COLUMN_NAME_LONGITUDE, Ponto.COLUMN_NAME_LONGITUDE);
        pontoProjectionMap.put(Ponto.COLUMN_NAME_ALTITUDE, Ponto.COLUMN_NAME_ALTITUDE);
    }

    public UrbaniceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static CreateTableStatementHelper getBaseSqlCreateTable() throws SqliteHelperException {
        if (baseCreateTable == null) {
            baseCreateTable = new CreateTableStatementHelper();
            baseCreateTable.setIfNotExists(true);
            baseCreateTable.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(BaseColumns._ID)
                    .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.PRIMARY_KEY)));
        }
        return baseCreateTable;
    }

    private static String getSqlCreateTableCorrida() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(Corrida.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_ID_LINHA)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_ACTIVA)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_NO_DOMINGO)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_NA_SEGUNDA)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_NA_TERCA)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_NA_QUARTA)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_NA_QUINTA)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_NA_SEXTA)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Corrida.COLUMN_NAME_NO_SABADO)
                .addTypeName(TypeName.BOOL).addColumnConstraint(ColumnConstraint.NOT_NULL).setDefaultValue("0")));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTableEstacao() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(Estacao.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Estacao.COLUMN_NAME_ID_PONTO)
                .addTypeName(TypeName.INTEGER)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Estacao
                .COLUMN_NAME_NOME).addTypeName(TypeName.TEXT).addColumnConstraint(ColumnConstraint
                .NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTableEstacaoDaLinha() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(EstacaoDaLinha.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(EstacaoDaLinha.COLUMN_NAME_ID_ESTACAO)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(EstacaoDaLinha
                .COLUMN_NAME_ID_LINHA).addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint
                .NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTableLinha() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(Linha.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Linha.COLUMN_NAME_NOME)
                .addTypeName(TypeName.TEXT).addColumnConstraint(ColumnConstraint.NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTableParagem() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(Paragem.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Paragem.COLUMN_NAME_ID_CORRIDA)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Paragem.COLUMN_NAME_ID_PLATAFORMA)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Paragem.COLUMN_NAME_HORA_DO_DIA)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Paragem.COLUMN_NAME_MINUTO_DA_HORA)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTablePlataforma() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(Plataforma.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Plataforma.COLUMN_NAME_ID_ESTACAO)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Plataforma.COLUMN_NAME_ID_PONTO)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTablePonto() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(Ponto.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Ponto.COLUMN_NAME_LATITUDE)
                .addTypeName(TypeName.DOUBLE).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Ponto.COLUMN_NAME_LONGITUDE)
                .addTypeName(TypeName.DOUBLE).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Ponto.COLUMN_NAME_ALTITUDE)
                .addTypeName(TypeName.DOUBLE)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTablePontoDoTracado() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(PontoDoTracado.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(PontoDoTracado.COLUMN_NAME_ID_PONTO)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(PontoDoTracado.COLUMN_NAME_ID_TRACADO)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(PontoDoTracado.COLUMN_NAME_ORDEM)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTableTracado() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(Tracado.TABLE_NAME);

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTableTracadoDaLinha() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(UrbaniceDbHelper.getBaseSqlCreateTable());

        createTableStatementHelper.setTableName(TracadoDaLinha.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(TracadoDaLinha.COLUMN_NAME_ID_TRACADO)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(TracadoDaLinha.COLUMN_NAME_ID_LINHA)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlCreateTableVersao() throws SqliteHelperException {
        CreateTableStatementHelper createTableStatementHelper = new CreateTableStatementHelper(Versao.TABLE_NAME, null, false, true);

        createTableStatementHelper.setTableName(Versao.TABLE_NAME);

        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Versao.COLUMN_NAME_DESIGNACAO)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));
        createTableStatementHelper.addColumnDefinition(new ColumnDefinition(new ColumnDefinition.Builder().setColumnName(Versao.COLUMN_NAME_VALOR)
                .addTypeName(TypeName.INTEGER).addColumnConstraint(ColumnConstraint.NOT_NULL)));

        return new CreateTableStatementHelper(createTableStatementHelper).getCreateTable();
    }

    private static String getSqlDropTable(String tableName){
        return "DROP TABLE IF EXISTS " + tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableCorrida());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableEstacao());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableEstacaoDaLinha());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableLinha());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableParagem());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTablePlataforma());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTablePonto());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTablePontoDoTracado());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableTracado());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableTracadoDaLinha());
            db.execSQL(UrbaniceDbHelper.getSqlCreateTableVersao());
        } catch (SqliteHelperException e) {
            e.printStackTrace(); // TODO: Tratar desta excepção
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Corrida.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Estacao.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(EstacaoDaLinha.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Linha.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Paragem.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Plataforma.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Ponto.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(PontoDoTracado.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Tracado.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(TracadoDaLinha.TABLE_NAME));
        db.execSQL(UrbaniceDbHelper.getSqlDropTable(Versao.TABLE_NAME));
        onCreate(db);
    }

    public Cursor query(String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder,
                        String limit, String tables, HashMap<String, String> projectionMap) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tables);
        if (projectionMap != null)
            qb.setProjectionMap(projectionMap);

        SQLiteDatabase db = this.getReadableDatabase();

       /*
        * Performs the query. If no problems occur trying to read the database, then a Cursor
        * object is returned; otherwise, the cursor variable contains null. If no records were
        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
        */
        return qb.query(
                db,            // A base de dados
                projectionIn,  // As colunas a serem devolvidas (SELECT *******)
                selection,     // O texto para o WHERE
                selectionArgs, // Os valores para o WHERE que no anterior eram '?'
                groupBy,       // GROUP BY
                having,        // HAVING
                sortOrder,     // SORT BY
                limit          // LIMIT
        );
    }

    public Cursor queryLinha(String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having,
                             String sortOrder, String limit) {
        return query(projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit, Linha.TABLE_NAME, linhaProjectionMap);
    }

    public Cursor queryAllLinha(String[] projectionIn, String sortOrder, String limit) {
        return queryLinha(projectionIn, null, null, null, null, sortOrder, limit);
    }

    public Cursor querySingleLinha(int id_linha, String[] projectionIn) {
        return queryLinha(projectionIn, Linha._ID + " = ?", new String[]{String.valueOf(id_linha)}, null, null, null, null);
    }

    /**
     * Created by ricardo on 05-11-2014.
     */
    public static final class DbContract {
        private DbContract() {
        }

        public static abstract class Corrida implements BaseColumns {
            public static final String TABLE_NAME = "corrida";
            public static final String COLUMN_NAME_ID_LINHA = "id_linha";
            public static final String COLUMN_NAME_ACTIVA = "activa";
            public static final String COLUMN_NAME_NO_DOMINGO = "no_domingo";
            public static final String COLUMN_NAME_NA_SEGUNDA = "na_segunda";
            public static final String COLUMN_NAME_NA_TERCA = "na_terca";
            public static final String COLUMN_NAME_NA_QUARTA = "na_quarta";
            public static final String COLUMN_NAME_NA_QUINTA = "na_quinta";
            public static final String COLUMN_NAME_NA_SEXTA = "na_sexta";
            public static final String COLUMN_NAME_NO_SABADO = "no_sabado";
        }

        public static abstract class Estacao implements BaseColumns {
            public static final String TABLE_NAME = "estacao";
            public static final String COLUMN_NAME_ID_PONTO = "id_ponto";
            public static final String COLUMN_NAME_NOME = "nome";
        }

        public static abstract class EstacaoDaLinha implements BaseColumns {
            public static final String TABLE_NAME = "estacao_da_linha";
            public static final String COLUMN_NAME_ID_ESTACAO = "id_estacao";
            public static final String COLUMN_NAME_ID_LINHA = "id_linha";
        }

        public static abstract class Linha implements BaseColumns {
            public static final String TABLE_NAME = "linha";
            public static final String COLUMN_NAME_NOME = "nome";
        }

        public static abstract class Paragem implements BaseColumns {
            public static final String TABLE_NAME = "paragem";
            public static final String COLUMN_NAME_ID_CORRIDA = "id_corrida";
            public static final String COLUMN_NAME_ID_PLATAFORMA = "id_plataforma";
            public static final String COLUMN_NAME_HORA_DO_DIA = "hora_do_dia";
            public static final String COLUMN_NAME_MINUTO_DA_HORA = "minuto_da_hora";
        }

        public static abstract class Plataforma implements BaseColumns {
            public static final String TABLE_NAME = "plataforma";
            public static final String COLUMN_NAME_ID_ESTACAO = "id_estacao";
            public static final String COLUMN_NAME_ID_PONTO = "id_ponto";
        }

        public static abstract class Ponto implements BaseColumns {
            public static final String TABLE_NAME = "ponto";
            public static final String COLUMN_NAME_LATITUDE = "latitude";
            public static final String COLUMN_NAME_LONGITUDE = "longitude";
            public static final String COLUMN_NAME_ALTITUDE = "altitude";
        }

        public static abstract class PontoDoTracado implements BaseColumns {
            public static final String TABLE_NAME = "ponto_do_tracado";
            public static final String COLUMN_NAME_ID_PONTO = "id_ponto";
            public static final String COLUMN_NAME_ID_TRACADO = "id_tracado";
            public static final String COLUMN_NAME_ORDEM = "ordem";
        }

        public static abstract class Tracado implements BaseColumns {
            public static final String TABLE_NAME = "tracado";
        }

        public static abstract class TracadoDaLinha implements BaseColumns {
            public static final String TABLE_NAME = "tracado_da_linha";
            public static final String COLUMN_NAME_ID_TRACADO = "id_tracado";
            public static final String COLUMN_NAME_ID_LINHA = "id_linha";
        }

        public static abstract class Versao implements BaseColumns {
            public static final String TABLE_NAME = "versao";
            public static final String COLUMN_NAME_DESIGNACAO = "designacao";
            public static final String COLUMN_NAME_VALOR = "valor";
        }
    }
}
