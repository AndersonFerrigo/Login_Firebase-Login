package com.example.myapplication.content.aluno;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.views.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class MainAlunoContentActivity extends AppCompatActivity {
    /**
     * @since 22/09/2020
     */
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationViewAluno;

    private View headerViewAluno;

    private CadastroNovoUsuario alunoUsuario;

    private TextView txtAlunoNome;
    private TextView txtAlunoTurma;
    private TextView txtAlunoEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aluno_content);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

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
    }

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle aluno = getIntent().getExtras();
        if(aluno != null){
            alunoUsuario = aluno.getParcelable("aluno");
            txtAlunoNome.setText(alunoUsuario.getNome());
            txtAlunoTurma.setText(alunoUsuario.getTurma());
            txtAlunoEmail.setText(alunoUsuario.getEmail());
        }else {
            Toast.makeText(getApplicationContext(), R.string.erro_informacoes_aluno_bundle, Toast.LENGTH_LONG).show();
        }
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
        MainAlunoContentActivity.this.finish();
    }

    @Override
    public void onBackPressed() {}
}
