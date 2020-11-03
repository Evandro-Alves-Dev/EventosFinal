package br.com.eventosflorianpolis2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.eventosflorianpolis2020.database.EventoDAO;
import br.com.eventosflorianpolis2020.modelo.Eventos;

public class CadastroEventoActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextLocal;
    private EditText editTextData;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        editTextNome = findViewById(R.id.ed_edicaoNovoEvento);
        editTextLocal = findViewById(R.id.ed_edicaoNovoLocal);
        editTextData = findViewById(R.id.ed_edicaoNovaData);

        mostrarEvento();
    }

    public void mostrarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEditado") != null ) {
            Eventos evento = (Eventos) intent.getExtras().get("eventoEditado");
            editTextNome.setText(evento.getNome());
            editTextLocal.setText(evento.getLocal());
            editTextData.setText(evento.getData());
            id = evento.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        String local = editTextLocal.getText().toString();
        String data = editTextData.getText().toString();
        if (nome.length() == 0 || local.length() == 0 || data.length() == 0) {
            editTextNome.setError("Nome obrigatório");
            editTextLocal.setError("Local obrigatório");
            editTextData.setError("Data obrigatória");
        }
        else {
            Eventos evento = new Eventos(id, nome, local, data);
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