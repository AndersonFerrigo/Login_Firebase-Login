package com.example.myapplication.content.aluno.ui.atividades_concluidas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.atividades.EnviarAtividadeConcluida;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class EnviarAtividadesConcluidasFragment extends Fragment {
    /**
     * @since 26/09/2020
     */

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private View atividadeConcluida;
    private View informaUploadArquivo;

    private String recebemateriaSelecionada;

    private TextView recebeArquivoAtividadeConcluida;
    private TextView nomeAlunoAtividadeConcluida;
    private TextView turmaAlunoAtividadeConcluida;
    private EditText tituloAtividadeConcluida;

    private Spinner spnMateriaAtividadeConcluida;

    private Button btnProcurarAtividadeConcluida;
    private Button btnEnviarAtividadeConcluida;

    private Uri uriArquivoUploadAtividadesConcluidas;
    private DatabaseReference dbReferenceAtividadeConcluida;
    private FirebaseDatabase atividadeConcluidaDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private CadastroNovoUsuario alunoUsuario;
    private EnviarAtividadeConcluida enviarAtividadeConcluida;

    public EnviarAtividadesConcluidasFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        atividadeConcluida =  inflater.inflate(R.layout.enviar_atividades_concluidas_fragment, container, false);

        nomeAlunoAtividadeConcluida = atividadeConcluida.findViewById(R.id.txt_nome_aluno_atividade_concluida);
        turmaAlunoAtividadeConcluida = atividadeConcluida.findViewById(R.id.txt_turma_atividade_concluida);
        spnMateriaAtividadeConcluida = atividadeConcluida.findViewById(R.id.spn_materia_professor);
        tituloAtividadeConcluida = atividadeConcluida.findViewById(R.id.edt_titulo_atividade_concluida);
        btnProcurarAtividadeConcluida = atividadeConcluida.findViewById(R.id.btn_procurar_atividade_concluida);
        btnEnviarAtividadeConcluida = atividadeConcluida.findViewById(R.id.btn_enviar_atividade_concluida);
        recebeArquivoAtividadeConcluida = atividadeConcluida.findViewById(R.id.txt_recebe_arquivo_ativ_concluida_);
        informaUploadArquivo = atividadeConcluida.findViewById(R.id.progressbar_informa_upload);

        return atividadeConcluida;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(atividadeConcluida.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaAtividadeConcluida.setAdapter(adapterMateriaProfessor);

        dbReferenceAtividadeConcluida = atividadeConcluidaDataBase.getInstance().getReference().child("atividades_concluidas");
        storageReference = storage.getInstance().getReference();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle aluno = getActivity().getIntent().getExtras();
        if(aluno != null){
            alunoUsuario = aluno.getParcelable("aluno");
            nomeAlunoAtividadeConcluida.setText(alunoUsuario.getNome());
            turmaAlunoAtividadeConcluida.setText(alunoUsuario.getTurma());
        }else{
            Toast.makeText(getContext(), R.string.erro_informacoes_aluno_bundle, Toast.LENGTH_LONG).show();
        }

        spnMateriaAtividadeConcluida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                recebemateriaSelecionada = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnProcurarAtividadeConcluida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(atividadeConcluida.getContext(),  Manifest.permission.READ_EXTERNAL_STORAGE )
                                                                                            == PackageManager.PERMISSION_GRANTED){
                    openFile();
                }else{
                    ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        btnEnviarAtividadeConcluida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((nomeAlunoAtividadeConcluida.getText().toString().isEmpty())
                        || (tituloAtividadeConcluida.getText().toString().equals(""))
                        || (recebemateriaSelecionada.equals("Escolha uma matéria"))
                        || (turmaAlunoAtividadeConcluida.getText().toString().isEmpty())
                        || (recebeArquivoAtividadeConcluida.getText().toString().equals(""))
                        || (uriArquivoUploadAtividadesConcluidas.equals(""))) {

                    Toast.makeText(getContext(),R.string.campos_obrigatorios_aluno, Toast.LENGTH_LONG).show();

                }else {
                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    enviarAtividadeConcluida = new EnviarAtividadeConcluida();
                    String filename = System.currentTimeMillis() + "";

                    // Retorna o caminho raiz
                    storageReference.child("atividades_concluidas_uploads")
                                    .child(filename)
                                    .putFile(uriArquivoUploadAtividadesConcluidas)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String urlUploadedFile = taskSnapshot.getStorage().getPath();

                            enviarAtividadeConcluida.setUID(UUID.randomUUID().toString());
                            enviarAtividadeConcluida.setAluno(nomeAlunoAtividadeConcluida.getText().toString());
                            enviarAtividadeConcluida.setTitulo(tituloAtividadeConcluida.getText().toString());
                            enviarAtividadeConcluida.setMateria(recebemateriaSelecionada);
                            enviarAtividadeConcluida.setTurma(turmaAlunoAtividadeConcluida.getText().toString());
                            enviarAtividadeConcluida.setDocumento(urlUploadedFile);

                            dbReferenceAtividadeConcluida.push().setValue(enviarAtividadeConcluida)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    informaUploadArquivo.setVisibility(View.GONE);
                                    Toast.makeText(atividadeConcluida.getContext(), R.string.sucesso_enviar_atividade_concluida, Toast.LENGTH_LONG).show();
                                    limparDados();
                                    habilitarComponentes();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    informaUploadArquivo.setVisibility(View.GONE);
                                    Toast.makeText(atividadeConcluida.getContext(), R.string.falha_enviar_atividade_concluida, Toast.LENGTH_LONG).show();
                                    habilitarComponentes();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(atividadeConcluida.getContext(), R.string.erro_upload_arquivo, Toast.LENGTH_LONG).show();
                            habilitarComponentes();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            informaUploadArquivo.setVisibility(View.VISIBLE);
                            desabilitarComponentes();
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            openFile();
        }else{
            Toast.makeText(atividadeConcluida.getContext(), R.string.permissao_leitura_storage, Toast.LENGTH_LONG).show();
        }
    }

    public void openFile() {
        String mimeType = "*/*";
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        sIntent.putExtra("CONTENT_TYPE", mimeType);
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getActivity().getPackageManager().resolveActivity(sIntent, 0) != null){
            chooserIntent = Intent.createChooser(sIntent, "Abrir arquivo");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Abrir arquivo");
        }

        try {
            startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(atividadeConcluida.getContext(), R.string.nenhum_arquivo_encontrado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_FILE_REQUESTCODE
                && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uriArquivoUploadAtividadesConcluidas = resultData.getData();
                recebeArquivoAtividadeConcluida.setText(resultData.getData().getLastPathSegment());
            }
        }
    }

    public void limparDados(){
        tituloAtividadeConcluida.setText("");
        recebeArquivoAtividadeConcluida.setText("");
    }

    public void desabilitarComponentes(){
        nomeAlunoAtividadeConcluida.setEnabled(false);
        turmaAlunoAtividadeConcluida.setEnabled(false);
        spnMateriaAtividadeConcluida.setEnabled(false);
        tituloAtividadeConcluida.setEnabled(false);
        btnProcurarAtividadeConcluida.setEnabled(false);
        btnEnviarAtividadeConcluida.setEnabled(false);
        recebeArquivoAtividadeConcluida.setEnabled(false);
        informaUploadArquivo.setEnabled(false);

    }

    public void habilitarComponentes(){
        nomeAlunoAtividadeConcluida.setEnabled(true);
        turmaAlunoAtividadeConcluida.setEnabled(true);
        spnMateriaAtividadeConcluida.setEnabled(true);
        tituloAtividadeConcluida.setEnabled(true);
        btnProcurarAtividadeConcluida.setEnabled(true);
        btnEnviarAtividadeConcluida.setEnabled(true);
        recebeArquivoAtividadeConcluida.setEnabled(true);
        informaUploadArquivo.setEnabled(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}