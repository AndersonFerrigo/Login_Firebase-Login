package com.example.myapplication.content.aluno.ui.duvidas_conteudo;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.ui.duvidas_atividade.DuvidasAtividadesConcluidasViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DuvidasConteudoFragment extends Fragment {

    View vDuvidasConteudo;

    ProgressBar simpleProgressBar;
    String recebeTurmaSelecionada;
    TextView recebeArquivoAtividadeConcluida;
    Spinner escolherTurma;
    private Spinner spnMateriaProfessor;
    EditText tituloAtividadeConcluida;
    Button btnProcurarAtividadeConcluida;
    Button btnEnviarAtividadeConcluida;

    ProgressBar progressDialog;

    Uri uriArquivoUploadAtividadesConcluidas;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceAtividadeConcluida;
    private FirebaseDatabase atividadeConcluidaDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;



    private DuvidasConteudoViewModel mVMDuvidasConteudo;

    public static DuvidasConteudoFragment newInstance() {
        return new DuvidasConteudoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         vDuvidasConteudo = inflater.inflate(R.layout.duvidas_conteudo_fragment, container, false);


        spnMateriaProfessor = vDuvidasConteudo.findViewById(R.id.spn_materia_professor);
        escolherTurma = vDuvidasConteudo.findViewById(R.id.spin_turma_atividade);
        tituloAtividadeConcluida = vDuvidasConteudo.findViewById(R.id.edt_titulo_atividade_concluida);
        btnProcurarAtividadeConcluida = vDuvidasConteudo.findViewById(R.id.btn_procurar_atividade_concluida);
        btnEnviarAtividadeConcluida = vDuvidasConteudo.findViewById(R.id.btn_enviar_atividade_concluida);
        recebeArquivoAtividadeConcluida = vDuvidasConteudo.findViewById(R.id.txt_recebe_arquivo_ativ_concluida_);
        simpleProgressBar = vDuvidasConteudo.findViewById(R.id.simpleProgressBar);

        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(vDuvidasConteudo.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        escolherTurma.setAdapter(turmasAdapter);

        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(vDuvidasConteudo.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);


        dbReferenceAtividadeConcluida = atividadeConcluidaDataBase.getInstance().getReference().child("atividadesConcluidas");
        storageReference = storage.getInstance().getReference();


        return vDuvidasConteudo;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            openFile();
        }else{
            Toast.makeText(vDuvidasConteudo.getContext(), "Necessário aceitar a permissão de leitura", Toast.LENGTH_LONG).show();
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
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Abrir arquivo");
        }

        try {
            startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(vDuvidasConteudo.getContext(), "Nenhum arquivo encontrado.", Toast.LENGTH_SHORT).show();
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
        tituloAtividadeConcluida.setText("");
        recebeArquivoAtividadeConcluida.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mVMDuvidasConteudo = ViewModelProviders.of(this).get(DuvidasConteudoViewModel.class);
        // TODO: Use the ViewModel
        escolherTurma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(adapterView.getItemAtPosition(position).equals(0)){
                    Toast.makeText(vDuvidasConteudo.getContext(), "Escolha uma turma", Toast.LENGTH_LONG).show();

                }else{
                    recebeTurmaSelecionada = adapterView.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        btnProcurarAtividadeConcluida.setOnClickListener(new View.OnClickListener() {
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

        btnEnviarAtividadeConcluida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleProgressBar.setVisibility(View.VISIBLE);

                mVMDuvidasConteudo = new DuvidasConteudoViewModel();

                String filename = System.currentTimeMillis() + "";
                // Retorna O caminho raiz
                storageReference.child("AtividadesConcluidasUploads").child(filename).putFile(uriArquivoUploadAtividadesConcluidas)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String urlUploadedFile = taskSnapshot.getMetadata().toString();

                                mVMDuvidasConteudo.setTurma(recebeTurmaSelecionada);
                                mVMDuvidasConteudo.setTituloAtividadeConcluida(tituloAtividadeConcluida.getText().toString());
                                mVMDuvidasConteudo.setPathDocumentoAtividadeConcluida(urlUploadedFile);

                                //Salva novo usuario no firebase
                                dbReferenceAtividadeConcluida.push().setValue(mVMDuvidasConteudo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                simpleProgressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(vDuvidasConteudo.getContext(), "Atividade enviada com sucesso", Toast.LENGTH_LONG).show();
                                                limparDados();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                simpleProgressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(vDuvidasConteudo.getContext(), "Problemas ao enviar atividade", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        simpleProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(vDuvidasConteudo.getContext(), "Problemas ao realizar o upload do arquivo", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        simpleProgressBar.setVisibility(View.VISIBLE);
                    }
                });


            }


        });
    }

}