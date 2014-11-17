package pt.rikmartins.urbaniceandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.HashMap;

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

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String BOOL_TYPE = " BOOL";

    private static final String NOT_NULL_CONSTRAINT = " NOT NULL";
    private static final String PRIMARY_KEY_CONSTRAINT = " PRIMARY KEY";
    private static final String SQL_CREATE_TRACADO =
            "CREATE TABLE " + Tracado.TABLE_NAME + " ( " +
                    Tracado._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT +
                    " )";
    private static final String UNIQUE_CONSTRAINT = " UNIQUE";
    private static final String DEFAULT_CONSTRAINT = " DEFAULT ";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_CORRIDA =
            "CREATE TABLE " + Corrida.TABLE_NAME + " ( " +
                    Corrida._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Corrida.COLUMN_NAME_ID_LINHA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Corrida.COLUMN_NAME_ACTIVA + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    Corrida.COLUMN_NAME_NO_DOMINGO + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    Corrida.COLUMN_NAME_NA_SEGUNDA + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    Corrida.COLUMN_NAME_NA_TERCA + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    Corrida.COLUMN_NAME_NA_QUARTA + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    Corrida.COLUMN_NAME_NA_QUINTA + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    Corrida.COLUMN_NAME_NA_SEXTA + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    Corrida.COLUMN_NAME_NO_SABADO + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" +
                    " )";
    private static final String SQL_CREATE_ESTACAO =
            "CREATE TABLE " + Estacao.TABLE_NAME + " ( " +
                    Estacao._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Estacao.COLUMN_NAME_ID_PONTO + INTEGER_TYPE + COMMA_SEP +
                    Estacao.COLUMN_NAME_NOME + TEXT_TYPE + NOT_NULL_CONSTRAINT + UNIQUE_CONSTRAINT +
                    " )";
    private static final String SQL_CREATE_ESTACAO_DA_LINHA =
            "CREATE TABLE " + EstacaoDaLinha.TABLE_NAME + " ( " +
                    EstacaoDaLinha._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    EstacaoDaLinha.COLUMN_NAME_ID_ESTACAO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    EstacaoDaLinha.COLUMN_NAME_ID_LINHA + INTEGER_TYPE + NOT_NULL_CONSTRAINT +
                    " )";
    private static final String SQL_CREATE_LINHA =
            "CREATE TABLE " + Linha.TABLE_NAME + " ( " +
                    Linha._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Linha.COLUMN_NAME_NOME + TEXT_TYPE + NOT_NULL_CONSTRAINT +
                    " )";
    private static final String SQL_CREATE_PARAGEM =
            "CREATE TABLE " + Paragem.TABLE_NAME + " ( " +
                    Paragem._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_ID_CORRIDA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_ID_PLATAFORMA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_HORA_DO_DIA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_MINUTO_DA_HORA + INTEGER_TYPE + NOT_NULL_CONSTRAINT +
                    " )";
    private static final String SQL_CREATE_PLATAFORMA =
            "CREATE TABLE " + Plataforma.TABLE_NAME + " ( " +
                    Plataforma._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Plataforma.COLUMN_NAME_ID_ESTACAO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Plataforma.COLUMN_NAME_ID_PONTO + INTEGER_TYPE + NOT_NULL_CONSTRAINT +
                    " )";
    private static final String SQL_CREATE_PONTO =
            "CREATE TABLE " + Ponto.TABLE_NAME + " ( " +
                    Ponto._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Ponto.COLUMN_NAME_LATITUDE + DOUBLE_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Ponto.COLUMN_NAME_LONGITUDE + DOUBLE_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Ponto.COLUMN_NAME_ALTITUDE + DOUBLE_TYPE +
                    " )";
    private static final String SQL_CREATE_PONTO_DO_TRACADO =
            "CREATE TABLE " + PontoDoTracado.TABLE_NAME + " ( " +
                    PontoDoTracado._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    PontoDoTracado.COLUMN_NAME_ID_PONTO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    PontoDoTracado.COLUMN_NAME_ID_TRACADO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    PontoDoTracado.COLUMN_NAME_ORDEM + INTEGER_TYPE + NOT_NULL_CONSTRAINT +
                    " )";
    private static final String SQL_CREATE_TRACADO_DA_LINHA =
            "CREATE TABLE " + TracadoDaLinha.TABLE_NAME + " ( " +
                    TracadoDaLinha._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    TracadoDaLinha.COLUMN_NAME_ID_TRACADO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    TracadoDaLinha.COLUMN_NAME_ID_LINHA + INTEGER_TYPE + NOT_NULL_CONSTRAINT +
                    " )";

    private static final String SQL_CREATE_VERSAO =
            "CREATE TABLE " + Versao.TABLE_NAME + " ( " +
                    Versao.COLUMN_NAME_DESIGNACAO + TEXT_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Versao.COLUMN_NAME_VALOR + INTEGER_TYPE + NOT_NULL_CONSTRAINT +
                    " )";

    private static final String SQL_DELETE_CORRIDA =
            "DROP TABLE IF EXISTS " + Corrida.TABLE_NAME;

    private static final String SQL_DELETE_ESTACAO =
            "DROP TABLE IF EXISTS " + Estacao.TABLE_NAME;

    private static final String SQL_DELETE_ESTACAO_DA_LINHA =
            "DROP TABLE IF EXISTS " + EstacaoDaLinha.TABLE_NAME;

    private static final String SQL_DELETE_LINHA =
            "DROP TABLE IF EXISTS " + Linha.TABLE_NAME;

    private static final String SQL_DELETE_PARAGEM =
            "DROP TABLE IF EXISTS " + Paragem.TABLE_NAME;

    private static final String SQL_DELETE_PLATAFORMA =
            "DROP TABLE IF EXISTS " + Plataforma.TABLE_NAME;

    private static final String SQL_DELETE_PONTO =
            "DROP TABLE IF EXISTS " + Ponto.TABLE_NAME;

    private static final String SQL_DELETE_PONTO_DO_TRACADO =
            "DROP TABLE IF EXISTS " + PontoDoTracado.TABLE_NAME;

    private static final String SQL_DELETE_TRACADO =
            "DROP TABLE IF EXISTS " + Tracado.TABLE_NAME;

    private static final String SQL_DELETE_TRACADO_DA_LINHA =
            "DROP TABLE IF EXISTS " + TracadoDaLinha.TABLE_NAME;

    private static final String SQL_DELETE_VERSAO =
            "DROP TABLE IF EXISTS " + Versao.TABLE_NAME;
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CORRIDA);
        db.execSQL(SQL_CREATE_ESTACAO);
        db.execSQL(SQL_CREATE_ESTACAO_DA_LINHA);
        db.execSQL(SQL_CREATE_LINHA);
        db.execSQL(SQL_CREATE_PARAGEM);
        db.execSQL(SQL_CREATE_PLATAFORMA);
        db.execSQL(SQL_CREATE_PONTO);
        db.execSQL(SQL_CREATE_PONTO_DO_TRACADO);
        db.execSQL(SQL_CREATE_TRACADO);
        db.execSQL(SQL_CREATE_TRACADO_DA_LINHA);
        db.execSQL(SQL_CREATE_VERSAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CORRIDA);
        db.execSQL(SQL_DELETE_ESTACAO);
        db.execSQL(SQL_DELETE_ESTACAO_DA_LINHA);
        db.execSQL(SQL_DELETE_LINHA);
        db.execSQL(SQL_DELETE_PARAGEM);
        db.execSQL(SQL_DELETE_PLATAFORMA);
        db.execSQL(SQL_DELETE_PONTO);
        db.execSQL(SQL_DELETE_PONTO_DO_TRACADO);
        db.execSQL(SQL_DELETE_TRACADO);
        db.execSQL(SQL_DELETE_TRACADO_DA_LINHA);
        db.execSQL(SQL_DELETE_VERSAO);
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
                             String sortOrder, String limit){
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
