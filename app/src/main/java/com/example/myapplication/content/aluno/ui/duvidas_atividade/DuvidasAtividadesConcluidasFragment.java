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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DuvidasAtividadesConcluidasFragment extends Fragment {

    private View vDuvidasAtividades;
    private View informaUploadArquivo;

    private String recebeNomeDuvidaAtividadeProfessor;
    private String recebeTituloDuvidaAtividadeProfessor;
    private String recebeTurmaDuvidaAtividadeProfessor;
    private String recebeMateriaDuvidaAtividadeProfessor;
    private String recebeDescricaoDuvidaAtividadeProfessor;


    private EditText edtNomeProfessorAtividade;
    private EditText edtTituloAtividadeDuvida;
    private EditText edtDescricaoNovaAtividade;
    private TextView txtRecebeArquivoDuvidaAtividade;

    private Button btnProcurarAtividadeDuvidas;
    private Button btnEnviarAtividadeDuvida;


    private Spinner spnEscolheTurmaAtividadeDuvida;
    private Spinner spnMateriaProfessorAtividadeDuvida;

    private Uri uriArquivoUploadAtividadesDuvida;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceAtividadeDuvida;
    private FirebaseDatabase atividadeDuvidaDataBase;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    private DuvidasAtividadesConcluidasViewModel mViewModeDuvidasAtividade;

    public static DuvidasAtividadesConcluidasFragment newInstance() {
        return new DuvidasAtividadesConcluidasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vDuvidasAtividades =  inflater.inflate(R.layout.duvidas_atividades_concluidas_fragment, container, false);

        edtNomeProfessorAtividade = vDuvidasAtividades.findViewById(R.id.edt_nome_professor_duvida_atividade);
        edtTituloAtividadeDuvida = vDuvidasAtividades.findViewById(R.id.edt_titulo_atividade_concluida);
        edtDescricaoNovaAtividade = vDuvidasAtividades.findViewById(R.id.edt_duvidas_atividades_enviada);

        spnEscolheTurmaAtividadeDuvida = vDuvidasAtividades.findViewById(R.id.spin_turma_atividade);
        spnMateriaProfessorAtividadeDuvida = vDuvidasAtividades.findViewById(R.id.spn_materia_professor);

        btnProcurarAtividadeDuvidas = vDuvidasAtividades.findViewById(R.id.btn_procurar_atividade_duvida);
        btnEnviarAtividadeDuvida = vDuvidasAtividades.findViewById(R.id.btn_enviar_atividade_duvida);


        txtRecebeArquivoDuvidaAtividade = vDuvidasAtividades.findViewById(R.id.txt_recebe_arquivo_ativ_concluida_);
        informaUploadArquivo = vDuvidasAtividades.findViewById(R.id.progressbar_informa_upload);


        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(vDuvidasAtividades.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheTurmaAtividadeDuvida.setAdapter(turmasAdapter);

        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(vDuvidasAtividades.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessorAtividadeDuvida.setAdapter(adapterMateriaProfessor);


        dbReferenceAtividadeDuvida = atividadeDuvidaDataBase.getInstance().getReference().child("duvidasAtividadesConcluidas");
        storageReference = storage.getInstance().getReference();


        return vDuvidasAtividades;
    }

    @Override
    public void onResume() {
        super.onResume();

        spnEscolheTurmaAtividadeDuvida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    recebeTurmaDuvidaAtividadeProfessor = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

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
                recebeNomeDuvidaAtividadeProfessor = edtNomeProfessorAtividade.getText().toString();
                recebeTituloDuvidaAtividadeProfessor = edtTituloAtividadeDuvida.getText().toString();
                recebeDescricaoDuvidaAtividadeProfessor = edtDescricaoNovaAtividade.getText().toString();

                if((recebeNomeDuvidaAtividadeProfessor.isEmpty())|| (recebeTituloDuvidaAtividadeProfessor.isEmpty())
                        || (recebeDescricaoDuvidaAtividadeProfessor.isEmpty())
                        || (recebeTurmaDuvidaAtividadeProfessor.equals("Escolha uma Turma"))
                        || (recebeMateriaDuvidaAtividadeProfessor.equals("Escolha uma materia"))
                        || (uriArquivoUploadAtividadesDuvida.equals(""))) {

                    Toast.makeText(getContext(), "Todos os dados devem ser preenchidos", Toast.LENGTH_LONG).show();

                }else {

                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    mViewModeDuvidasAtividade = new DuvidasAtividadesConcluidasViewModel();

                    String filename = System.currentTimeMillis() + "";
                    // Retorna O caminho raiz
                    storageReference.child("DuvidasAtividadesConcluidasUploads").child(filename).putFile(uriArquivoUploadAtividadesDuvida)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String urlUploadedFile = taskSnapshot.getMetadata().toString();

                                    mViewModeDuvidasAtividade.setNomeProfessorAtividadeDuvida(recebeNomeDuvidaAtividadeProfessor);
                                    mViewModeDuvidasAtividade.setTituloDuvidaAtividade(recebeTituloDuvidaAtividadeProfessor);
                                    mViewModeDuvidasAtividade.setTurmaProfessorAtividadeDuvida(recebeTurmaDuvidaAtividadeProfessor);
                                    mViewModeDuvidasAtividade.setMateriaProfessorAtividadeDuvida(recebeMateriaDuvidaAtividadeProfessor);
                                    mViewModeDuvidasAtividade.setDescricaoAtividadeDuvida(recebeDescricaoDuvidaAtividadeProfessor);
                                    mViewModeDuvidasAtividade.setPathDocumentoAtividadeDuvida(urlUploadedFile);

                                    //Salva novo usuario no firebase
                                    dbReferenceAtividadeDuvida.push().setValue(mViewModeDuvidasAtividade)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(vDuvidasAtividades.getContext(), "Atividade enviada com sucesso", Toast.LENGTH_LONG).show();
                                                    limparDados();
                                                    habilitarComponentes();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(vDuvidasAtividades.getContext(), "Problemas ao enviar atividade", Toast.LENGTH_LONG).show();
                                                    habilitarComponentes();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(vDuvidasAtividades.getContext(), "Problemas ao realizar o upload do arquivo", Toast.LENGTH_LONG).show();
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
            Toast.makeText(vDuvidasAtividades.getContext(), "Necessário aceitar a permissão de leitura", Toast.LENGTH_LONG).show();
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
            Toast.makeText(vDuvidasAtividades.getContext(), "Nenhum arquivo encontrado.", Toast.LENGTH_SHORT).show();
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
                uriArquivoUploadAtividadesDuvida = resultData.getData();

                // Perform operations on the document using its URI.
                //        Toast.makeText(getApplicationContext(), "Caminho do arquivo = " + uri, Toast.LENGTH_LONG).show();
                txtRecebeArquivoDuvidaAtividade.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());

            }
        }
    }

    public void limparDados(){
        edtNomeProfessorAtividade.setText("");
        edtTituloAtividadeDuvida.setText("");
        edtDescricaoNovaAtividade.setText("");
        txtRecebeArquivoDuvidaAtividade.setText("");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModeDuvidasAtividade = ViewModelProviders.of(this).get(DuvidasAtividadesConcluidasViewModel.class);
        // TODO: Use the ViewModel
    }

    public void desabilitarComponentes(){
        edtNomeProfessorAtividade.setEnabled(false);
        edtTituloAtividadeDuvida.setEnabled(false);
        edtDescricaoNovaAtividade.setEnabled(false);
        spnEscolheTurmaAtividadeDuvida.setEnabled(false);
        spnMateriaProfessorAtividadeDuvida.setEnabled(false);
        btnProcurarAtividadeDuvidas.setEnabled(false);
        btnEnviarAtividadeDuvida.setEnabled(false);
        txtRecebeArquivoDuvidaAtividade.setEnabled(false);
    }

    public void habilitarComponentes(){
        edtNomeProfessorAtividade.setEnabled(true);
        edtTituloAtividadeDuvida.setEnabled(true);
        edtDescricaoNovaAtividade.setEnabled(true);
        spnEscolheTurmaAtividadeDuvida.setEnabled(true);
        spnMateriaProfessorAtividadeDuvida.setEnabled(true);
        btnProcurarAtividadeDuvidas.setEnabled(true);
        btnEnviarAtividadeDuvida.setEnabled(true);
        txtRecebeArquivoDuvidaAtividade.setEnabled(true);
    }
}