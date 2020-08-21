package com.example.myapplication.content.aluno;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.model.CadastroNovoUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainAlunoContentActivity extends AppCompatActivity {

    /**
     * @since 2020
     */

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationViewAluno;
    private View headerViewAluno;

    private CadastroNovoUsuario alunoUsuario;

    private String recebeNomeBundle;
    private String recebeTurmaBundle;
    private String recebeEmailBundle;

    private TextView txtAlunoNome;
    private TextView txtAlunoTurma;
    private TextView txtAlunoEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aluno_content);

        navigationViewAluno = findViewById(R.id.nav_view_aluno);
        headerViewAluno = navigationViewAluno.getHeaderView(0);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_aluno);
        NavigationView navigationView = findViewById(R.id.nav_view_aluno);


        Toolbar toolbar = findViewById(R.id.toolbar_aluno);
        setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Bundle aluno = getIntent().getExtras();
        if(aluno.isEmpty()){
            Toast.makeText(getApplicationContext(), "Erro ao recuperar informações do aluno", Toast.LENGTH_LONG).show();
        }else {
            alunoUsuario = aluno.getParcelable("aluno");
            recebeNomeBundle = alunoUsuario.getNome();
            recebeTurmaBundle = alunoUsuario.getTurma();
            recebeEmailBundle = alunoUsuario.getEmail();
        }

        txtAlunoNome  = headerViewAluno.findViewById(R.id.nav_header_perfil_aluno_usuario);
        txtAlunoTurma  = headerViewAluno.findViewById(R.id.nav_header_turma_aluno_usuario);
        txtAlunoEmail = headerViewAluno.findViewById(R.id.nav_header_email_aluno_usuario);


        txtAlunoNome.setText(recebeNomeBundle);
        txtAlunoTurma.setText(recebeTurmaBundle);
        txtAlunoEmail.setText(recebeEmailBundle);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_atividade_aluno, R.id.nav_conteudo_aluno,
                        R.id.nav_enviar_atividade_concluida, R.id.nav_duvidas_atividade_concluida,
                        R.id.nav_duvidas_atividade_concluida, R.id.nav_duvidas_conteudo)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_aluno);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_aluno_content, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_aluno);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
