package br.com.eventosflorianpolis2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.eventosflorianpolis2020.database.LocaisDAO;
import br.com.eventosflorianpolis2020.modelo.Locais;

public class CadastroLocaisActivity extends AppCompatActivity {

    private int id = 0;
    private EditText editTextNome;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextCapacidade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_locais);
        editTextNome = findViewById(R.id.et_nome);
        editTextBairro = findViewById(R.id.et_bairro);
        editTextCidade = findViewById(R.id.et_cidade);
        editTextCapacidade = findViewById(R.id.et_capacidade);

        carregarLocais();
    }

    public void carregarLocais() {
        Intent intent = new Intent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("locaisEdicao") != null) {
            Locais locais = (Locais) intent.getExtras().get("locaisEdicao");
            editTextNome.setText(locais.getNome());
            editTextBairro.setText(locais.getBairro());
            editTextCidade.setText(locais.getCidade());
            editTextCapacidade.setText(locais.getCapacidade());
            id = locais.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String capacidade = editTextCapacidade.getText().toString();
        Locais locais = new Locais(id, nome, bairro, cidade, capacidade);
        LocaisDAO locaisDAO = new LocaisDAO(getBaseContext());
        boolean salvou = locaisDAO.salvar(locais);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastroLocaisActivity.this, "Erro ao salvar",Toast.LENGTH_LONG).show();
        }
    }

}
