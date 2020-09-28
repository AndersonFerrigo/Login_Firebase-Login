package com.example.myapplication.content.aluno.ui.duvidas_conteudo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

public class DuvidasConteudoFragment extends Fragment {

    /**
     * @since 20/08/2020
     */

    private View vDuvidasConteudo;
    private View informaUploadArquivo;

    private String recebeMateriaDuvidaConteudoAluno;

    private TextView txtNomeAlunoDuvidaConteudo;
    private TextView txtTurmaAlunoDuvidaConteudo;
    private EditText edtTituloDuvidaConteudo;
    private EditText edtDescricaoDuvidaConteudo;
    private TextView txtRecebeArquivoDuvidaConteudo;

    private Button btnProcurarDuvidaConteudo;
    private Button btnEnviarDuvidaConteudo;

    private Spinner spnMateriaProfessorConteudoDuvida;

    private Uri uriArquivoUploadConteudoDuvida;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceConteudoDuvida;
    private FirebaseDatabase conteudoDuvidaDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EnviarDuvidaAtividadeConteudo duvidaConteudo;
    private CadastroNovoUsuario alunoUsuario;


    public DuvidasConteudoFragment(){ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vDuvidasConteudo = inflater.inflate(R.layout.duvidas_conteudo_fragment, container, false);

        txtNomeAlunoDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.txt_nome_aluno_duvida_conteudo);
        txtTurmaAlunoDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.txt_turma_aluno_duvida_conteudo);
        edtTituloDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.edt_titulo_duvida_conteudo);
        edtDescricaoDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.edt_descricao_duvidas_conteudo);
        txtRecebeArquivoDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.txt_recebe_arquivo_duvida_conteudo);
        spnMateriaProfessorConteudoDuvida = vDuvidasConteudo.findViewById(R.id.spn_materia_duvida_professor);
        btnProcurarDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.btn_procurar_conteudo_duvida);
        btnEnviarDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.btn_enviar_conteudo_duvida);
        informaUploadArquivo = vDuvidasConteudo.findViewById(R.id.progressbar_informa_upload);

        return vDuvidasConteudo;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(vDuvidasConteudo.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessorConteudoDuvida.setAdapter(adapterMateriaProfessor);

        dbReferenceConteudoDuvida = conteudoDuvidaDataBase.getInstance().getReference().child("duvidas_conteudo_aulas");
        storageReference = storage.getInstance().getReference();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle aluno = getActivity().getIntent().getExtras();
        if(aluno != null){
            alunoUsuario = aluno.getParcelable("aluno");
            txtNomeAlunoDuvidaConteudo.setText(alunoUsuario.getNome());
            txtTurmaAlunoDuvidaConteudo.setText(alunoUsuario.getTurma());
        }else {
            Toast.makeText(getContext(), R.string.erro_informacoes_aluno_bundle, Toast.LENGTH_LONG).show();
        }

        spnMateriaProfessorConteudoDuvida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                recebeMateriaDuvidaConteudoAluno = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        btnProcurarDuvidaConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(ContextCompat.checkSelfPermission(vDuvidasConteudo.getContext(),  Manifest.permission.READ_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED){
                    openFile();
                }else{
                    ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        btnEnviarDuvidaConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((txtNomeAlunoDuvidaConteudo.getText().toString().isEmpty())
                    || (txtTurmaAlunoDuvidaConteudo.getText().toString().isEmpty())
                    || (edtTituloDuvidaConteudo.getText().toString().equals(""))
                    ||(edtDescricaoDuvidaConteudo.getText().toString().equals(""))
                    || (recebeMateriaDuvidaConteudoAluno.equals("Escolha uma matéria"))
                    ||(txtRecebeArquivoDuvidaConteudo.getText().toString().equals(""))
                    || (uriArquivoUploadConteudoDuvida.equals(""))) {

                    Toast.makeText(getContext(), R.string.campos_obrigatorios_aluno, Toast.LENGTH_LONG).show();

                }else {
                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    duvidaConteudo = new EnviarDuvidaAtividadeConteudo();

                    String filename = System.currentTimeMillis() + "";
                    // Retorna O caminho raiz
                    storageReference.child("duvidas_conteudos_aulas_uploads")
                                    .child(filename)
                                    .putFile(uriArquivoUploadConteudoDuvida)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String urlUploadedFile = taskSnapshot.getStorage().getPath();
                            duvidaConteudo.setAluno(txtNomeAlunoDuvidaConteudo.getText().toString());
                            duvidaConteudo.setTurma(txtTurmaAlunoDuvidaConteudo.getText().toString());
                            duvidaConteudo.setTitulo(edtTituloDuvidaConteudo.getText().toString());
                            duvidaConteudo.setMateria(recebeMateriaDuvidaConteudoAluno);
                            duvidaConteudo.setDuvida(edtDescricaoDuvidaConteudo.getText().toString());
                            duvidaConteudo.setDocumento(urlUploadedFile);

                            dbReferenceConteudoDuvida.push().setValue(duvidaConteudo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    informaUploadArquivo.setVisibility(View.GONE);
                                    Toast.makeText(vDuvidasConteudo.getContext(),R.string.sucesso_enviar_duvida_conteudo,
                                                                                                           Toast.LENGTH_LONG).show();
                                    limparDados();
                                    habilitarComponentes();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    informaUploadArquivo.setVisibility(View.GONE);
                                    Toast.makeText(vDuvidasConteudo.getContext(), R.string.falha_enviar_duvida_conteudo, Toast.LENGTH_LONG).show();
                                    habilitarComponentes();
                                }
                            });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        informaUploadArquivo.setVisibility(View.GONE);
                        Toast.makeText(vDuvidasConteudo.getContext(), R.string.erro_upload_arquivo, Toast.LENGTH_LONG).show();
                        habilitarComponentes();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
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
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFile();
        } else {
            Toast.makeText(vDuvidasConteudo.getContext(), R.string.permissao_leitura_storage, Toast.LENGTH_LONG).show();
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
        if (getActivity().getPackageManager().resolveActivity(sIntent, 0) != null) {
            chooserIntent = Intent.createChooser(sIntent, "Abrir arquivo");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Abrir arquivo");
        }

        try {
            startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(vDuvidasConteudo.getContext(), R.string.nenhum_arquivo_encontrado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_FILE_REQUESTCODE
                && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uriArquivoUploadConteudoDuvida = resultData.getData();
                txtRecebeArquivoDuvidaConteudo.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());
            }
        }
    }

    public void limparDados() {
        edtTituloDuvidaConteudo.setText("");
        edtDescricaoDuvidaConteudo.setText("");
        txtRecebeArquivoDuvidaConteudo.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void desabilitarComponentes(){
        txtNomeAlunoDuvidaConteudo.setEnabled(false);
        txtTurmaAlunoDuvidaConteudo.setEnabled(false);
        edtTituloDuvidaConteudo.setEnabled(false);
        edtDescricaoDuvidaConteudo.setEnabled(false);
        spnMateriaProfessorConteudoDuvida.setEnabled(false);
        btnProcurarDuvidaConteudo.setEnabled(false);
        btnEnviarDuvidaConteudo.setEnabled(false);
        txtRecebeArquivoDuvidaConteudo.setEnabled(false);
        informaUploadArquivo.setEnabled(false);
    }

    public void habilitarComponentes(){
        txtNomeAlunoDuvidaConteudo.setEnabled(true);
        txtTurmaAlunoDuvidaConteudo.setEnabled(true);
        edtTituloDuvidaConteudo.setEnabled(true);
        edtDescricaoDuvidaConteudo.setEnabled(true);
        spnMateriaProfessorConteudoDuvida.setEnabled(true);
        btnProcurarDuvidaConteudo.setEnabled(true);
        btnEnviarDuvidaConteudo.setEnabled(true);
        txtRecebeArquivoDuvidaConteudo.setEnabled(true);
        informaUploadArquivo.setEnabled(true);
    }
}