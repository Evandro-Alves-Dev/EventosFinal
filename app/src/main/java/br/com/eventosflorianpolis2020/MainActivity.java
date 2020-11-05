package br.com.eventosflorianpolis2020;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.eventosflorianpolis2020.database.EventoDAO;
import br.com.eventosflorianpolis2020.modelo.Eventos;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEvento;
    private ArrayAdapter<Eventos> adapterEvento;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewEvento = findViewById(R.id.listView_evento);
        onClickListenerView();
        onLongClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEvento = new ArrayAdapter<Eventos>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar());
        listViewEvento.setAdapter(adapterEvento);
    }

    private void onClickListenerView() {
        listViewEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Eventos eventoClicado = adapterEvento.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setTitle("Editar")
                        .setMessage("Editar este item?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                                intent.putExtra("eventoEditado", eventoClicado);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Não", null).show();
            }
        });
    }

    private void onLongClickListener() {
        listViewEvento.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Eventos eventoClicado = adapterEvento.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setTitle("Excluir item")
                    .setMessage("Excluir permanentemente este item?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EventoDAO eventoDao = new EventoDAO(getBaseContext());
                            boolean excluiu = eventoDao.excluir(eventoClicado);
                            if (excluiu) {
                                onResume();
                                Toast.makeText(MainActivity.this,
                                        "Item exluido com Sucesso.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Não", null).show();
                return true;
            }
        });
    }

    public void onClickNovoEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
        startActivity(intent);
    }

    public void onClickNovoLocal(View v) {
        Intent intent = new Intent(MainActivity.this, ListaLocaisActivity.class);
        startActivity(intent);
        finish();
    }
}