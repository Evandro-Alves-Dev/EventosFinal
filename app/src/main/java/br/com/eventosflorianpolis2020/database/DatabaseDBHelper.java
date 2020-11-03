package br.com.eventosflorianpolis2020.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import br.com.eventosflorianpolis2020.database.contract.EventoContract;
import br.com.eventosflorianpolis2020.database.contract.LocaisContract;
import br.com.eventosflorianpolis2020.database.entity.LocaisEntity;
import br.com.eventosflorianpolis2020.modelo.Locais;

public class DatabaseDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.evento";
    private static final int DATABASE_VERSION = 2;

    public DatabaseDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocaisContract.criarTabela());
        db.execSQL(EventoContract.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EventoContract.removerTabela());
        db.execSQL(LocaisContract.removerTabela());
        db.execSQL(LocaisContract.criarTabela());
        db.execSQL(EventoContract.criarTabela());
    }
}
