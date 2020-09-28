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

public class DetalhesDuvidaConteudoActivity extends AppCompatActivity {
    /**
     * @since 26/09/2020
     */
    private ListarDuvidasAlunoAtividadeConteudo listarDuvidasConteudo;

    private TextView txtTituloDuvidaConteudo;
    private TextView txtMateriaDuvidaConteudo;
    private TextView txtNomeAlunoDuvidaConteudo;
    private TextView txtCaminhoArquivoDuvidaConteudo;
    private TextView txtDescricaoDuvidasConteudo;
    private Button btnFecharDetalhesDuvidaConteudo;
    private Button btnDownloadDocumentoDuvidaConteudo;

    private String recebeDocumentoDownload;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_duvida_conteudo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtTituloDuvidaConteudo = findViewById(R.id.titulo_detalhes_duvida_conteudo);
        txtMateriaDuvidaConteudo = findViewById(R.id.materia_detalhes_duvida_conteudo);
        txtNomeAlunoDuvidaConteudo = findViewById(R.id.nome_aluno_detalhes_duvida_conteudo);
        txtCaminhoArquivoDuvidaConteudo = findViewById(R.id.caminho_arquivo_detalhes_duvida_conteudo);
        txtDescricaoDuvidasConteudo = findViewById(R.id.descricao_detalhes_duvidas_conteudo);
        btnFecharDetalhesDuvidaConteudo = findViewById(R.id.btn_fechar_detalhes_duvida_conteudo);
        btnDownloadDocumentoDuvidaConteudo = findViewById(R.id.btn_download_duvida_conteudo);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent conteudo = getIntent();
        listarDuvidasConteudo = conteudo.getParcelableExtra("duvidas_conteudos_aulas");
        if(listarDuvidasConteudo.equals("")){
            Toast.makeText(getApplicationContext(), R.string.erro_detalhes_conteudo_bundle, Toast.LENGTH_LONG).show();
        }else {
            txtTituloDuvidaConteudo.setText(listarDuvidasConteudo.getTitulo());
            txtMateriaDuvidaConteudo.setText(listarDuvidasConteudo.getMateria());
            txtNomeAlunoDuvidaConteudo.setText(listarDuvidasConteudo.getAluno());
            txtDescricaoDuvidasConteudo.setText(listarDuvidasConteudo.getDescricao());
            recebeDocumentoDownload = listarDuvidasConteudo.getDocumento();

            btnFecharDetalhesDuvidaConteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            btnDownloadDocumentoDuvidaConteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.informa_download_arquivo, Toast.LENGTH_LONG).show();

                    storageReference = firebaseStorage.getInstance().getReference();
                    reference = storageReference.child(recebeDocumentoDownload);

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String arquivo = uri.toString();
                            downloadFile(DetalhesDuvidaConteudoActivity.this, recebeDocumentoDownload, DIRECTORY_DOWNLOADS, arquivo );
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
