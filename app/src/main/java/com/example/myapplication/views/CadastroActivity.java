package com.example.myapplication.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Cadastro;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CadastroActivity extends AppCompatActivity {
    private Cadastro cadastroNovoUser;
    String recebePerfil;

    ImageView imgTurmaAlunoIc;
    ImageView imgMateriaProfessorIc;

    private EditText edtEmailNovoUsuario;
    private EditText edtSenhaNovoUsuario;
    private Button btnRegistrarNovoUsuario;
    private Button btnVoltarLogin;
    private Spinner spnTipoPerfilUsuario;
    private Spinner spnTurmaAluno;
    private Spinner spnMateriaProfessor;

    private DatabaseReference dbReferenceCadastroNovoUsuario;
    private FirebaseDatabase cadastroNovoUsuario;
    @Override
    protected void onStart() {
        super.onStart();
        edtEmailNovoUsuario = findViewById(R.id.editEmail_cadastro);
        edtSenhaNovoUsuario = findViewById(R.id.editSenha_cadastro);
        btnRegistrarNovoUsuario = findViewById(R.id.btn_cadastrar_novo_usuario);
        btnVoltarLogin = findViewById(R.id.btn_Voltar);
        spnTipoPerfilUsuario = findViewById(R.id.spin_tipo);
        imgMateriaProfessorIc = findViewById(R.id.img_materia_professor_ic);
        imgTurmaAlunoIc = findViewById(R.id.img_turma_aluno_ic);
        spnMateriaProfessor = findViewById(R.id.spn_materia_professor);
        spnTurmaAluno = findViewById(R.id.spn_turma_aluno);



        // Carrega o conteudo do spinner perfil
        ArrayAdapter adapterPerfilUsuario = ArrayAdapter.createFromResource(getApplicationContext(),
                                        R.array.perfilUsuario, R.layout.spinner_text_adapter);
        adapterPerfilUsuario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
        );
        spnTipoPerfilUsuario.setAdapter(adapterPerfilUsuario);

        // Carrega o conteudo do spinner materia do professor
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getApplicationContext(),
                                        R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);

        // Carrega o conteudo do spinner turma do aluno
        ArrayAdapter adapterTurmaAluno = ArrayAdapter.createFromResource(getApplicationContext(),
                                        R.array.turmas_escola, R.layout.spinner_text_adapter);
        adapterTurmaAluno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTurmaAluno.setAdapter(adapterTurmaAluno);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        dbReferenceCadastroNovoUsuario = cadastroNovoUsuario.getInstance().getReference().child("users");
    }

    @Override
    protected void onResume() {
        super.onResume();

        spnTipoPerfilUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(adapterView.getItemAtPosition(position).equals(0)){
                    Toast.makeText(getApplicationContext(), "Escolha uma turma", Toast.LENGTH_LONG).show();
                }else{
                    recebePerfil = adapterView.getItemAtPosition(position).toString();
                    if(recebePerfil.equals("Aluno")){
                        Toast.makeText(getApplicationContext(), "Aluno", Toast.LENGTH_LONG).show();
                        imgTurmaAlunoIc.setVisibility(View.VISIBLE);
                        imgMateriaProfessorIc.setVisibility(View.INVISIBLE);

                        spnTurmaAluno.setVisibility(View.VISIBLE);
                        spnMateriaProfessor.setVisibility(View.INVISIBLE);
                    }
                    if(recebePerfil.equals("Professor")){
                        Toast.makeText(getApplicationContext(), "Professor", Toast.LENGTH_LONG).show();
                        imgTurmaAlunoIc.setVisibility(View.INVISIBLE);
                        imgMateriaProfessorIc.setVisibility(View.VISIBLE);

                        spnTurmaAluno.setVisibility(View.INVISIBLE);
                        spnMateriaProfessor.setVisibility(View.VISIBLE);

                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        btnRegistrarNovoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((edtEmailNovoUsuario.getText().toString().equals("")) && (edtSenhaNovoUsuario.getText().toString().equals(""))
                        && recebePerfil.equals("Escolha um perfil")){

                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show();
                }else {
                    cadastroNovoUser = new Cadastro();
                    cadastroNovoUser.setUid(UUID.randomUUID().toString());
                    cadastroNovoUser.setEmail(edtEmailNovoUsuario.getText().toString());
                    cadastroNovoUser.setSenha(edtSenhaNovoUsuario.getText().toString());
                    cadastroNovoUser.setPerfil(recebePerfil);

                    //Salva novo usuario no firebase
                    dbReferenceCadastroNovoUsuario.push().setValue(cadastroNovoUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Cadastro  de usuario realizado com sucesso", Toast.LENGTH_LONG).show();
                                    limparDados();
                                    Intent retornarLogin = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(retornarLogin);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Problemas ao realizar o cadastro", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

        btnVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(voltarTelaLogin);
                finish();
            }
        });
    }

    public void limparDados(){
        edtEmailNovoUsuario.setText("");
        edtSenhaNovoUsuario.setText("");
    }
}
