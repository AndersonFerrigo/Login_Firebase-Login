package com.example.myapplication.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.model.login.RecuperarSenhaUsuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecuperarSenhaActivity extends AppCompatActivity {

    /**
     * @since 22/09/2020
     *
     */
    private String recebePerfilRecuperarSenha;
    private String recebeSenhaRecuperada;

    private EditText edtNomeRecuperarSenha;
    private EditText edtEmailRecuperarSenha;

    private Spinner spnPerfilRecuperarSenha;

    private Button btnRecuperarSenhaUsuario;
    private Button btnRetornaLogin;

    private DatabaseReference dbReferenceRecuperarSenha;
    private FirebaseDatabase fdbRecuperarSenhaUsuario;

    private RecuperarSenhaUsuario recuperarSenhaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        edtEmailRecuperarSenha = findViewById(R.id.edt_email_recuperar_senha);
        edtNomeRecuperarSenha = findViewById(R.id.edt_nome_recuperar_senha);
        spnPerfilRecuperarSenha = findViewById(R.id.spn_perfil);
        btnRecuperarSenhaUsuario = findViewById(R.id.btn_resetar_senha);
        btnRetornaLogin = findViewById(R.id.btn_voltar_login);

        dbReferenceRecuperarSenha = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Carrega o conteudo do spinner perfil
        ArrayAdapter adapterPerfilUsuario = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.perfilUsuario, R.layout.spinner_text_adapter);
        adapterPerfilUsuario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
        );
        spnPerfilRecuperarSenha.setAdapter(adapterPerfilUsuario);
    }

    @Override
    protected void onResume() {
        super.onResume();
        spnPerfilRecuperarSenha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                recebePerfilRecuperarSenha = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});

        btnRecuperarSenhaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((edtNomeRecuperarSenha.getText().equals("")) || (edtEmailRecuperarSenha.getText().equals(""))
                                                                        || recebePerfilRecuperarSenha.equals("Escolha um perfil")){

                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();

                }else{
                    recuperarSenhaUsuario = new RecuperarSenhaUsuario();
                    recuperarSenhaUsuario.setRecuperaNomeUsuario(edtNomeRecuperarSenha.getText().toString());
                    recuperarSenhaUsuario.setRecuperaEmailUsuario(edtEmailRecuperarSenha.getText().toString());
                    recuperarSenhaUsuario.setRecuperaPerfilUsuario(recebePerfilRecuperarSenha);

                    dbReferenceRecuperarSenha.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            CadastroNovoUsuario cadastroNovoUsuario = snapshot.getValue(CadastroNovoUsuario.class);
                            assert cadastroNovoUsuario != null;
                            if ((recuperarSenhaUsuario.getRecuperaNomeUsuario().equals(cadastroNovoUsuario.getNome())) &&
                                    (recuperarSenhaUsuario.getRecuperaEmailUsuario().equals(cadastroNovoUsuario.getEmail())) &&
                                    (recuperarSenhaUsuario.getRecuperaPerfilUsuario().equals(cadastroNovoUsuario.getPerfil()))) {

                                recebeSenhaRecuperada = "Sua senha é  " + cadastroNovoUsuario.getSenha();

                                AlertDialog.Builder builder = new AlertDialog.Builder(RecuperarSenhaActivity.this);
                                builder.setMessage(recebeSenhaRecuperada)
                                        .setTitle(R.string.txt_title_dialog_recuperar_senha);

                                builder.setPositiveButton(R.string.btn_dialog_recuperar_senha_ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                                AlertDialog dialog =  builder.create();
                                dialog.show();
                            }
                            limparDados();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), " Dados incorretos", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btnRetornaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retornarLogin();
            }
        });
    }

    @Override
    public void onBackPressed() {
        retornarLogin();
    }

    public void retornarLogin(){
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        RecuperarSenhaActivity.this.finish();
    }

    public void limparDados(){
        edtNomeRecuperarSenha.setText("");
        edtEmailRecuperarSenha.setText("");
    }

}