package com.example.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.model.login.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    /***
     * @since 22/09/2020
     *
     */
    private boolean login;

    private Login loginUsuario;

    private EditText editEmail;
    private EditText editSenha;
    private TextView txtResetSenha;

    private Button btnLogar;
    private Button btnCadastro;

    private AlertDialog dialog;

    private DatabaseReference databaseReference;
    private FirebaseDatabase buscarLogin;

    private CadastroNovoUsuario novoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Comando para deixar a barra de status transparente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        editEmail = findViewById(R.id.editEmail_Login);
        editSenha = findViewById(R.id.editSenha_Login);
        btnLogar = findViewById(R.id.btn_Logar);
        btnCadastro = findViewById(R.id.btn_cadastrar);
        txtResetSenha = findViewById(R.id.textEsqueciaSenha);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = buscarLogin.getInstance().getReference().child("users");
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastroNovoUsuarioIntent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(cadastroNovoUsuarioIntent);
                LoginActivity.this.finish();
            }
        });

        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recuperarSenhaUsuarioIntent = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(recuperarSenhaUsuarioIntent);
                LoginActivity.this.finish();
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if ((editEmail.getText().toString().equals("")) && (editSenha.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Email e senha devem ser preenchidos!", Toast.LENGTH_LONG).show();
            }
            else {
                loginUsuario = new Login();
                loginUsuario.setEmail(editEmail.getText().toString());
                loginUsuario.setSenha(editSenha.getText().toString());

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            novoUsuario = dataSnapshot.getValue(CadastroNovoUsuario.class);

                            if ((loginUsuario.getEmail().equals(novoUsuario.getEmail())) &&
                                    loginUsuario.getSenha().equals(novoUsuario.getSenha())&&
                                    novoUsuario.getPerfil().equals("Aluno")) {

                                Intent intentMainAlunoContent = new Intent(getApplicationContext(), MainAlunoContentActivity.class);
                                intentMainAlunoContent.putExtra("aluno", novoUsuario);
                                startActivity(intentMainAlunoContent);
                                login = true;
                                LoginActivity.this.finish();
                            }
                                if ((loginUsuario.getEmail().equals(novoUsuario.getEmail())) &&
                                    loginUsuario.getSenha().equals(novoUsuario.getSenha())&&
                                    novoUsuario.getPerfil().equals("Professor")) {

                                    Intent intentMainProfessorContent = new Intent(getApplicationContext(), MainProfessorContentActivity.class);
                                    intentMainProfessorContent.putExtra("professor", novoUsuario);
                                    startActivity(intentMainProfessorContent);
                                    login = true;
                                    LoginActivity.this.finish();
                                }
                        }

                        if(!login){
                            Toast.makeText(getApplicationContext(), "Usuário não cadastrado", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Não foi possível recuperar usuario, tente novamente. ", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    public Dialog showDialog(){
        final AlertDialog.Builder  builder =  new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.sair_aplicacao_dialog, null);
        view.findViewById(R.id.btn_fechar_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        view.findViewById(R.id.btn_sair_homeschool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        return dialog;
    }
}
