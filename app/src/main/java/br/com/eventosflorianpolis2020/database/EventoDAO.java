package br.com.eventosflorianpolis2020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.eventosflorianpolis2020.database.entity.EventoEntity;
import br.com.eventosflorianpolis2020.database.entity.LocaisEntity;
import br.com.eventosflorianpolis2020.modelo.Eventos;
import br.com.eventosflorianpolis2020.modelo.Locais;

public class EventoDAO {

    private final String SQL_MOSTRAR_TODOS = "SELECT evento._id, evento.nome, data, idlocal, descricao, bairro, cidade, capacidade FROM " +
            EventoEntity.TABLE_NAME + " INNER JOIN " + LocaisEntity.TABLE_NAME + " ON " +
            EventoEntity.COLUMN_NAME_ID_LOCAL + " = " + LocaisEntity.TABLE_NAME +  "." + LocaisEntity._ID;

    private DBGateway dbGateway;

    public EventoDAO (Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean excluir (Eventos eventos) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, eventos.getNome());
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
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, eventos.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_ID_LOCAL, eventos.getLocais().getId());
        if (eventos.getId() >0) {
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME,
                    contentValues,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(eventos.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME,
                null, contentValues) > 0;
    }

    public List<Eventos> listar() {
        List<Eventos> eventos = new ArrayList<Eventos>();
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
            Locais locais = new Locais(idLocal, descricao, bairro, cidade, capacidade);
            eventos.add(new Eventos(id, nome, data, locais));
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

    public List<Eventos> listarPesquisa(int opcao, String letra) {
        List<Eventos> eventosSearch = new ArrayList<>();


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

            Locais locais = new Locais(idLocal, descricao, bairro, cidade, capacidade);
            eventosSearch.add(new Eventos(id, nome, data, locais));
        }
        cursor.close();
        return eventosSearch;
    }

    public List<Eventos> listarPesquisaCidade(int opcao, String letra, String letraCidade) {
        List<Eventos> eventosSearch = new ArrayList<>();

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


            Locais locais = new Locais(idLocal, descricao, bairro, cidade, capacidade);
            eventosSearch.add(new Eventos(id, nome, data, locais));
        }
        cursor.close();
        return eventosSearch;

    }


}

