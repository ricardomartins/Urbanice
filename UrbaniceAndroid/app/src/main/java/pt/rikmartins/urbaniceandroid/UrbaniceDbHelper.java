package pt.rikmartins.urbaniceandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Corrida;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Estacao;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.EstacaoDaLinha;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Linha;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Paragem;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Plataforma;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Ponto;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.PontoDoTracado;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Tracado;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.TracadoDaLinha;
import static pt.rikmartins.urbaniceandroid.UrbaniceContract.Versao;

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
                    Corrida.COLUMN_NAME_NO_SABADO + BOOL_TYPE + NOT_NULL_CONSTRAINT + DEFAULT_CONSTRAINT + "0" + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_ESTACAO =
            "CREATE TABLE " + Estacao.TABLE_NAME + " ( " +
                    Estacao._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Estacao.COLUMN_NAME_ID_PONTO + INTEGER_TYPE + COMMA_SEP +
                    Estacao.COLUMN_NAME_NOME + TEXT_TYPE + NOT_NULL_CONSTRAINT + UNIQUE_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_ESTACAO_DA_LINHA =
            "CREATE TABLE " + EstacaoDaLinha.TABLE_NAME + " ( " +
                    EstacaoDaLinha._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    EstacaoDaLinha.COLUMN_NAME_ID_ESTACAO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    EstacaoDaLinha.COLUMN_NAME_ID_LINHA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_LINHA =
            "CREATE TABLE " + Linha.TABLE_NAME + " ( " +
                    Linha._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Linha.COLUMN_NAME_NOME + TEXT_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_PARAGEM =
            "CREATE TABLE " + Paragem.TABLE_NAME + " ( " +
                    Paragem._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_ID_CORRIDA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_ID_PLATAFORMA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_HORA_DO_DIA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Paragem.COLUMN_NAME_MINUTO_DA_HORA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_PLATAFORMA =
            "CREATE TABLE " + Plataforma.TABLE_NAME + " ( " +
                    Plataforma._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Plataforma.COLUMN_NAME_ID_ESTACAO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Plataforma.COLUMN_NAME_ID_PONTO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_PONTO =
            "CREATE TABLE " + Ponto.TABLE_NAME + " ( " +
                    Ponto._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    Ponto.COLUMN_NAME_LATITUDE + DOUBLE_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Ponto.COLUMN_NAME_LONGITUDE + DOUBLE_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Ponto.COLUMN_NAME_ALTITUDE + DOUBLE_TYPE + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_PONTO_DO_TRACADO =
            "CREATE TABLE " + PontoDoTracado.TABLE_NAME + " ( " +
                    PontoDoTracado._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    PontoDoTracado.COLUMN_NAME_ID_PONTO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    PontoDoTracado.COLUMN_NAME_ID_TRACADO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    PontoDoTracado.COLUMN_NAME_ORDEM + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_TRACADO =
            "CREATE TABLE " + Tracado.TABLE_NAME + " ( " +
                    Tracado._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_TRACADO_DA_LINHA =
            "CREATE TABLE " + TracadoDaLinha.TABLE_NAME + " ( " +
                    TracadoDaLinha._ID + INTEGER_TYPE + PRIMARY_KEY_CONSTRAINT + COMMA_SEP +
                    TracadoDaLinha.COLUMN_NAME_ID_TRACADO + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    TracadoDaLinha.COLUMN_NAME_ID_LINHA + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_CREATE_VERSAO =
            "CREATE TABLE " + Versao.TABLE_NAME + " ( " +
                    Versao.COLUMN_NAME_DESIGNACAO + TEXT_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    Versao.COLUMN_NAME_VALOR + INTEGER_TYPE + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
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

    }
}
