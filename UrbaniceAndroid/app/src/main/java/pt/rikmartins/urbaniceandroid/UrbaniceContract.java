package pt.rikmartins.urbaniceandroid;

import android.net.Uri;
import android.provider.BaseColumns;
/**
 * Created by ricardo on 05-11-2014.
 */
public final class UrbaniceContract {
    /** The authority for the contacts provider */
    public static final String AUTORIDADE = "pt.rikmartins.androiddev.TransportesGuarda.provider";
    /** A content:// style uri to the authority for the contacts provider */
    public static final Uri URI_AUTORIDADE = Uri.parse("content://" + AUTORIDADE);

    private UrbaniceContract(){}

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
        public static final String COLUMN_NAME_ID_LINHA = "id_linha";
        public static final String COLUMN_NAME_ID_TRACADO = "id_tracado";
    }
}

