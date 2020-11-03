package br.com.eventosflorianpolis2020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.eventosflorianpolis2020.database.entity.LocaisEntity;
import br.com.eventosflorianpolis2020.modelo.Locais;

public class LocaisDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + LocaisEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public LocaisDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Locais locais) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocaisEntity.COLUMN_NAME_NOME, locais.getNome());
        contentValues.put(LocaisEntity.COLUMN_NAME_BAIRRO, locais.getBairro());
        contentValues.put(LocaisEntity.COLUMN_NAME_CIDADE, locais.getCidade());
        contentValues.put(LocaisEntity.COLUMN_NAME_CAPACIDADE, locais.getCapacidade());
        if (locais.getId() > 0) {
            return dbGateway.getDatabase().update(LocaisEntity.TABLE_NAME,
                    contentValues,
                    LocaisEntity._ID + "=?",
                    new String[]{String.valueOf(locais.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(LocaisEntity.TABLE_NAME,
                null,
                contentValues) > 0;
    }

    public List<Locais> listar(){
        List<Locais> locais = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(LocaisEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_NOME));
            String bairro = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CIDADE));
            String capacidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CAPACIDADE));
            locais.add(new Locais(id, nome, bairro, cidade, capacidade));
        }
        cursor.close();
        return locais;
    }

    public boolean excluir(Locais locais) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocaisEntity.COLUMN_NAME_NOME, locais.getNome());
        contentValues.put(LocaisEntity.COLUMN_NAME_BAIRRO, locais.getBairro());
        contentValues.put(LocaisEntity.COLUMN_NAME_CIDADE, locais.getCidade());
        contentValues.put(LocaisEntity.COLUMN_NAME_CAPACIDADE, locais.getCapacidade());
        if (locais.getId() > 0) {
            return dbGateway.getDatabase().delete(LocaisEntity.TABLE_NAME,
                    LocaisEntity._ID + "=?",
                    new String[]{String.valueOf(locais.getId())}) > 0;
        }
        return true;
    }
}
