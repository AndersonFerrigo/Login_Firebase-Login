package com.example.myapplication.content.aluno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.ListarAtividadeConteudo;

public class DetalhesConteudoActivity extends AppCompatActivity {
    private ListarAtividadeConteudo listarAtividadeConteudo;
    private TextView txtTituloNovoConteudo;
    private TextView txtMateriaNovoConteudo;
    private TextView txtNomeProfessorNovoConteudo;
    private TextView txtCaminhoArquivoNovoConteudo;
    private TextView txtDescricaoNovoConteudo;
    private Button btnFecharDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_conteudo);

        // Comando para deixar a barra de status transparente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtTituloNovoConteudo = findViewById(R.id.titulo_detalhes_novo_conteudo);
        txtMateriaNovoConteudo = findViewById(R.id.materia_detalhes_novo_conteudo);
        txtNomeProfessorNovoConteudo = findViewById(R.id.nome_professor_detalhes_novo_conteudo);
        txtCaminhoArquivoNovoConteudo = findViewById(R.id.caminho_arquivo_detalhes_novo_conteudo);
        txtDescricaoNovoConteudo = findViewById(R.id.descricao_detalhes_novo_conteudo);
        btnFecharDetalhes = findViewById(R.id.btn_fechar_detalhes_novo_conteudo);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent conteudo = getIntent();
        listarAtividadeConteudo = conteudo.getParcelableExtra("conteudo");
        if(listarAtividadeConteudo.equals("")){
            Toast.makeText(getApplicationContext(), "Erro ao recuperar informações do aluno", Toast.LENGTH_LONG).show();
        }else {

            txtTituloNovoConteudo.setText(listarAtividadeConteudo.getTitulo());
            txtMateriaNovoConteudo.setText(listarAtividadeConteudo.getMateria());
            txtNomeProfessorNovoConteudo.setText(listarAtividadeConteudo.getProfessor());
            txtDescricaoNovoConteudo.setText(listarAtividadeConteudo.getDescricaoAtividade());
            txtCaminhoArquivoNovoConteudo.setText(listarAtividadeConteudo.getCaminhoArquivo());

            btnFecharDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

    }
}