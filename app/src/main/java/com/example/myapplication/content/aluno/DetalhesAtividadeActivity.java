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

public class DetalhesAtividadeActivity extends AppCompatActivity {

    private ListarAtividadeConteudo listarAtividadeConteudo;
    private TextView txtTituloNovaAtividade;
    private TextView txtMateriaNovaAtividade;
    private TextView txtNomeProfessorNovaAtividade;
    private TextView txtCaminhoArquivoNovaAtividade;
    private TextView txtDescricaoNovaAtividade;
    private Button btnFecharDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atividade);

        // Comando para deixar a barra de status transparente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtTituloNovaAtividade = findViewById(R.id.titulo_detalhes_nova_atividade);
        txtMateriaNovaAtividade = findViewById(R.id.materia_detalhes_nova_atividade);
        txtNomeProfessorNovaAtividade = findViewById(R.id.nome_professor_detalhes_nova_atividade);
        txtCaminhoArquivoNovaAtividade = findViewById(R.id.caminho_arquivo_detalhes_nova_atividade);
        txtDescricaoNovaAtividade = findViewById(R.id.descricao_detalhes_nova_atividade);
        btnFecharDetalhes = findViewById(R.id.btn_fechar_detalhes_nova_atividade);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent conteudo = getIntent();
        listarAtividadeConteudo = conteudo.getParcelableExtra("atividade");
        if(listarAtividadeConteudo.equals("")){
            Toast.makeText(getApplicationContext(), "Erro ao recuperar informações do aluno", Toast.LENGTH_LONG).show();
        }else {

            txtTituloNovaAtividade.setText(listarAtividadeConteudo.getTitulo());
            txtMateriaNovaAtividade.setText(listarAtividadeConteudo.getMateria());
            txtNomeProfessorNovaAtividade.setText(listarAtividadeConteudo.getProfessor());
            txtDescricaoNovaAtividade.setText(listarAtividadeConteudo.getDescricaoAtividade());
            txtCaminhoArquivoNovaAtividade.setText(listarAtividadeConteudo.getCaminhoArquivo());

            btnFecharDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }


    }
}