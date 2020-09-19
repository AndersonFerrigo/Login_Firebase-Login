package com.example.myapplication.content.aluno;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.model.CadastroNovoUsuario;
import com.example.myapplication.views.LoginActivity;
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout_aluno);
        NavigationView navigationView = findViewById(R.id.nav_view_aluno);

        navigationViewAluno = findViewById(R.id.nav_view_aluno);

        Toolbar toolbar = findViewById(R.id.toolbar_aluno);
        setSupportActionBar(toolbar);


        headerViewAluno = navigationViewAluno.getHeaderView(0);
        txtAlunoNome  = headerViewAluno.findViewById(R.id.nav_header_perfil_aluno_usuario);
        txtAlunoTurma  = headerViewAluno.findViewById(R.id.nav_header_turma_aluno_usuario);
        txtAlunoEmail = headerViewAluno.findViewById(R.id.nav_header_email_aluno_usuario);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_atividade_aluno, R.id.nav_conteudo_aluno,
                R.id.nav_enviar_atividade_concluida, R.id.nav_duvidas_atividade_concluida,
                R.id.nav_duvidas_atividade_concluida, R.id.nav_duvidas_conteudo)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_aluno);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationViewAluno, navController);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle aluno = getIntent().getExtras();
        if(aluno.isEmpty()){
            Toast.makeText(getApplicationContext(), "Erro ao recuperar informações do aluno", Toast.LENGTH_LONG).show();
        }else {
            alunoUsuario = aluno.getParcelable("aluno");
            recebeNomeBundle = alunoUsuario.getNome();
            recebeTurmaBundle = alunoUsuario.getTurma();
            recebeEmailBundle = alunoUsuario.getEmail();
        }
        txtAlunoNome.setText(recebeNomeBundle);
        txtAlunoTurma.setText(recebeTurmaBundle);
        txtAlunoEmail.setText(recebeEmailBundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_aluno_content, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out_aluno:
                logOutAluno();
                return true;

            case R.id.menu_perfil_aluno:
                perfil();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_aluno);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void logOutAluno() {
        Intent intentLogOut = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentLogOut);
    }

    private void perfil() { }

    @Override
    public void onBackPressed() {

    }
}
