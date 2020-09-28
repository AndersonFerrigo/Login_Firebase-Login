package com.example.myapplication.content.aluno;

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
import com.example.myapplication.model.conteudo.ListarAtividadeConteudo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DetalhesConteudoActivity extends AppCompatActivity {
    /**
     * @since 22/09/2020
     */
    private ListarAtividadeConteudo listarAtividadeConteudo;

    private TextView txtTituloNovoConteudo;
    private TextView txtMateriaNovoConteudo;
    private TextView txtNomeProfessorNovoConteudo;
    private TextView txtDescricaoNovoConteudo;
    private Button btnFecharDetalhes;
    private Button btnDownloadDocumentoNovoConteudo;

    private String recebeDocumentoNovaAtividade;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_conteudo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtTituloNovoConteudo = findViewById(R.id.titulo_detalhes_novo_conteudo);
        txtMateriaNovoConteudo = findViewById(R.id.materia_detalhes_novo_conteudo);
        txtNomeProfessorNovoConteudo = findViewById(R.id.nome_professor_detalhes_novo_conteudo);
        txtDescricaoNovoConteudo = findViewById(R.id.descricao_detalhes_novo_conteudo);
        btnFecharDetalhes = findViewById(R.id.btn_fechar_detalhes_novo_conteudo);
        btnDownloadDocumentoNovoConteudo = findViewById(R.id.btn_download_novo_conteudo);
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
            Toast.makeText(getApplicationContext(), R.string.erro_detalhes_conteudo_bundle, Toast.LENGTH_LONG).show();
        }else {

            txtNomeProfessorNovoConteudo.setText(listarAtividadeConteudo.getProfessor());
            txtMateriaNovoConteudo.setText(listarAtividadeConteudo.getMateria());
            txtTituloNovoConteudo.setText(listarAtividadeConteudo.getTitulo());
            txtDescricaoNovoConteudo.setText(listarAtividadeConteudo.getDescricao());
            recebeDocumentoNovaAtividade = listarAtividadeConteudo.getDocumento();

            btnFecharDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            btnDownloadDocumentoNovoConteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.informa_download_arquivo, Toast.LENGTH_LONG).show();

                    storageReference = firebaseStorage.getInstance().getReference();
                    reference = storageReference.child(recebeDocumentoNovaAtividade);

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String arquivo = uri.toString();
                            downloadFile(DetalhesConteudoActivity.this, recebeDocumentoNovaAtividade, DIRECTORY_DOWNLOADS, arquivo );
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {  }
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