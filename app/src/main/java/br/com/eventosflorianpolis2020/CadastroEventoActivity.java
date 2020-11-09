package br.com.eventosflorianpolis2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import br.com.eventosflorianpolis2020.database.EventoDAO;
import br.com.eventosflorianpolis2020.database.LocaisDAO;
import br.com.eventosflorianpolis2020.modelo.Eventos;
import br.com.eventosflorianpolis2020.modelo.Locais;

public class CadastroEventoActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextData;

    private int id = 0;
    private Spinner spinnerLocais;
    private ArrayAdapter<Locais> locaisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");

        spinnerLocais = findViewById(R.id.sp_locais);
        editTextNome = findViewById(R.id.ed_edicaoNovoEvento);
        editTextData = findViewById(R.id.ed_edicaoNovaData);

        mostrarLocais();
        mostrarEvento();
    }

    private void mostrarLocais() {
        LocaisDAO locaisDAO = new LocaisDAO(getBaseContext());
        locaisAdapter = new ArrayAdapter<Locais>(CadastroEventoActivity.this,
                android.R.layout.simple_spinner_item,
                locaisDAO.listar());
        spinnerLocais.setAdapter(locaisAdapter);
    }

    public void mostrarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEditado") != null ) {
            Eventos evento = (Eventos) intent.getExtras().get("eventoEditado");
            editTextNome.setText(evento.getNome());
            editTextData.setText(evento.getData());
            int posicaoLocal = posicaoLocais(evento.getLocais());
            spinnerLocais.setSelection(posicaoLocal);
            id = evento.getId();
        }
    }

    private int posicaoLocais(Locais locais) {
        for (int posicao = 0; posicao < locaisAdapter.getCount(); posicao++) {
            if (locaisAdapter.getItem(posicao).getId() == locais.getId()){
                return posicao;
            }
        }
        return 0;
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        int posicaoLocais = (int) spinnerLocais.getSelectedItemId();
        Locais locais = (Locais) locaisAdapter.getItem(posicaoLocais);

        if (nome.length() == 0 || data.length() == 0) {
            editTextNome.setError("Nome obrigatório");
            editTextData.setError("Data obrigatória");

        }
        else {
            Eventos evento = new Eventos(id, nome, data, locais);
            EventoDAO eventoDao = new EventoDAO(getBaseContext());
            boolean salvou = eventoDao.salvar(evento);
            if (salvou) {
                finish();
            }
            else {
                Toast.makeText(CadastroEventoActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
            }

        }

    }
}