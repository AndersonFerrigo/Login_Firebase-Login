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
import com.example.myapplication.content.professor.DetalhesAtividadesRecebidasAlunoActivity;
import com.example.myapplication.model.conteudo.ListarAtividadeConteudo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DetalhesAtividadeActivity extends AppCompatActivity {
    /**
     * @since 22/09/2020
     */
    private ListarAtividadeConteudo listarAtividadeConteudo;

    private TextView txtTituloNovaAtividade;
    private TextView txtMateriaNovaAtividade;
    private TextView txtNomeProfessorNovaAtividade;
    private TextView txtDescricaoNovaAtividade;
    private Button btnFecharDetalhes;
    private Button btnDownloadDocumentoNovaAtividade;

    private String recebeDocumentoNovaAtividade;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_atividade);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtTituloNovaAtividade = findViewById(R.id.titulo_detalhes_nova_atividade);
        txtMateriaNovaAtividade = findViewById(R.id.materia_detalhes_nova_atividade);
        txtNomeProfessorNovaAtividade = findViewById(R.id.nome_professor_detalhes_nova_atividade);
        txtDescricaoNovaAtividade = findViewById(R.id.descricao_detalhes_nova_atividade);
        btnFecharDetalhes = findViewById(R.id.btn_fechar_detalhes_nova_atividade);
        btnDownloadDocumentoNovaAtividade = findViewById(R.id.btn_download_nova_atividade);

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
            Toast.makeText(getApplicationContext(), R.string.erro_detalhes_atividade_bundle, Toast.LENGTH_LONG).show();
        } else {

            txtTituloNovaAtividade.setText(listarAtividadeConteudo.getTitulo());
            txtMateriaNovaAtividade.setText(listarAtividadeConteudo.getMateria());
            txtNomeProfessorNovaAtividade.setText(listarAtividadeConteudo.getProfessor());
            txtDescricaoNovaAtividade.setText(listarAtividadeConteudo.getDescricao());
            recebeDocumentoNovaAtividade = listarAtividadeConteudo.getDocumento();

            btnFecharDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            btnDownloadDocumentoNovaAtividade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.informa_download_arquivo, Toast.LENGTH_LONG).show();

                    storageReference = firebaseStorage.getInstance().getReference();
                    reference = storageReference.child(recebeDocumentoNovaAtividade);

                    reference.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String arquivo = uri.toString();
                            downloadFile(DetalhesAtividadeActivity.this, recebeDocumentoNovaAtividade, DIRECTORY_DOWNLOADS, arquivo );
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
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