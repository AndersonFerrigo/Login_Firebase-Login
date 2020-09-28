package com.example.myapplication.content.aluno.ui.duvidas_atividade;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.example.myapplication.R;
import com.example.myapplication.model.duvidas.EnviarDuvidaAtividadeConteudo;
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

public class DuvidasAtividadesConcluidasFragment extends Fragment {
    /**
     * @since  26/09/2020
     */
    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private View vDuvidasAtividades;
    private View informaUploadArquivo;

    private String recebeMateriaDuvidaAtividadeProfessor;

    private TextView txtNomeAlunoAtividadeConcluidaDuvida;
    private TextView txtTurmaAlunoAtividadeConcluidaDuvida;
    private EditText edtTituloAtividadeDuvida;
    private EditText edtDescricaoDuvidaAtividadeConcluida;
    private TextView txtRecebeArquivoDuvidaAtividade;
    private Button btnProcurarAtividadeDuvidas;
    private Button btnEnviarAtividadeDuvida;
    private Spinner spnMateriaProfessorAtividadeDuvida;

    private Uri uriArquivoUploadAtividadesDuvida;
    private DatabaseReference dbReferenceAtividadeDuvida;
    private FirebaseDatabase atividadeDuvidaDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EnviarDuvidaAtividadeConteudo duvidaAtividade;
    private CadastroNovoUsuario alunoUsuario;

