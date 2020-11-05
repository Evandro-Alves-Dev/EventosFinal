package br.com.eventosflorianpolis2020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.eventosflorianpolis2020.database.entity.EventoEntity;
import br.com.eventosflorianpolis2020.modelo.Eventos;
import br.com.eventosflorianpolis2020.modelo.Locais;

public class EventoDAO {

    private final String SQL_MOSTRAR_TODOS = "SELECT * FROM " + EventoEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public EventoDAO (Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean excluir (Eventos eventos) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, eventos.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_LOCAL, eventos.getLocal());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, eventos.getData());
        if (eventos.getId() > 0) {
            return dbGateway.getDatabase().delete(EventoEntity.TABLE_NAME,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(eventos.getId())}) > 0;
        }
        return true;
    }

    public boolean salvar (Eventos eventos) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, eventos.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_LOCAL, eventos.getLocal());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, eventos.getData());
        if (eventos.getId() >0) {
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME,
                    contentValues,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(eventos.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME,
                null, contentValues) > 0;
    }

    public List<Eventos> listar () {
        List<Eventos> eventos = new ArrayList<Eventos>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_MOSTRAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String local = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_LOCAL));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            eventos.add(new Eventos(id, nome, local, data));
        }
        cursor.close();
        return eventos;
    }
}

