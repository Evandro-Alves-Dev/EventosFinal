package br.com.eventosflorianpolis2020;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.eventosflorianpolis2020.database.LocaisDAO;
import br.com.eventosflorianpolis2020.modelo.Locais;

public class ListaLocaisActivity extends AppCompatActivity {

    private ListView listViewLocais;
    private ArrayAdapter<Locais> adapterLocais;

    private int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_locais);
        setTitle("Local");
        listViewLocais = findViewById(R.id.lv_locais);
        definirOnclickListenerListView();
        definirOnLongClickListenerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocaisDAO locaisDAO = new LocaisDAO(getBaseContext());
        adapterLocais = new ArrayAdapter<Locais>(ListaLocaisActivity.this,
                android.R.layout.simple_list_item_1,
                locaisDAO.listar());
        listViewLocais.setAdapter(adapterLocais);
    }

    private void definirOnclickListenerListView() {
        listViewLocais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Locais locaisClicados = adapterLocais.getItem(position);

                new AlertDialog.Builder(ListaLocaisActivity.this)
                    .setIcon(android.R.drawable.ic_menu_edit)
                    .setTitle("Editar")
                    .setMessage("Editar este local?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ListaLocaisActivity.this, CadastroLocaisActivity.class);
                            intent.putExtra("locaisEditados", locaisClicados);
                            startActivity(intent);
                        }
                    })
                .setNegativeButton("Não", null).show();
            }
        });
    }

    private void definirOnLongClickListenerView() {
        listViewLocais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Locais locaisClicados = adapterLocais.getItem(position);

                new AlertDialog.Builder(ListaLocaisActivity.this)
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Excluir local?")
                    .setMessage("Excluir permanentemente este local?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LocaisDAO locaisDAO = new LocaisDAO(getBaseContext());
                            boolean excluiu = locaisDAO.excluir(locaisClicados);
                            if (excluiu) {
                                onResume();
                            }
                        }
                    })
                    .setNegativeButton("Não", null).show();
                return true;
            }
        });

    }

    public void onClickNovoLocal(View v) {
        Intent intent = new Intent(ListaLocaisActivity.this, CadastroLocaisActivity.class);
        startActivity(intent);
    }

    public void OnClickVoltarEventos(View v) {
        Intent intent = new Intent(ListaLocaisActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
