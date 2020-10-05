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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.conteudo.CadastroNovoConteudo;
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

public class NovoConteudoFragment extends Fragment {
    /**
     * @since 26/09/2020
     */
    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private View novoConteudo;
    private View informaUploadArquivo;

    private String recebeTurmaNovoConteudoProfessor;

    private TextView txtNomeProfessorNovoConteudo;
    private TextView txtMateriaProfessorNovoConteudo;
    private EditText edtTituloNovoConteudo;
    private EditText edtDescricaoNovoConteudo;
    private TextView txtRecebeArquivoNovoConteudo;
    private Spinner spnEscolheTurmaNovoConteudo;
    private Button btnProcurarConteudo;
    private Button btnAdicionarNovoConteudo;

    private Uri uriArquivoUploadNovoConteudo;
    private DatabaseReference dbReferenceCadastroNovoConteudo;
    private FirebaseDatabase cadastroNovoConteudo;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private CadastroNovoConteudo cadastroConteudo;
    private CadastroNovoUsuario usuarioProfessor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        novoConteudo = inflater.inflate(R.layout.fragment_novo_conteudo, container, false);

        txtNomeProfessorNovoConteudo = novoConteudo.findViewById(R.id.txt_nome_professor_novo_conteudo);
        txtMateriaProfessorNovoConteudo = novoConteudo.findViewById(R.id.txt_materia_professor_novo_conteudo);
        edtTituloNovoConteudo = novoConteudo.findViewById(R.id.edt_titulo_cadastrar_conteudo);
        edtDescricaoNovoConteudo = novoConteudo.findViewById(R.id.edt_descrição_cadastrar_novo_conteudo);
        spnEscolheTurmaNovoConteudo = novoConteudo.findViewById(R.id.spin_turma_conteudo);
        txtRecebeArquivoNovoConteudo = novoConteudo.findViewById(R.id.txt_recebe_arquivo_novo_conteudo);
        btnProcurarConteudo = novoConteudo.findViewById(R.id.btn_procurar_cadastrar_conteudo);
        btnAdicionarNovoConteudo = novoConteudo.findViewById(R.id.btn_adicionar_novo_conteudo);
        informaUploadArquivo = novoConteudo.findViewById(R.id.progressbar_informa_upload);

        return novoConteudo;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(novoConteudo.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEscolheTurmaNovoConteudo.setAdapter(turmasAdapter);

        dbReferenceCadastroNovoConteudo = cadastroNovoConteudo.getInstance().getReference().child("conteudos_adicionados");
        storageReference = storage.getInstance().getReference();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle professor = getActivity().getIntent().getExtras();
        if(professor != null){
            usuarioProfessor = professor.getParcelable("professor");
            txtNomeProfessorNovoConteudo.setText(usuarioProfessor.getNome());
            txtMateriaProfessorNovoConteudo.setText(usuarioProfessor.getMateria());
        }else {
            Toast.makeText(getContext(), R.string.erro_informacoes_professor_bundle, Toast.LENGTH_LONG).show();
        }

        spnEscolheTurmaNovoConteudo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                  recebeTurmaNovoConteudoProfessor = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        btnProcurarConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(novoConteudo.getContext(),  Manifest.permission.READ_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED){
                    openFile();
                }else{
                    ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                                                                                    9);
                }
            }
        });

        btnAdicionarNovoConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if((txtNomeProfessorNovoConteudo.getText().toString().isEmpty())
                || (txtMateriaProfessorNovoConteudo.getText().toString().isEmpty())
                || (edtTituloNovoConteudo.getText().toString().equals(""))
                || (recebeTurmaNovoConteudoProfessor.equals("Escolha uma Turma"))
                || (edtDescricaoNovoConteudo.getText().toString().equals(""))
                || (txtRecebeArquivoNovoConteudo.getText().toString().equals(""))
                || (uriArquivoUploadNovoConteudo.equals(""))) {

                    Toast.makeText(getContext(), R.string.campos_obrigatorios_novo_conteudo, Toast.LENGTH_LONG).show();

            }else {

                informaUploadArquivo.setVisibility(View.VISIBLE);
                desabilitarComponentes();
                cadastroConteudo = new CadastroNovoConteudo();
                String filename = System.currentTimeMillis() + "";

                storageReference.child("conteudos_adicionados_uploads").child(filename).putFile(uriArquivoUploadNovoConteudo)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String urlUploadedFile = taskSnapshot.getStorage().getPath();

                        cadastroConteudo.setUID(UUID.randomUUID().toString());
                        cadastroConteudo.setProfessor(txtNomeProfessorNovoConteudo.getText().toString());
                        cadastroConteudo.setMateria(txtMateriaProfessorNovoConteudo.getText().toString());
                        cadastroConteudo.setTitulo(edtTituloNovoConteudo.getText().toString());
                        cadastroConteudo.setTurma(recebeTurmaNovoConteudoProfessor);
                        cadastroConteudo.setDescricao(edtDescricaoNovoConteudo.getText().toString());
                        cadastroConteudo.setDocumento(urlUploadedFile);

                        dbReferenceCadastroNovoConteudo.push().setValue(cadastroConteudo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                informaUploadArquivo.setVisibility(View.GONE);
                                Toast.makeText(novoConteudo.getContext(), R.string.sucesso_enviar_novo_conteudo,
                                                                                                          Toast.LENGTH_LONG).show();
                                limparDados();
                                habilitarComponentes();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {
                                informaUploadArquivo.setVisibility(View.GONE);
                                Toast.makeText(novoConteudo.getContext(), R.string.falha_enviar_novo_conteudo,
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
                        Toast.makeText(novoConteudo.getContext(), R.string.erro_upload_arquivo, Toast.LENGTH_LONG).show();
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
            Toast.makeText(novoConteudo.getContext(), R.string.permissao_leitura_storage, Toast.LENGTH_LONG).show();
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
            Toast.makeText(novoConteudo.getContext(), R.string.nenhum_arquivo_encontrado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == CHOOSE_FILE_REQUESTCODE
                && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uriArquivoUploadNovoConteudo = resultData.getData();
                txtRecebeArquivoNovoConteudo.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());
            }
        }
    }

    public void limparDados(){
        edtTituloNovoConteudo.setText("");
        edtDescricaoNovoConteudo.setText("");
        txtRecebeArquivoNovoConteudo.setText("");
    }

    public void desabilitarComponentes(){
        txtNomeProfessorNovoConteudo.setEnabled(false);
        txtMateriaProfessorNovoConteudo.setEnabled(false);
        edtTituloNovoConteudo.setEnabled(false);
        spnEscolheTurmaNovoConteudo.setEnabled(false);
        edtDescricaoNovoConteudo.setEnabled(false);
        txtRecebeArquivoNovoConteudo.setEnabled(false);
        btnProcurarConteudo.setEnabled(false);
        btnAdicionarNovoConteudo.setEnabled(false);
    }

    public void habilitarComponentes(){
        txtNomeProfessorNovoConteudo.setEnabled(true);
        txtMateriaProfessorNovoConteudo.setEnabled(true);
        edtTituloNovoConteudo.setEnabled(true);
        spnEscolheTurmaNovoConteudo.setEnabled(true);
        edtDescricaoNovoConteudo.setEnabled(true);
        txtRecebeArquivoNovoConteudo.setEnabled(true);
        btnProcurarConteudo.setEnabled(true);
        btnAdicionarNovoConteudo.setEnabled(true);
    }
}