    public DuvidasAtividadesConcluidasFragment () { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vDuvidasAtividades =  inflater.inflate(R.layout.duvidas_atividades_concluidas_fragment, container, false);

        txtNomeAlunoAtividadeConcluidaDuvida = vDuvidasAtividades.findViewById(R.id.txt_nome_professor_duvida_atividade);
        txtTurmaAlunoAtividadeConcluidaDuvida = vDuvidasAtividades.findViewById(R.id.txt_turma_aluno_duvida_atividade);
        edtTituloAtividadeDuvida = vDuvidasAtividades.findViewById(R.id.edt_titulo_atividade_concluida);
        edtDescricaoDuvidaAtividadeConcluida = vDuvidasAtividades.findViewById(R.id.edt_duvidas_atividades_enviada);
        spnMateriaProfessorAtividadeDuvida = vDuvidasAtividades.findViewById(R.id.spn_materia_professor);
        btnProcurarAtividadeDuvidas = vDuvidasAtividades.findViewById(R.id.btn_procurar_atividade_duvida);
        btnEnviarAtividadeDuvida = vDuvidasAtividades.findViewById(R.id.btn_enviar_atividade_duvida);
        txtRecebeArquivoDuvidaAtividade = vDuvidasAtividades.findViewById(R.id.txt_recebe_duvida_arquivo_ativ_concluida);
        informaUploadArquivo = vDuvidasAtividades.findViewById(R.id.progressbar_informa_upload);

        return vDuvidasAtividades;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(vDuvidasAtividades.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessorAtividadeDuvida.setAdapter(adapterMateriaProfessor);

        dbReferenceAtividadeDuvida = atividadeDuvidaDataBase.getInstance().getReference().child("duvidas_atividades_concluidas");
        storageReference = storage.getInstance().getReference();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle aluno = getActivity().getIntent().getExtras();
        if(aluno != null){
            alunoUsuario = aluno.getParcelable("aluno");
            txtNomeAlunoAtividadeConcluidaDuvida.setText(alunoUsuario.getNome());
            txtTurmaAlunoAtividadeConcluidaDuvida.setText(alunoUsuario.getTurma());

        }else {
            Toast.makeText(getContext(), R.string.erro_informacoes_aluno_bundle, Toast.LENGTH_LONG).show();
        }

        spnMateriaProfessorAtividadeDuvida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                recebeMateriaDuvidaAtividadeProfessor = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnProcurarAtividadeDuvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(vDuvidasAtividades.getContext(),  Manifest.permission.READ_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED){
                    openFile();
                }else{
                    ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        btnEnviarAtividadeDuvida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((txtNomeAlunoAtividadeConcluidaDuvida.getText().toString().isEmpty())
                        || (txtTurmaAlunoAtividadeConcluidaDuvida.getText().toString().isEmpty())
                        || (edtTituloAtividadeDuvida.getText().toString().equals(""))
                        || (edtDescricaoDuvidaAtividadeConcluida.getText().toString().isEmpty())
                        || (recebeMateriaDuvidaAtividadeProfessor.equals("Escolha uma matéria"))
                        || (txtRecebeArquivoDuvidaAtividade.getText().toString().equals(""))
                        || (uriArquivoUploadAtividadesDuvida.equals(""))) {

                    Toast.makeText(getContext(), R.string.campos_obrigatorios_aluno, Toast.LENGTH_LONG).show();

                }else {

                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();

                    duvidaAtividade = new EnviarDuvidaAtividadeConteudo();

                    String filename = System.currentTimeMillis() + "";

                    storageReference.child("duvidas_atividades_concluidas_uploads")
                                    .child(filename)
                                    .putFile(uriArquivoUploadAtividadesDuvida)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String urlUploadedFile = taskSnapshot.getStorage().getPath();

                            duvidaAtividade.setUID(UUID.randomUUID().toString());
                            duvidaAtividade.setAluno(txtNomeAlunoAtividadeConcluidaDuvida.getText().toString());
                            duvidaAtividade.setTitulo(edtTituloAtividadeDuvida.getText().toString());
                            duvidaAtividade.setTurma(txtTurmaAlunoAtividadeConcluidaDuvida.getText().toString());
                            duvidaAtividade.setMateria(recebeMateriaDuvidaAtividadeProfessor);
                            duvidaAtividade.setDuvida(edtDescricaoDuvidaAtividadeConcluida.getText().toString());
                            duvidaAtividade.setDocumento(urlUploadedFile);

                            dbReferenceAtividadeDuvida.push().setValue(duvidaAtividade)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void aVoid) {
                                    informaUploadArquivo.setVisibility(View.GONE);
                                    Toast.makeText(vDuvidasAtividades.getContext(),
                                            R.string.sucesso_enviar_duvida_atividade_concluida,
                                            Toast.LENGTH_LONG).show();
                                    limparDados();
                                    habilitarComponentes();
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {
                                informaUploadArquivo.setVisibility(View.GONE);
                                Toast.makeText(vDuvidasAtividades.getContext(),
                                         R.string.falha_enviar_duvida_atividade_concluida,
                                         Toast.LENGTH_LONG).show();
                                habilitarComponentes();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(vDuvidasAtividades.getContext(),
                                     R.string.erro_upload_arquivo,
                                     Toast.LENGTH_LONG).show();
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
        if(requestCode == 9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            openFile();
        }else{
            Toast.makeText(vDuvidasAtividades.getContext(), R.string.permissao_leitura_storage,
                                                                                                        Toast.LENGTH_LONG).show();
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
            Toast.makeText(vDuvidasAtividades.getContext(), R.string.nenhum_arquivo_encontrado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_FILE_REQUESTCODE
                && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uriArquivoUploadAtividadesDuvida = resultData.getData();
                txtRecebeArquivoDuvidaAtividade.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());
            }
        }
    }

    public void limparDados(){
        edtTituloAtividadeDuvida.setText("");
        edtDescricaoDuvidaAtividadeConcluida.setText("");
        txtRecebeArquivoDuvidaAtividade.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void desabilitarComponentes(){
        txtNomeAlunoAtividadeConcluidaDuvida.setEnabled(false);
        txtTurmaAlunoAtividadeConcluidaDuvida.setEnabled(false);
        edtTituloAtividadeDuvida.setEnabled(false);
        edtDescricaoDuvidaAtividadeConcluida.setEnabled(false);
        spnMateriaProfessorAtividadeDuvida.setEnabled(false);
        btnProcurarAtividadeDuvidas.setEnabled(false);
        btnEnviarAtividadeDuvida.setEnabled(false);
        txtRecebeArquivoDuvidaAtividade.setEnabled(false);
    }

    public void habilitarComponentes(){
        txtNomeAlunoAtividadeConcluidaDuvida.setEnabled(true);
        txtTurmaAlunoAtividadeConcluidaDuvida.setEnabled(true);
        edtTituloAtividadeDuvida.setEnabled(true);
        edtDescricaoDuvidaAtividadeConcluida.setEnabled(true);
        spnMateriaProfessorAtividadeDuvida.setEnabled(true);
        btnProcurarAtividadeDuvidas.setEnabled(true);
        btnEnviarAtividadeDuvida.setEnabled(true);
        txtRecebeArquivoDuvidaAtividade.setEnabled(true);
    }
}