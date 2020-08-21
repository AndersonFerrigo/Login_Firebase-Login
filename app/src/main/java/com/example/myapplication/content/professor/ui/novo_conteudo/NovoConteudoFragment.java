package com.example.myapplication.content.professor.ui.novo_conteudo;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.content.professor.ui.nova_atividades.NovaAtividadeViewModel;
import com.example.myapplication.model.CadastarAtividade;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class NovoConteudoFragment extends Fragment {

    /**
     * @since 2020
     */

    private View novoConteudo;
    private View informaUploadArquivo;

    private String recebeNomeProfessor;
    private String recebeTituloNovoConteudoProfessor;
    private String recebeTurmaNovoConteudoProfessor;
    private String recebeMateriaNovoConteudoProfessor;
    private String recebeDescricaoNovoConteudoProfessor;

    private EditText edtNomeProfessorConteudo;
    private EditText edtTituloNovoConteudo;
    private EditText edtDescricaoNovoConteudo;
    private TextView txtRecebeArquivoNovoConteudo;

    private Spinner spnEscolheTurmaNovoConteudo;
    private Spinner spnEscolheMateriaNovoConteudo;

    private Uri uriArquivoUploadNovoConteudo;

    private Button btnProcurarConteudo;
    private Button btnAdicionarNovoConteudo;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceCadastroNovoConteudo;
    private FirebaseDatabase cadastroNovoConteudo;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private NovoConteudoViewModel novoConteudoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        novoConteudo = inflater.inflate(R.layout.fragment_novo_conteudo, container, false);

        edtNomeProfessorConteudo = novoConteudo.findViewById(R.id.edt_nome_professor_novo_conteudo);
        edtTituloNovoConteudo = novoConteudo.findViewById(R.id.edt_titulo_cadastrar_conteudo);
        edtDescricaoNovoConteudo = novoConteudo.findViewById(R.id.edt_descrição_cadastrar_novo_conteudo);

        spnEscolheTurmaNovoConteudo = novoConteudo.findViewById(R.id.spin_turma_conteudo);
        spnEscolheMateriaNovoConteudo = novoConteudo.findViewById(R.id.spn_materia_professor);

        txtRecebeArquivoNovoConteudo = novoConteudo.findViewById(R.id.txt_receive_archive);

        btnProcurarConteudo = novoConteudo.findViewById(R.id.btn_procurar_cadastrar_conteudo);
        btnAdicionarNovoConteudo = novoConteudo.findViewById(R.id.btn_adicionar_novo_conteudo);

        informaUploadArquivo = novoConteudo.findViewById(R.id.progressbar_informa_upload);


        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(novoConteudo.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheTurmaNovoConteudo.setAdapter(turmasAdapter);

        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(novoConteudo.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheMateriaNovoConteudo.setAdapter(adapterMateriaProfessor);


        dbReferenceCadastroNovoConteudo = cadastroNovoConteudo.getInstance().getReference().child("conteudos_adicionados");
        storageReference = storage.getInstance().getReference();

        return novoConteudo;
    }

    @Override
    public void onResume() {
        super.onResume();

        spnEscolheTurmaNovoConteudo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                  recebeTurmaNovoConteudoProfessor = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spnEscolheMateriaNovoConteudo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                recebeMateriaNovoConteudoProfessor = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnProcurarConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(novoConteudo.getContext(),  Manifest.permission.READ_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED){
                    openFile();
                }else{
                    ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        btnAdicionarNovoConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recebeNomeProfessor = edtNomeProfessorConteudo.getText().toString();
                recebeTituloNovoConteudoProfessor = edtTituloNovoConteudo.getText().toString();
                recebeDescricaoNovoConteudoProfessor = edtDescricaoNovoConteudo.getText().toString();

                if((recebeNomeProfessor.isEmpty())|| (recebeTituloNovoConteudoProfessor.isEmpty())
                        || (recebeDescricaoNovoConteudoProfessor.isEmpty())
                        || (recebeTurmaNovoConteudoProfessor.equals("Escolha uma Turma"))
                        || (recebeMateriaNovoConteudoProfessor.equals("Escolha uma materia"))
                        || (uriArquivoUploadNovoConteudo.equals(""))) {

                    Toast.makeText(getContext(), "Todos os dados devem ser preenchidos", Toast.LENGTH_LONG).show();
                }else {
                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    novoConteudoViewModel = new NovoConteudoViewModel();

                    String filename = System.currentTimeMillis() + "";
                    // Retorna O caminho raiz
                    storageReference.child("ConteudosAdicionadosUploads").child(filename).putFile(uriArquivoUploadNovoConteudo)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    String urlUploadedFile = taskSnapshot.getMetadata().toString();

                                    novoConteudoViewModel.setNomeProfessor(recebeNomeProfessor);
                                    novoConteudoViewModel.setTituloNovoConteudoProfessor(recebeTituloNovoConteudoProfessor);
                                    novoConteudoViewModel.setTurmaProfessor(recebeTurmaNovoConteudoProfessor);
                                    novoConteudoViewModel.setMateriaProfessor(recebeMateriaNovoConteudoProfessor);
                                    novoConteudoViewModel.setDescriçãoNovoConteudoProfessor(recebeDescricaoNovoConteudoProfessor);
                                    novoConteudoViewModel.setPathDocumentoNovoConteudoProfessor(urlUploadedFile);

                                    //Salva novo usuario no firebase
                                    dbReferenceCadastroNovoConteudo.push().setValue(novoConteudoViewModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(novoConteudo.getContext(), "Conteudo cadastrado com sucesso", Toast.LENGTH_LONG).show();
                                                    limparDados();
                                                    habilitarComponentes();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(novoConteudo.getContext(), "Problemas ao realizar o cadastro", Toast.LENGTH_LONG).show();
                                                    habilitarComponentes();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(novoConteudo.getContext(), "Problemas ao realizar o upload do arquivo", Toast.LENGTH_LONG).show();
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
            Toast.makeText(novoConteudo.getContext(), "Necessário aceitar a permissão de leitura", Toast.LENGTH_LONG).show();
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
            Toast.makeText(novoConteudo.getContext(), "Nenhum arquivo encontrado.", Toast.LENGTH_SHORT).show();
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
                uriArquivoUploadNovoConteudo = resultData.getData();

                // Perform operations on the document using its URI.
                //        Toast.makeText(getApplicationContext(), "Caminho do arquivo = " + uri, Toast.LENGTH_LONG).show();
                 txtRecebeArquivoNovoConteudo.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());

            }
        }
    }

    public void limparDados(){
        edtNomeProfessorConteudo.setText("");
        edtTituloNovoConteudo.setText("");
        edtDescricaoNovoConteudo.setText("");
        txtRecebeArquivoNovoConteudo.setText("");
        spnEscolheTurmaNovoConteudo.getItemIdAtPosition(0);
    }

    public void desabilitarComponentes(){
        edtNomeProfessorConteudo.setEnabled(false);
        edtTituloNovoConteudo.setEnabled(false);
        edtDescricaoNovoConteudo.setEnabled(false);
        txtRecebeArquivoNovoConteudo.setEnabled(false);
        spnEscolheTurmaNovoConteudo.setEnabled(false);
        spnEscolheMateriaNovoConteudo.setEnabled(false);
        btnProcurarConteudo.setEnabled(false);
        btnAdicionarNovoConteudo.setEnabled(false);
    }

    public void habilitarComponentes(){
        edtNomeProfessorConteudo.setEnabled(true);
        edtTituloNovoConteudo.setEnabled(true);
        edtDescricaoNovoConteudo.setEnabled(true);
        txtRecebeArquivoNovoConteudo.setEnabled(true);
        spnEscolheTurmaNovoConteudo.setEnabled(true);
        spnEscolheMateriaNovoConteudo.setEnabled(true);
        btnProcurarConteudo.setEnabled(true);
        btnAdicionarNovoConteudo.setEnabled(true);
    }
}