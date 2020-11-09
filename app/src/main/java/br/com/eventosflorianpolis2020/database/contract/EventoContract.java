package br.com.eventosflorianpolis2020.database.contract;

import br.com.eventosflorianpolis2020.database.entity.EventoEntity;
import br.com.eventosflorianpolis2020.database.entity.LocaisEntity;

public final class EventoContract {

    private EventoContract() {}

    public static final String criarTabela() {
        return "CREATE TABLE " + EventoEntity.TABLE_NAME + " (" +
            EventoEntity._ID + " INTEGER PRIMARY KEY," +
            EventoEntity.COLUMN_NAME_NOME + " TEXT," +
            EventoEntity.COLUMN_NAME_DATA + " TEXT," +
            EventoEntity.COLUMN_NAME_ID_LOCAL + " INTEGER," +
            "FOREIGN KEY (" + EventoEntity.COLUMN_NAME_ID_LOCAL + ") REFERENCES " +
            LocaisEntity.TABLE_NAME + "(" + LocaisEntity._ID + "))";
    }

    public static final String removerTabela() {
        return "DROP TABLE IF EXISTS " + EventoEntity.TABLE_NAME;
    }
}
