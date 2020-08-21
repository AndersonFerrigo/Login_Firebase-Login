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
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EnviarAtividadesConcluidasFragment extends Fragment {
    /**
     * @since 2020
     */

    private View atividadeConcluida;
    private View informaUploadArquivo;

    private String recebeTurmaSelecionada;
    private String recebeTituloAtividadeConcluida;
    private String recebemateriaSelecionada;
    private String recebeNomeAluno;

    private TextView recebeArquivoAtividadeConcluida;
    private EditText nomeAlunoAtividadeConcluida;
    private EditText tituloAtividadeConcluida;

    private Spinner escolherTurma;
    private Spinner spnMateriaAtividadeConcluida;

    private Button btnProcurarAtividadeConcluida;
    private Button btnEnviarAtividadeConcluida;

    private Uri uriArquivoUploadAtividadesConcluidas;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceAtividadeConcluida;
    private FirebaseDatabase atividadeConcluidaDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EnviarAtividadesConcluidasViewModel mViewModelAtividadeConcluida;

    public static EnviarAtividadesConcluidasFragment newInstance() {
        return new EnviarAtividadesConcluidasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        atividadeConcluida =  inflater.inflate(R.layout.enviar_atividades_concluidas_fragment, container, false);

        nomeAlunoAtividadeConcluida = atividadeConcluida.findViewById(R.id.edt_nome_aluno_atividade_concluida);
        spnMateriaAtividadeConcluida = atividadeConcluida.findViewById(R.id.spn_materia_professor);
        escolherTurma = atividadeConcluida.findViewById(R.id.spin_turma_atividade);
        tituloAtividadeConcluida = atividadeConcluida.findViewById(R.id.edt_titulo_atividade_concluida);
        btnProcurarAtividadeConcluida = atividadeConcluida.findViewById(R.id.btn_procurar_atividade_concluida);
        btnEnviarAtividadeConcluida = atividadeConcluida.findViewById(R.id.btn_enviar_atividade_concluida);
        recebeArquivoAtividadeConcluida = atividadeConcluida.findViewById(R.id.txt_recebe_arquivo_ativ_concluida_);
        informaUploadArquivo = atividadeConcluida.findViewById(R.id.progressbar_informa_upload);

        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(atividadeConcluida.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        escolherTurma.setAdapter(turmasAdapter);

        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(atividadeConcluida.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaAtividadeConcluida.setAdapter(adapterMateriaProfessor);

        dbReferenceAtividadeConcluida = atividadeConcluidaDataBase.getInstance().getReference().child("atividadesConcluidas");
        storageReference = storage.getInstance().getReference();

        return atividadeConcluida;
    }

    @Override
    public void onResume() {
        super.onResume();
        escolherTurma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                recebeTurmaSelecionada = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

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
                recebeNomeAluno = nomeAlunoAtividadeConcluida.getText().toString();
                recebeTituloAtividadeConcluida = tituloAtividadeConcluida.getText().toString();

                if((recebeNomeAluno.equals(""))|| (recebeTituloAtividadeConcluida.equals(""))
                        || (recebemateriaSelecionada.equals("Escolha uma materia"))
                        || (recebeTurmaSelecionada.equals("Escolha uma turma"))
                        ||(uriArquivoUploadAtividadesConcluidas.equals(""))) {

                    Toast.makeText(getContext(),"Todos os dados devem ser preenchidos", Toast.LENGTH_LONG).show();
                }else {
                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    mViewModelAtividadeConcluida = new EnviarAtividadesConcluidasViewModel();
                    String filename = System.currentTimeMillis() + "";
                    // Retorna o caminho raiz
                    storageReference.child("AtividadesConcluidasUploads").child(filename).putFile(uriArquivoUploadAtividadesConcluidas)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    String urlUploadedFile = taskSnapshot.getMetadata().toString();

                                    mViewModelAtividadeConcluida.setNomeAluno(recebeNomeAluno);
                                    mViewModelAtividadeConcluida.setTituloAtividadeConcluidaAluno(recebeTituloAtividadeConcluida);
                                    mViewModelAtividadeConcluida.setMateriaAluno(recebemateriaSelecionada);
                                    mViewModelAtividadeConcluida.setTurmaAluno(recebeTurmaSelecionada);
                                    mViewModelAtividadeConcluida.setPathDocumentoAtividadeConcluidaAluno(urlUploadedFile);

                                    //Salva novo usuario no firebase
                                    dbReferenceAtividadeConcluida.push().setValue(mViewModelAtividadeConcluida)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(atividadeConcluida.getContext(), "Atividade enviada com sucesso", Toast.LENGTH_LONG).show();
                                                    limparDados();
                                                    habilitarComponentes();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(atividadeConcluida.getContext(), "Problemas ao enviar atividade", Toast.LENGTH_LONG).show();
                                                    habilitarComponentes();
                                                }
                                            });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(atividadeConcluida.getContext(), "Problemas ao realizar o upload do arquivo", Toast.LENGTH_LONG).show();
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
            Toast.makeText(atividadeConcluida.getContext(), "Necessário aceitar a permissão de leitura", Toast.LENGTH_LONG).show();
        }
    }

    public void openFile() {
        String mimeType = "*/*";
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // special intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        // if you want any file type, you can skip next line
        sIntent.putExtra("CONTENT_TYPE", mimeType);
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getActivity().getPackageManager().resolveActivity(sIntent, 0) != null){
            // it is device with Samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Abrir arquivo");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Abrir arquivo");
        }

        try {
            startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(atividadeConcluida.getContext(), "Nenhum arquivo encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_FILE_REQUESTCODE
                && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            if (resultData != null) {
                uriArquivoUploadAtividadesConcluidas = resultData.getData();
                // Perform operations on the document using its URI.
                //        Toast.makeText(getApplicationContext(), "Caminho do arquivo = " + uri, Toast.LENGTH_LONG).show();
                recebeArquivoAtividadeConcluida.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());
            }
        }
    }

    public void limparDados(){
        nomeAlunoAtividadeConcluida.setText("");
        tituloAtividadeConcluida.setText("");
        recebeArquivoAtividadeConcluida.setText("");
    }

    public void desabilitarComponentes(){

        nomeAlunoAtividadeConcluida.setEnabled(false);
        spnMateriaAtividadeConcluida.setEnabled(false);
        escolherTurma.setEnabled(false);
        tituloAtividadeConcluida.setEnabled(false);
        btnProcurarAtividadeConcluida.setEnabled(false);
        btnEnviarAtividadeConcluida.setEnabled(false);
        recebeArquivoAtividadeConcluida.setEnabled(false);
        informaUploadArquivo.setEnabled(false);

    }

    public void habilitarComponentes(){

        nomeAlunoAtividadeConcluida.setEnabled(true);
        spnMateriaAtividadeConcluida.setEnabled(true);
        escolherTurma.setEnabled(true);
        tituloAtividadeConcluida.setEnabled(true);
        btnProcurarAtividadeConcluida.setEnabled(true);
        btnEnviarAtividadeConcluida.setEnabled(true);
        recebeArquivoAtividadeConcluida.setEnabled(true);
        informaUploadArquivo.setEnabled(true);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModelAtividadeConcluida = ViewModelProviders.of(this).get(EnviarAtividadesConcluidasViewModel.class);
        // TODO: Use the ViewModel
    }
}