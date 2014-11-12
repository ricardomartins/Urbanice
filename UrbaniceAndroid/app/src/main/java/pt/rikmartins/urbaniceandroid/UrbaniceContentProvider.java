package pt.rikmartins.urbaniceandroid;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

import static pt.rikmartins.urbaniceandroid.UrbaniceContentProvider.ProviderContract.*;
import static pt.rikmartins.urbaniceandroid.UrbaniceDbHelper.DbContract;

public class UrbaniceContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher;

    private UrbaniceDbHelper urbaniceDbHelper;

    private static HashMap<String, String> linhaProjectionMap;
    private static HashMap<String, String> corridaProjectionMap;
    private static HashMap<String, String> tracadoProjectionMap;
    private static HashMap<String, String> estacaoProjectionMap;
    private static HashMap<String, String> plataformaProjectionMap;
    private static HashMap<String, String> paragemProjectionMap;
    private static HashMap<String, String> pontoProjectionMap;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, Linha.TABLE_NAME, Linha.MATCH_LINHAS);
        sUriMatcher.addURI(AUTHORITY, Linha.TABLE_NAME + "/#", Linha.MATCH_LINHA_ID);
        sUriMatcher.addURI(AUTHORITY, Linha.TABLE_NAME + "/#/" + Corrida.TABLE_NAME, Linha.MATCH_LINHA_CORRIDAS);
        sUriMatcher.addURI(AUTHORITY, Linha.TABLE_NAME + "/#/" + Tracado.TABLE_NAME, Linha.MATCH_LINHA_TRACADOS);
        sUriMatcher.addURI(AUTHORITY, Linha.TABLE_NAME + "/#/" + Estacao.TABLE_NAME, Linha.MATCH_LINHA_ESTACOES);

        sUriMatcher.addURI(AUTHORITY, Corrida.TABLE_NAME, Corrida.MATCH_CORRIDAS);
        sUriMatcher.addURI(AUTHORITY, Corrida.TABLE_NAME + "/#", Corrida.MATCH_CORRIDA_ID);
        sUriMatcher.addURI(AUTHORITY, Corrida.TABLE_NAME + "/#/" + Paragem.TABLE_NAME, Corrida.MATCH_CORRIDA_PARAGENS);

        sUriMatcher.addURI(AUTHORITY, Tracado.TABLE_NAME, Tracado.MATCH_TRACADOS);
        sUriMatcher.addURI(AUTHORITY, Tracado.TABLE_NAME + "/#", Tracado.MATCH_TRACADO_ID);
        sUriMatcher.addURI(AUTHORITY, Tracado.TABLE_NAME + "/#/" + Ponto.TABLE_NAME, Tracado.MATCH_TRACADO_PONTOS);

        sUriMatcher.addURI(AUTHORITY, Estacao.TABLE_NAME, Estacao.MATCH_ESTACOES);
        sUriMatcher.addURI(AUTHORITY, Estacao.TABLE_NAME + "/#", Estacao.MATCH_ESTACAO_ID);
        sUriMatcher.addURI(AUTHORITY, Estacao.TABLE_NAME + "/#/" + Plataforma.TABLE_NAME, Estacao.MATCH_ESTACAO_PLATAFORMAS);

        sUriMatcher.addURI(AUTHORITY, Plataforma.TABLE_NAME, Plataforma.MATCH_PLATAFORMAS);
        sUriMatcher.addURI(AUTHORITY, Plataforma.TABLE_NAME + "/#", Plataforma.MATCH_PLATAFORMA_ID);
        sUriMatcher.addURI(AUTHORITY, Plataforma.TABLE_NAME + "/#/" + Paragem.TABLE_NAME, Plataforma.MATCH_PLATAFORMA_PARAGENS);

        sUriMatcher.addURI(AUTHORITY, Paragem.TABLE_NAME, Paragem.MATCH_PARAGENS);
        sUriMatcher.addURI(AUTHORITY, Paragem.TABLE_NAME + "/#", Paragem.MATCH_PARAGEM_ID);

        sUriMatcher.addURI(AUTHORITY, Ponto.TABLE_NAME, Ponto.MATCH_PONTOS);
        sUriMatcher.addURI(AUTHORITY, Ponto.TABLE_NAME + "/#", Ponto.MATCH_PONTO_ID);

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

    @Override
    public boolean onCreate() {
        urbaniceDbHelper = new UrbaniceDbHelper(getContext());

        return true;
    }

    public Cursor queryLinha(Uri uri, String[] projection, String selection,
                             String[] selectionArgs, String sortOrder, int uriMatch) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (uriMatch) {
            case Linha.MATCH_LINHA_ID:
                qb.appendWhere(
                        DbContract.Linha._ID + "=" +
                                uri.getPathSegments().get(1)); // TODO: Criar constante para o 1
            case Linha.MATCH_LINHAS:
                qb.setTables(DbContract.Linha.TABLE_NAME);
                qb.setProjectionMap(linhaProjectionMap);
                break;
            case Linha.MATCH_LINHA_CORRIDAS:
                qb.setTables(DbContract.Corrida.TABLE_NAME);
                qb.setProjectionMap(corridaProjectionMap);
                qb.appendWhere(
                        DbContract.Corrida.COLUMN_NAME_ID_LINHA + "=" +
                                uri.getPathSegments().get(1)); // TODO: Criar constante para o 1
                break;
            case Linha.MATCH_LINHA_TRACADOS:
                qb.setTables(DbContract.Tracado.TABLE_NAME + " JOIN " +
                        DbContract.TracadoDaLinha.TABLE_NAME + " ON " +
                        DbContract.Tracado.TABLE_NAME + "." + DbContract.Tracado._ID + " = " +
                        DbContract.TracadoDaLinha.TABLE_NAME + "." + DbContract.TracadoDaLinha.COLUMN_NAME_ID_TRACADO);
                qb.setProjectionMap(tracadoProjectionMap);
                qb.appendWhere(
                        DbContract.TracadoDaLinha.COLUMN_NAME_ID_LINHA + "=" +
                                uri.getPathSegments().get(1)); // TODO: Criar constante para o 1
                break;
            case Linha.MATCH_LINHA_ESTACOES:
                qb.setTables(DbContract.Estacao.TABLE_NAME + " JOIN " +
                        DbContract.EstacaoDaLinha.TABLE_NAME + " ON " +
                        DbContract.Estacao.TABLE_NAME + "." + DbContract.Estacao._ID + " = " +
                        DbContract.EstacaoDaLinha.TABLE_NAME + "." + DbContract.EstacaoDaLinha.COLUMN_NAME_ID_ESTACAO);
                qb.setProjectionMap(estacaoProjectionMap);
                qb.appendWhere(
                        DbContract.EstacaoDaLinha.COLUMN_NAME_ID_LINHA + "=" +
                                uri.getPathSegments().get(1)); // TODO: Criar constante para o 1
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    public Cursor queryCorrida(Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder, int uriMatch) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    public Cursor queryTracado(Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder, int uriMatch) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    public Cursor queryEstacao(Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder, int uriMatch) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    public Cursor queryPlataforma(Uri uri, String[] projection, String selection,
                                  String[] selectionArgs, String sortOrder, int uriMatch) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    public Cursor queryParagem(Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder, int uriMatch) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    public Cursor queryPonto(Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder, int uriMatch) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int uriMatch = sUriMatcher.match(uri);

        if (uriMatch >= Linha.MATCH_LINHAS && uriMatch < (Linha.MATCH_LINHAS + 100)){
            return queryLinha(uri, projection, selection, selectionArgs, sortOrder, uriMatch);
        }
        if (uriMatch >= Corrida.MATCH_CORRIDAS && uriMatch < (Corrida.MATCH_CORRIDAS + 100)){
            return queryCorrida(uri, projection, selection, selectionArgs, sortOrder, uriMatch);
        }
        if (uriMatch >= Tracado.MATCH_TRACADOS && uriMatch < (Tracado.MATCH_TRACADOS + 100)){
            return queryTracado(uri, projection, selection, selectionArgs, sortOrder, uriMatch);
        }
        if (uriMatch >= Estacao.MATCH_ESTACOES && uriMatch < (Estacao.MATCH_ESTACOES + 100)){
            return queryEstacao(uri, projection, selection, selectionArgs, sortOrder, uriMatch);
        }
        if (uriMatch >= Plataforma.MATCH_PLATAFORMAS && uriMatch < (Plataforma.MATCH_PLATAFORMAS + 100)){
            return queryPlataforma(uri, projection, selection, selectionArgs, sortOrder, uriMatch);
        }
        if (uriMatch >= Paragem.MATCH_PARAGENS && uriMatch < (Paragem.MATCH_PARAGENS + 100)){
            return queryParagem(uri, projection, selection, selectionArgs, sortOrder, uriMatch);
        }
        if (uriMatch >= Ponto.MATCH_PONTOS && uriMatch < (Ponto.MATCH_PONTOS + 100)){
            return queryPonto(uri, projection, selection, selectionArgs, sortOrder, uriMatch);
        }
        throw new UnsupportedOperationException("Not yet implemented");

//        switch (match) {
//            // If the incoming URI is for notes, chooses the Notes projection
//            case NOTES:
//                qb.setProjectionMap(sNotesProjectionMap);
//                break;
//
//           /* If the incoming URI is for a single note identified by its ID, chooses the
//            * note ID projection, and appends "_ID = <noteID>" to the where clause, so that
//            * it selects that single note
//            */
//            case NOTE_ID:
//                qb.setProjectionMap(sNotesProjectionMap);
//                qb.appendWhere(
//                        NotePad.Notes._ID +    // the name of the ID column
//                                "=" +
//                                // the position of the note ID itself in the incoming URI
//                                uri.getPathSegments().get(NotePad.Notes.NOTE_ID_PATH_POSITION));
//                break;
//
//            case LIVE_FOLDER_NOTES:
//                // If the incoming URI is from a live folder, chooses the live folder projection.
//                qb.setProjectionMap(sLiveFolderProjectionMap);
//                break;
//
//            default:
//                // If the URI doesn't match any of the known patterns, throw an exception.
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//
//
//        String orderBy;
//        // If no sort order is specified, uses the default
//        if (TextUtils.isEmpty(sortOrder)) {
//            orderBy = NotePad.Notes.DEFAULT_SORT_ORDER;
//        } else {
//            // otherwise, uses the incoming sort order
//            orderBy = sortOrder;
//        }
//
//        // Opens the database object in "read" mode, since no writes need to be done.
//        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
//
//       /*
//        * Performs the query. If no problems occur trying to read the database, then a Cursor
//        * object is returned; otherwise, the cursor variable contains null. If no records were
//        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
//        */
//        Cursor c = qb.query(
//                db,            // The database to query
//                projection,    // The columns to return from the query
//                selection,     // The columns for the where clause
//                selectionArgs, // The values for the where clause
//                null,          // don't group the rows
//                null,          // don't filter by row groups
//                orderBy        // The sort order
//        );
//
//        // Tells the Cursor what URI to watch, so it knows when its source data changes
//        c.setNotificationUri(getContext().getContentResolver(), uri);
//        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Created by ricardo on 10-11-2014.
     */
    public static final class ProviderContract {
        public static final String AUTHORITY = "pt.rikmartins.androiddev.urbaniceandroid.provider";

        public static abstract class Linha extends DbContract.Linha {
            public static final int MATCH_LINHAS = 100;
            public static final int MATCH_LINHA_ID = MATCH_LINHAS + 1;
            public static final int MATCH_LINHA_CORRIDAS = MATCH_LINHAS + 10;
            public static final int MATCH_LINHA_TRACADOS = MATCH_LINHAS + 20;
            public static final int MATCH_LINHA_ESTACOES = MATCH_LINHAS + 30;
        }

        public static abstract class Corrida extends DbContract.Corrida {
            public static final int MATCH_CORRIDAS = 200;
            public static final int MATCH_CORRIDA_ID = MATCH_CORRIDAS + 1;
            public static final int MATCH_CORRIDA_PARAGENS = MATCH_CORRIDAS + 10;
        }

        public static abstract class Tracado extends DbContract.Tracado {
            public static final int MATCH_TRACADOS = 300;
            public static final int MATCH_TRACADO_ID = MATCH_TRACADOS + 1;
            public static final int MATCH_TRACADO_PONTOS = MATCH_TRACADOS + 10;
        }

        public static abstract class Estacao extends DbContract.Estacao {
            public static final int MATCH_ESTACOES = 400;
            public static final int MATCH_ESTACAO_ID = MATCH_ESTACOES + 1;
            public static final int MATCH_ESTACAO_PLATAFORMAS = MATCH_ESTACOES + 10;
        }

        public static abstract class Plataforma extends DbContract.Plataforma {
            public static final int MATCH_PLATAFORMAS = 500;
            public static final int MATCH_PLATAFORMA_ID = MATCH_PLATAFORMAS + 1;
            public static final int MATCH_PLATAFORMA_PARAGENS = MATCH_PLATAFORMAS + 10;
        }

        public static abstract class Paragem extends DbContract.Paragem {
            public static final int MATCH_PARAGENS = 600;
            public static final int MATCH_PARAGEM_ID = MATCH_PARAGENS + 1;
        }

        public static abstract class Ponto extends DbContract.Ponto {
            public static final int MATCH_PONTOS = 700;
            public static final int MATCH_PONTO_ID = MATCH_PONTOS + 1;
        }

    }
}
