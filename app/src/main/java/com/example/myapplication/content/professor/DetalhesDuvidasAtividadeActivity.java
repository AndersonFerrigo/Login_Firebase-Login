package com.example.myapplication.content.professor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.duvidas.ListarDuvidasAlunoAtividadeConteudo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DetalhesDuvidasAtividadeActivity extends AppCompatActivity {
    /**
     * @since 26/09/2020
     */
    private ListarDuvidasAlunoAtividadeConteudo listarDuvidasAtividade;

    private TextView txtTituloDuvidaAtividade;
    private TextView txtMateriaDuvidaAtividade;
    private TextView txtNomeAlunoDuvidaAtividade;
    private TextView txtCaminhoArquivoDuvidaAtividade;
    private TextView txtDescricaoDuvidasAtividade;
    private Button btnFecharDetalhesDuvidaAtividade;
    private Button btnDownloadDocumentoDuvidaAtividade;

    private String recebeDocumentoDuvidaAtividade;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_duvidas_conteudo_atividade);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtTituloDuvidaAtividade = findViewById(R.id.titulo_detalhes_duvida_atividade);
        txtMateriaDuvidaAtividade = findViewById(R.id.materia_detalhes_duvida_atividade);
        txtNomeAlunoDuvidaAtividade = findViewById(R.id.nome_aluno_detalhes_duvida_atividade);
        txtCaminhoArquivoDuvidaAtividade = findViewById(R.id.caminho_arquivo_detalhes_duvida_atividade);
        txtDescricaoDuvidasAtividade = findViewById(R.id.descricao_detalhes_duvidas_atividade);
        btnFecharDetalhesDuvidaAtividade = findViewById(R.id.btn_fechar_detalhes_duvida_atividade);
        btnDownloadDocumentoDuvidaAtividade = findViewById(R.id.btn_download_duvida_atividade);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent conteudo = getIntent();
        listarDuvidasAtividade = conteudo.getParcelableExtra("duvidas_atividades_concluidas");
        if(listarDuvidasAtividade.equals("")){
            Toast.makeText(getApplicationContext(), R.string.erro_detalhes_atividade_concluida_bundle, Toast.LENGTH_LONG).show();
        }else {
            txtTituloDuvidaAtividade.setText(listarDuvidasAtividade.getTitulo());
            txtMateriaDuvidaAtividade.setText(listarDuvidasAtividade.getMateria());
            txtNomeAlunoDuvidaAtividade.setText(listarDuvidasAtividade.getAluno());
            txtDescricaoDuvidasAtividade.setText(listarDuvidasAtividade.getDescricao());

            recebeDocumentoDuvidaAtividade = listarDuvidasAtividade.getDocumento();

            btnFecharDetalhesDuvidaAtividade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            btnDownloadDocumentoDuvidaAtividade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.informa_download_arquivo, Toast.LENGTH_LONG).show();

                    storageReference = firebaseStorage.getInstance().getReference();
                    reference = storageReference.child(recebeDocumentoDuvidaAtividade);

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String arquivo = uri.toString();
                            downloadFile(DetalhesDuvidasAtividadeActivity.this, recebeDocumentoDuvidaAtividade, DIRECTORY_DOWNLOADS, arquivo );
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            });
        }
    }
    public void downloadFile(Context context, String filename, String diretorio, String url){

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, diretorio, filename);
        downloadManager.enqueue(request);
    }
}