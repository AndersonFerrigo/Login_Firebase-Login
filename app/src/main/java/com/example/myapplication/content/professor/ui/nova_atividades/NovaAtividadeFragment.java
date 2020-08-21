package com.example.myapplication.content.professor.ui.nova_atividades;

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
import com.example.myapplication.model.CadastarAtividade;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NovaAtividadeFragment extends Fragment {

    /**
     * @since 2020
     */

    private View novaAtividade;
    private View informaUploadArquivo;

    private String recebeNomeProfessor;
    private String recebeTituloNovaAtividadeProfessor;
    private String recebeTurmaNovaAtividadeProfessor;
    private String recebeMateriaNovaAtividadeProfessor;
    private String recebeDescricaoNovaAtividadeProfessor;


    private EditText edtNomeProfessorAtividade;
    private EditText edtTituloNovaAtividade;
    private EditText edtDescricaoNovaAtividade;
    private TextView txtRecebeArquivoNovaAtividade;

    private Spinner spnEscolheTurmaNovaAtividade;
    private Spinner spnEscolheMateriaNovaAtividade;

    private Button procurarAtividade;
    private Button adicionarAtividade;

    private Uri uriArquivoUploadNovaAtividade;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceCadastroNovaAtividade;
    private FirebaseDatabase cadastroNovaAtividade;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private NovaAtividadeViewModel novaAtividadeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        novaAtividade = inflater.inflate(R.layout.fragment_nova_atividade, container, false);

        edtNomeProfessorAtividade = novaAtividade.findViewById(R.id.edt_nome_professor_nova_atividade);
        edtTituloNovaAtividade = novaAtividade.findViewById(R.id.edt_titulo_cadastrar_atividade);
        edtDescricaoNovaAtividade = novaAtividade.findViewById(R.id.edt_conteudo_cadastrar_atividade);

        spnEscolheTurmaNovaAtividade = novaAtividade.findViewById(R.id.spin_turma_atividade);
        spnEscolheMateriaNovaAtividade = novaAtividade.findViewById(R.id.spn_materia_professor);

        procurarAtividade = novaAtividade.findViewById(R.id.btn_procurar_cadastrar_atividade);
        adicionarAtividade = novaAtividade.findViewById(R.id.btn_adicionar_nova_atividade);

        txtRecebeArquivoNovaAtividade = novaAtividade.findViewById(R.id.txt_receive_archive);
        informaUploadArquivo = novaAtividade.findViewById(R.id.progressbar_informa_upload);


        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(novaAtividade.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheTurmaNovaAtividade.setAdapter(turmasAdapter);

        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(novaAtividade.getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheMateriaNovaAtividade.setAdapter(adapterMateriaProfessor);


        dbReferenceCadastroNovaAtividade = cadastroNovaAtividade.getInstance().getReference().child("atividades_adicionadas");
        storageReference = storage.getInstance().getReference();

        return novaAtividade;
    }

    @Override
    public void onResume() {
        super.onResume();

        spnEscolheTurmaNovaAtividade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                  recebeTurmaNovaAtividadeProfessor = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spnEscolheMateriaNovaAtividade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                recebeMateriaNovaAtividadeProfessor = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        procurarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(novaAtividade.getContext(),  Manifest.permission.READ_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED){
                    openFile();
                }else{
                    ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });


        adicionarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recebeNomeProfessor = edtNomeProfessorAtividade.getText().toString();
                recebeTituloNovaAtividadeProfessor = edtTituloNovaAtividade.getText().toString();
                recebeDescricaoNovaAtividadeProfessor = edtDescricaoNovaAtividade.getText().toString();

                if((recebeNomeProfessor.isEmpty())|| (recebeTituloNovaAtividadeProfessor.isEmpty())
                        || (recebeDescricaoNovaAtividadeProfessor.isEmpty())
                        || (recebeTurmaNovaAtividadeProfessor.equals("Escolha uma Turma"))
                        || (recebeMateriaNovaAtividadeProfessor.equals("Escolha uma materia"))
                        || (uriArquivoUploadNovaAtividade.equals(""))){

                    Toast.makeText(getContext(),"Todos os dados devem ser preenchidos", Toast.LENGTH_LONG).show();

                }else {
                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    novaAtividadeViewModel = new NovaAtividadeViewModel();

                    String filename = System.currentTimeMillis() + "";
                    // Retorna o caminho raiz
                    storageReference.child("AtividadesAdicionadasUploads").child(filename).putFile(uriArquivoUploadNovaAtividade)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    String urlUploadedFile = taskSnapshot.getMetadata().toString();

                                    novaAtividadeViewModel.setNomeProfessor(recebeNomeProfessor);
                                    novaAtividadeViewModel.setTituloNovaAtividadeProfessor(recebeTituloNovaAtividadeProfessor);
                                    novaAtividadeViewModel.setTurmaProfessor(recebeTurmaNovaAtividadeProfessor);
                                    novaAtividadeViewModel.setMateriaProfessor(recebeMateriaNovaAtividadeProfessor);
                                    novaAtividadeViewModel.setDescriçãoNovaAtividadeProfessor(recebeDescricaoNovaAtividadeProfessor);
                                    novaAtividadeViewModel.setPathDocumentoNovaAtividadeProfessor(urlUploadedFile);

                                    //Salva novo usuario no firebase
                                    dbReferenceCadastroNovaAtividade.push().setValue(novaAtividadeViewModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(novaAtividade.getContext(), "Atividade cadastrada com sucesso", Toast.LENGTH_LONG).show();
                                                    limparDados();
                                                    habilitarComponentes();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    informaUploadArquivo.setVisibility(View.GONE);
                                                    Toast.makeText(novaAtividade.getContext(), "Problemas ao realizar o cadastro", Toast.LENGTH_LONG).show();
                                                    habilitarComponentes();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(novaAtividade.getContext(), "Problemas ao realizar o upload do arquivo", Toast.LENGTH_LONG).show();
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
            Toast.makeText(novaAtividade.getContext(), "Necessário aceitar a permissão de leitura", Toast.LENGTH_LONG).show();
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
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(novaAtividade.getContext(), "Nenhum arquivo encontrado.", Toast.LENGTH_SHORT).show();
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
                uriArquivoUploadNovaAtividade = resultData.getData();

                // Perform operations on the document using its URI.
                //        Toast.makeText(getApplicationContext(), "Caminho do arquivo = " + uri, Toast.LENGTH_LONG).show();
                txtRecebeArquivoNovaAtividade.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());

            }
        }
    }

    public void limparDados(){
        edtNomeProfessorAtividade.setText("");
        edtTituloNovaAtividade.setText("");
        edtDescricaoNovaAtividade.setText("");
        txtRecebeArquivoNovaAtividade.setText("");
    }

    public void desabilitarComponentes(){
        edtNomeProfessorAtividade.setEnabled(false);
        edtTituloNovaAtividade.setEnabled(false);
        edtDescricaoNovaAtividade.setEnabled(false);
        spnEscolheTurmaNovaAtividade.setEnabled(false);
        spnEscolheMateriaNovaAtividade.setEnabled(false);
        procurarAtividade.setEnabled(false);
        adicionarAtividade.setEnabled(false);
        txtRecebeArquivoNovaAtividade.setEnabled(false);

    }

    public void habilitarComponentes(){
        edtNomeProfessorAtividade.setEnabled(true);
        edtTituloNovaAtividade.setEnabled(true);
        edtDescricaoNovaAtividade.setEnabled(true);
        spnEscolheTurmaNovaAtividade.setEnabled(true);
        spnEscolheMateriaNovaAtividade.setEnabled(true);
        procurarAtividade.setEnabled(true);
        adicionarAtividade.setEnabled(true);
        txtRecebeArquivoNovaAtividade.setEnabled(true);

    }
}