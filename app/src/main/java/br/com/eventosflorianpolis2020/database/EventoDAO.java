package br.com.eventosflorianpolis2020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.eventosflorianpolis2020.database.entity.EventoEntity;
import br.com.eventosflorianpolis2020.database.entity.LocaisEntity;
import br.com.eventosflorianpolis2020.modelo.Evento;
import br.com.eventosflorianpolis2020.modelo.Local;

public class EventoDAO {

    private final String SQL_MOSTRAR_TODOS = "SELECT evento._id, evento.nome, data, idlocal, descricao, bairro, cidade, capacidade FROM " +
            EventoEntity.TABLE_NAME + " INNER JOIN " + LocaisEntity.TABLE_NAME + " ON " +
            EventoEntity.COLUMN_NAME_ID_LOCAL + " = " + LocaisEntity.TABLE_NAME +  "." + LocaisEntity._ID;

    private DBGateway dbGateway;

    public EventoDAO (Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean excluir (Evento evento) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());
        if (evento.getId() > 0) {
            return dbGateway.getDatabase().delete(EventoEntity.TABLE_NAME,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(evento.getId())}) > 0;
        }
        return true;
    }

    public boolean salvar (Evento evento) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_ID_LOCAL, evento.getLocal().getId());
        if (evento.getId() >0) {
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME,
                    contentValues,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(evento.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME,
                null, contentValues) > 0;
    }

    public List<Evento> listar() {
        List<Evento> eventos = new ArrayList<Evento>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_MOSTRAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String descricao = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_NOME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, descricao, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }

    public String orientacao(int opcao) {
        if(opcao == 1) {
            return "DESC";
        }
        else if(opcao == 2) {
            return "ASC";
        }
        return "ASC";
    }

    public List<Evento> listarPesquisa(int opcao, String letra) {
        List<Evento> eventoSearches = new ArrayList<>();


        String SELECTION = "SELECT evento._id, evento.nome, data, idlocal, descricao, bairro, cidade, capacidade FROM "
                + EventoEntity.TABLE_NAME + " INNER JOIN " + LocaisEntity.TABLE_NAME + " ON " +
                EventoEntity.COLUMN_NAME_ID_LOCAL + " = " +
                LocaisEntity.TABLE_NAME + "." + LocaisEntity._ID +
                " WHERE " + EventoEntity.COLUMN_NAME_NOME+ " LIKE " + "'%" + letra + "%'" +
                " ORDER BY " + EventoEntity.COLUMN_NAME_NOME + " "+ orientacao(opcao);

        Cursor cursor = dbGateway.getDatabase().rawQuery(
                SELECTION , null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String descricao = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_NOME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CAPACIDADE));

            Local local = new Local(idLocal, descricao, bairro, cidade, capacidade);
            eventoSearches.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventoSearches;
    }

    public List<Evento> listarPesquisaCidade(int opcao, String letra, String letraCidade) {
        List<Evento> eventoSearches = new ArrayList<>();

        String SELECTION = "SELECT evento._id, evento.nome, data, idlocal, descricao, bairro, cidade, capacidade FROM "
                + EventoEntity.TABLE_NAME + " INNER JOIN " + LocaisEntity.TABLE_NAME + " ON " +
                EventoEntity.COLUMN_NAME_ID_LOCAL + " = " +
                LocaisEntity.TABLE_NAME + "." + LocaisEntity._ID +
                " WHERE " + EventoEntity.COLUMN_NAME_NOME + " LIKE " + "'%" + letra + "%'" +
                " AND " + LocaisEntity.COLUMN_NAME_CIDADE + " LIKE " + "'%" + letraCidade + "%'" +
                " ORDER BY " + EventoEntity.COLUMN_NAME_NOME + " " + orientacao(opcao);

        Cursor cursor = dbGateway.getDatabase().rawQuery(
                SELECTION , null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String descricao = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_NOME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CAPACIDADE));


            Local local = new Local(idLocal, descricao, bairro, cidade, capacidade);
            eventoSearches.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventoSearches;

    }
}

