package com.example.myapplication.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.MainAlunoContentActivity;
import com.example.myapplication.content.professor.MainProfessorContentActivity;
import com.example.myapplication.model.Cadastro;
import com.example.myapplication.model.Login;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    private Login loginUsuario;

    private EditText editEmail, editSenha;
    private Button btnLogar, btnCadastro;
    private TextView txtResetSenha;
    private DatabaseReference databaseReference;
    private FirebaseDatabase buscarLogin;

    @Override
    protected void onStart() {
        super.onStart();
        editEmail = (EditText) findViewById(R.id.editEmail_Login);
        editSenha = (EditText) findViewById(R.id.editSenha_Login);
        btnLogar = (Button) findViewById(R.id.btn_Logar);
        btnCadastro = (Button) findViewById(R.id.btn_cadastrar);
        txtResetSenha = (TextView) findViewById(R.id.textEsqueciaSenha);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        databaseReference = buscarLogin.getInstance().getReference().child("users");



    }

    @Override
    protected void onResume() {
        super.onResume();

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(i);

            }
        });
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((editEmail.getText().equals("")) && (editSenha.getText().equals(""))){
                    Toast.makeText(getApplicationContext(), "Email e senha devem ser preenchidos!", Toast.LENGTH_LONG).show();
                }else{

                    loginUsuario = new Login();
                    loginUsuario.setEmail(editEmail.getText().toString());
                    loginUsuario.setSenha(editSenha.getText().toString());

                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Cadastro cadastro = snapshot.getValue(Cadastro.class);
                            if((loginUsuario.getEmail().equals(cadastro.getEmail())) &&
                                        loginUsuario.getSenha().equals(cadastro.getSenha())){
                                if(cadastro.getPerfil().equals("Aluno")){
                                    Toast.makeText(getApplicationContext(), "Perfil ==  " + cadastro.getPerfil(), Toast.LENGTH_LONG).show();
                                    Intent intentAtividades = new Intent(getApplicationContext(), MainAlunoContentActivity.class);
                                    startActivity(intentAtividades);
                                    limparDados();

                                }
                                 if(cadastro.getPerfil().equals("Professor")){
                                    Toast.makeText(getApplicationContext(), "Perfil ==  " + cadastro.getPerfil(), Toast.LENGTH_LONG).show();
                                    Intent intentAtividades = new Intent(getApplicationContext(), MainProfessorContentActivity.class);
                                    startActivity(intentAtividades);
                                     limparDados();

                                 }if(cadastro.getPerfil().equals("")){
                                    Toast.makeText(getApplicationContext(), "Email ou senha incorretos", Toast.LENGTH_LONG).show();
                                }

                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ResetSenha.class);
                startActivity(i);
            }
        });

    }

    public void limparDados(){
        editEmail.setText("");
        editSenha.setText("");
    }

}