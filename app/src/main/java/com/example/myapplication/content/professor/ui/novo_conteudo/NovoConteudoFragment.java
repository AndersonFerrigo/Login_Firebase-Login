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
    View novoConteudo;

    private CadastarAtividade cadastarAtividade;

    ProgressBar simpleProgressBar;
    String turma;
    TextView recebeArquivo;
    Spinner escolherTurma;
    EditText tituloConteudo;
    EditText descricaoConteudo;
    Button procurarConteudo;
    Button adicionarConteudo;

    Uri uriArquivoUpload;

    private static final int CHOOSE_FILE_REQUESTCODE = 2;

    private DatabaseReference dbReferenceCadastroNovoConteudo;
    private FirebaseDatabase cadastroNovoConteudo;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    private NovaAtividadeViewModel novaAtividadeViewModel;


    private NovoConteudoViewModel novoConteudoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        novoConteudo = inflater.inflate(R.layout.fragment_novo_conteudo, container, false);

        escolherTurma = novoConteudo.findViewById(R.id.spin_turma_conteudo);
        tituloConteudo = novoConteudo.findViewById(R.id.edt_titulo_cadastrar_conteudo);
        descricaoConteudo = novoConteudo.findViewById(R.id.edt_cadastrar_conteudo);
        procurarConteudo = novoConteudo.findViewById(R.id.btn_procurar_cadastrar_conteudo);
        adicionarConteudo = novoConteudo.findViewById(R.id.btn_adicionar_novo_conteudo);
        recebeArquivo = novoConteudo.findViewById(R.id.txt_receive_content);
        simpleProgressBar = novoConteudo.findViewById(R.id.simpleProgressBar);

        ArrayAdapter turmasAdapter = ArrayAdapter.createFromResource(novoConteudo.getContext(),
                R.array.turmas_escola, R.layout.spinner_text_adapter);

        turmasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        escolherTurma.setAdapter(turmasAdapter);


        dbReferenceCadastroNovoConteudo = cadastroNovoConteudo.getInstance().getReference().child("conteudos");
        storageReference = storage.getInstance().getReference();

        return novoConteudo;
    }

    @Override
    public void onResume() {
        super.onResume();

        escolherTurma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(adapterView.getItemAtPosition(position).equals(0)){
                    Toast.makeText(novoConteudo.getContext(), "Escolha uma turma", Toast.LENGTH_LONG).show();

                }else{
                    turma = adapterView.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        procurarConteudo.setOnClickListener(new View.OnClickListener() {
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

        adicionarConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleProgressBar.setVisibility(View.VISIBLE);
                cadastarAtividade = new CadastarAtividade();

                String filename = System.currentTimeMillis() + "";
                // Retorna O caminho raiz
                storageReference.child("AtividadesUploads").child(filename).putFile(uriArquivoUpload)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String urlUploadedFile = taskSnapshot.getMetadata().toString();

                                cadastarAtividade.setTurma(turma);
                                cadastarAtividade.setTitulo(tituloConteudo.getText().toString());
                                cadastarAtividade.setDescricaoAtividade(descricaoConteudo.getText().toString());
                                cadastarAtividade.setPathDocumentoAtividade(urlUploadedFile);

                                //Salva novo usuario no firebase
                                dbReferenceCadastroNovoConteudo.push().setValue(cadastarAtividade)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                simpleProgressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(novoConteudo.getContext(), "Cadastro  da atividade realizado com sucesso", Toast.LENGTH_LONG).show();
                                                limparDados();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                simpleProgressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(novoConteudo.getContext(), "Problemas ao realizar o cadastro", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        simpleProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(novoConteudo.getContext(), "Problemas ao realizar o upload do arquivo", Toast.LENGTH_LONG).show();
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
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            startActivityForResult(chooserIntent, CHOOSE_FILE_REQUESTCODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(novoConteudo.getContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
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
                uriArquivoUpload = resultData.getData();

                // Perform operations on the document using its URI.
                //        Toast.makeText(getApplicationContext(), "Caminho do arquivo = " + uri, Toast.LENGTH_LONG).show();
                recebeArquivo.setText("Arquivo selecionado: " + resultData.getData().getLastPathSegment());

            }
        }
    }

    public void limparDados(){
        tituloConteudo.setText("");
        descricaoConteudo.setText("");
        recebeArquivo.setText("");
    }
}