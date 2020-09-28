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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.atividades.CadastoNovaAtividade;
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

public class NovaAtividadeFragment extends Fragment {
    /**
     * @since 26/09/2020
     */
    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private View novaAtividade;
    private View informaUploadArquivo;

    private String recebeTurmaNovaAtividadeProfessor;

    private TextView txtNomeProfessorNovaAtividade;
    private TextView txtMateriaProfessorNovaAtividade;
    private EditText edtTituloNovaAtividade;
    private EditText edtDescricaoNovaAtividade;
    private TextView txtRecebeArquivoNovaAtividade;
    private Spinner spnEscolheTurmaNovaAtividade;
    private Button procurarAtividade;
    private Button adicionarAtividade;

    private Uri uriArquivoUploadNovaAtividade;
    private DatabaseReference dbReferenceCadastroNovaAtividade;
    private FirebaseDatabase fdCadastroNovaAtividade;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private CadastroNovoUsuario usuarioProfessor;
    private CadastoNovaAtividade cadastoNovaAtividade;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        novaAtividade = inflater.inflate(R.layout.fragment_nova_atividade, container, false);

        txtNomeProfessorNovaAtividade = novaAtividade.findViewById(R.id.txt_nome_professor_nova_atividade);
        txtMateriaProfessorNovaAtividade = novaAtividade.findViewById(R.id.txt_materia_professor_nova_atividade);
        edtTituloNovaAtividade = novaAtividade.findViewById(R.id.edt_titulo_cadastrar_atividade);
        edtDescricaoNovaAtividade = novaAtividade.findViewById(R.id.edt_conteudo_cadastrar_atividade);
        spnEscolheTurmaNovaAtividade = novaAtividade.findViewById(R.id.spin_turma_atividade);
        procurarAtividade = novaAtividade.findViewById(R.id.btn_procurar_cadastrar_atividade);
        adicionarAtividade = novaAtividade.findViewById(R.id.btn_adicionar_nova_atividade);
        txtRecebeArquivoNovaAtividade = novaAtividade.findViewById(R.id.txt_receive_archive);
        informaUploadArquivo = novaAtividade.findViewById(R.id.progressbar_informa_upload);

        return novaAtividade;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(novaAtividade.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheTurmaNovaAtividade.setAdapter(turmasAdapter);

        dbReferenceCadastroNovaAtividade = fdCadastroNovaAtividade.getInstance().getReference().child("atividades_adicionadas");
        storageReference = storage.getInstance().getReference();

    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle professor = getActivity().getIntent().getExtras();
        if(professor != null){
            usuarioProfessor = professor.getParcelable("professor");
            txtNomeProfessorNovaAtividade.setText(usuarioProfessor.getNome());
            txtMateriaProfessorNovaAtividade.setText(usuarioProfessor.getMateria());
        }else {
            Toast.makeText(getContext(), R.string.erro_informacoes_professor_bundle, Toast.LENGTH_LONG).show();
        }

        spnEscolheTurmaNovaAtividade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                  recebeTurmaNovaAtividadeProfessor = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
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
                if((txtNomeProfessorNovaAtividade.getText().toString().isEmpty())
                        || (txtMateriaProfessorNovaAtividade.getText().toString().isEmpty())
                        || (edtTituloNovaAtividade.getText().toString().equals(""))
                        || (recebeTurmaNovaAtividadeProfessor.equals("Escolha uma Turma"))
                        || (edtDescricaoNovaAtividade.getText().toString().equals(""))
                        || (txtRecebeArquivoNovaAtividade.getText().toString().equals(""))
                        || (uriArquivoUploadNovaAtividade.equals(""))){

                    Toast.makeText(getContext(),R.string.campos_obrigatorios_nova_atividade, Toast.LENGTH_LONG).show();

                }else {
                    informaUploadArquivo.setVisibility(View.VISIBLE);
                    desabilitarComponentes();
                    cadastoNovaAtividade = new CadastoNovaAtividade();
                    String filename = System.currentTimeMillis() + "";

                    storageReference.child("atividades_adicionadas_uploads").child(filename).putFile(uriArquivoUploadNovaAtividade)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String urlUploadedFile = taskSnapshot.getStorage().getPath();

                            cadastoNovaAtividade.setUID(UUID.randomUUID().toString());
                            cadastoNovaAtividade.setProfessor(txtNomeProfessorNovaAtividade.getText().toString());
                            cadastoNovaAtividade.setMateria(txtMateriaProfessorNovaAtividade.getText().toString());
                            cadastoNovaAtividade.setTitulo(edtTituloNovaAtividade.getText().toString());
                            cadastoNovaAtividade.setTurma(recebeTurmaNovaAtividadeProfessor);
                            cadastoNovaAtividade.setDescricao(edtDescricaoNovaAtividade.getText().toString());
                            cadastoNovaAtividade.setDocumento(urlUploadedFile);

                            dbReferenceCadastroNovaAtividade.push().setValue(cadastoNovaAtividade)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void aVoid) {
                                    informaUploadArquivo.setVisibility(View.GONE);
                                    Toast.makeText(novaAtividade.getContext(), R.string.sucesso_enviar_nova_atividade,
                                                                                                          Toast.LENGTH_LONG).show();
                                    limparDados();
                                    habilitarComponentes();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {

                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    informaUploadArquivo.setVisibility(View.GONE);
                                    Toast.makeText(novaAtividade.getContext(), R.string.falha_enviar_nova_atividade,
                                                                                                          Toast.LENGTH_LONG).show();
                                    habilitarComponentes();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            informaUploadArquivo.setVisibility(View.GONE);
                            Toast.makeText(novaAtividade.getContext(), R.string.erro_upload_arquivo, Toast.LENGTH_LONG).show();
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
        if(requestCode == 9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            openFile();
        }else{
            Toast.makeText(novaAtividade.getContext(), R.string.permissao_leitura_storage, Toast.LENGTH_LONG).show();
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
            Toast.makeText(novaAtividade.getContext(), R.string.nenhum_arquivo_encontrado , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_FILE_REQUESTCODE
                && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uriArquivoUploadNovaAtividade = resultData.getData();
                txtRecebeArquivoNovaAtividade.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());
            }
        }
    }

    public void limparDados(){
        edtTituloNovaAtividade.setText("");
        edtDescricaoNovaAtividade.setText("");
        txtRecebeArquivoNovaAtividade.setText("");
    }

    public void desabilitarComponentes(){
        txtNomeProfessorNovaAtividade.setEnabled(false);
        txtMateriaProfessorNovaAtividade.setEnabled(false);
        edtTituloNovaAtividade.setEnabled(false);
        edtDescricaoNovaAtividade.setEnabled(false);
        spnEscolheTurmaNovaAtividade.setEnabled(false);
        procurarAtividade.setEnabled(false);
        adicionarAtividade.setEnabled(false);
        txtRecebeArquivoNovaAtividade.setEnabled(false);
    }

    public void habilitarComponentes(){
        txtNomeProfessorNovaAtividade.setEnabled(true);
        txtMateriaProfessorNovaAtividade.setEnabled(true);
        edtTituloNovaAtividade.setEnabled(true);
        edtDescricaoNovaAtividade.setEnabled(true);
        spnEscolheTurmaNovaAtividade.setEnabled(true);
        procurarAtividade.setEnabled(true);
        adicionarAtividade.setEnabled(true);
        txtRecebeArquivoNovaAtividade.setEnabled(true);
    }
}