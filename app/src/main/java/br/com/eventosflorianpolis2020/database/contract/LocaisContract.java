package br.com.eventosflorianpolis2020.database.contract;

import br.com.eventosflorianpolis2020.database.entity.LocaisEntity;

public class LocaisContract {

    private LocaisContract() {}

    public static final String criarTabela() {
        return "CREATE TABLE " + LocaisEntity.TABLE_NAME + " (" +
                LocaisEntity._ID + " INTEGER PRIMARY KEY," +
                LocaisEntity.COLUMN_NAME_NOME + " TEXT," +
                LocaisEntity.COLUMN_NAME_BAIRRO + " TEXT," +
                LocaisEntity.COLUMN_NAME_CIDADE + " TEXT," +
                LocaisEntity.COLUMN_NAME_CAPACIDADE + " TEXT)";
    }

    public static final String removerTabela() {
        return "DROP TABLE IF EXISTS " + LocaisEntity.TABLE_NAME;
    }
}
