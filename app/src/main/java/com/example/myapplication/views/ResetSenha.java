package com.example.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Conexao;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetSenha extends AppCompatActivity {

    String recebeEmailResetSenha;

    private EditText edtEmailResetSenha;
    private Button btnResetSenha;
    private Button btnRetornaLogin;


    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        edtEmailResetSenha = findViewById(R.id.edit_email_reset);
        btnResetSenha = findViewById(R.id.btn_resetar_senha);
        btnRetornaLogin = findViewById(R.id.btn_voltar_login);

    }

    @Override
    protected void onStart() {
        super.onStart();

        btnResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recebeEmailResetSenha = edtEmailResetSenha.getText().toString();

                if(recebeEmailResetSenha.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Informe seu email", Toast.LENGTH_SHORT).show();
                }else{

                    edtEmailResetSenha.setText("");
                    Toast.makeText(getApplicationContext(), "Senha será enviada para email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRetornaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtEmailResetSenha.setText("");
                Intent retornarTelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(retornarTelaLogin);
            }
        });
    }

}