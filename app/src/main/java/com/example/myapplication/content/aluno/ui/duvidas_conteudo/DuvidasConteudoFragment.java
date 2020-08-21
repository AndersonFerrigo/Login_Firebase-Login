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

    /**
     * @since 20/08/2020
     */

    private View vDuvidasConteudo;
    private View informaUploadArquivo;

    private String recebeNomeDuvidaConteudoAluno;
    private String recebeTituloDuvidaConteudoAluno;
    private String recebeTurmaDuvidaConteudoAluno;
    private String recebeMateriaDuvidaConteudoAluno;
    private String recebeDescricaoDuvidaConteudoAluno;


    private EditText edtNomeAlunoDuvidaConteudo;
    private EditText edtTituloDuvidaConteudo;
    private EditText edtDescricaoDuvidaConteudo;
    private TextView txtRecebeArquivoDuvidaConteudo;

    private Button btnProcurarDuvidaConteudo;
    private Button btnEnviarDuvidaConteudo;


    private Spinner spnEscolheTurmaConteudoDuvida;
    private Spinner spnMateriaProfessorConteudoDuvida;

    private Uri uriArquivoUploadConteudoDuvida;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceConteudoDuvida;
    private FirebaseDatabase conteudoDuvidaDataBase;
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

        edtNomeAlunoDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.nome_do_aluno_conteudo_duvida);
        edtTituloDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.titulo_conteudo_duvida_aluno);
        edtDescricaoDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.edt_descricao_duvidas_conteudo);

        spnEscolheTurmaConteudoDuvida = vDuvidasConteudo.findViewById(R.id.spn_turma_conteudo);
        spnMateriaProfessorConteudoDuvida = vDuvidasConteudo.findViewById(R.id.spn_materia_duvida_professor);

        btnProcurarDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.btn_procurar_conteudo_duvida);
        btnEnviarDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.btn_enviar_conteudo_duvida);

        txtRecebeArquivoDuvidaConteudo = vDuvidasConteudo.findViewById(R.id.txt_recebe_arquivo_duvida_conteudo);
        informaUploadArquivo = vDuvidasConteudo.findViewById(R.id.progressbar_informa_upload);


        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(vDuvidasConteudo.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheTurmaConteudoDuvida.setAdapter(turmasAdapter);

        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(vDuvidasConteudo.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessorConteudoDuvida.setAdapter(adapterMateriaProfessor);

        dbReferenceConteudoDuvida = conteudoDuvidaDataBase.getInstance().getReference().child("duvidasConteudoAulas");
        storageReference = storage.getInstance().getReference();


        return vDuvidasConteudo;
    }

    @Override
    public void onResume() {
        super.onResume();

        spnEscolheTurmaConteudoDuvida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                recebeTurmaDuvidaConteudoAluno = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

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

                recebeNomeDuvidaConteudoAluno = edtNomeAlunoDuvidaConteudo.getText().toString();
                recebeTituloDuvidaConteudoAluno = edtTituloDuvidaConteudo.getText().toString();
                recebeDescricaoDuvidaConteudoAluno = edtDescricaoDuvidaConteudo.getText().toString();

                if((recebeNomeDuvidaConteudoAluno.isEmpty())|| (recebeTituloDuvidaConteudoAluno.isEmpty())
                        || (recebeDescricaoDuvidaConteudoAluno.isEmpty())
                        || (recebeTurmaDuvidaConteudoAluno.equals("Escolha uma Turma"))
                        || (recebeMateriaDuvidaConteudoAluno.equals("Escolha uma materia"))
                        || (uriArquivoUploadConteudoDuvida.equals(""))) {

                    Toast.makeText(getContext(), "Todos os dados devem ser preenchidos", Toast.LENGTH_LONG).show();

                }else {
                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    mVMDuvidasConteudo = new DuvidasConteudoViewModel();

                    String filename = System.currentTimeMillis() + "";
                    // Retorna O caminho raiz
                    storageReference.child("duvidasConteudosAulasUploads").child(filename).putFile(uriArquivoUploadConteudoDuvida)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String urlUploadedFile = taskSnapshot.getMetadata().toString();

                                    mVMDuvidasConteudo.setNomeAlunoConteudoDuvida(recebeNomeDuvidaConteudoAluno);
                                    mVMDuvidasConteudo.setTituloDuvidaConteudo(recebeTituloDuvidaConteudoAluno);
                                    mVMDuvidasConteudo.setTurmaProfessorConteudoDuvida(recebeTurmaDuvidaConteudoAluno);
                                    mVMDuvidasConteudo.setMateriaProfessorConteudoDuvida(recebeMateriaDuvidaConteudoAluno);
                                    mVMDuvidasConteudo.setDescricaoConteudoDuvida(recebeDescricaoDuvidaConteudoAluno);
                                    mVMDuvidasConteudo.setPathDocumentoConteudoDuvida(urlUploadedFile);

                                    //Salva novo usuario no firebase
                                    dbReferenceConteudoDuvida.push().setValue(mVMDuvidasConteudo)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(vDuvidasConteudo.getContext(), "Atividade enviada com sucesso", Toast.LENGTH_LONG).show();
                                                    limparDados();
                                                    habilitarComponentes();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(vDuvidasConteudo.getContext(), "Problemas ao enviar atividade", Toast.LENGTH_LONG).show();
                                                    habilitarComponentes();
                                                }
                                            });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(vDuvidasConteudo.getContext(), "Problemas ao realizar o upload do arquivo", Toast.LENGTH_LONG).show();
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
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFile();
        } else {
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
        if (getActivity().getPackageManager().resolveActivity(sIntent, 0) != null) {
            // it is device with Samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Abrir arquivo");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
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
                uriArquivoUploadConteudoDuvida = resultData.getData();

                // Perform operations on the document using its URI.
                //        Toast.makeText(getApplicationContext(), "Caminho do arquivo = " + uri, Toast.LENGTH_LONG).show();
                txtRecebeArquivoDuvidaConteudo.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());

            }
        }
    }

    public void limparDados() {
        edtNomeAlunoDuvidaConteudo.setText("");
        edtTituloDuvidaConteudo.setText("");
        edtDescricaoDuvidaConteudo.setText("");
        txtRecebeArquivoDuvidaConteudo.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mVMDuvidasConteudo = ViewModelProviders.of(this).get(DuvidasConteudoViewModel.class);
        // TODO: Use the ViewModel
    }

    public void desabilitarComponentes(){

        edtNomeAlunoDuvidaConteudo.setEnabled(false);
        edtTituloDuvidaConteudo.setEnabled(false);
        edtDescricaoDuvidaConteudo.setEnabled(false);
        spnEscolheTurmaConteudoDuvida.setEnabled(false);
        spnMateriaProfessorConteudoDuvida.setEnabled(false);
        btnProcurarDuvidaConteudo.setEnabled(false);
        btnEnviarDuvidaConteudo.setEnabled(false);
        txtRecebeArquivoDuvidaConteudo.setEnabled(false);
        informaUploadArquivo.setEnabled(false);

    }

    public void habilitarComponentes(){

        edtNomeAlunoDuvidaConteudo.setEnabled(true);
        edtTituloDuvidaConteudo.setEnabled(true);
        edtDescricaoDuvidaConteudo.setEnabled(true);
        spnEscolheTurmaConteudoDuvida.setEnabled(true);
        spnMateriaProfessorConteudoDuvida.setEnabled(true);
        btnProcurarDuvidaConteudo.setEnabled(true);
        btnEnviarDuvidaConteudo.setEnabled(true);
        txtRecebeArquivoDuvidaConteudo.setEnabled(true);
        informaUploadArquivo.setEnabled(true);

    }
}