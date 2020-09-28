package com.example.myapplication.content.professor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.MainAlunoContentActivity;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.views.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainProfessorContentActivity extends AppCompatActivity {
    /**
     * @since 26/09/2020
     */
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationViewProfessor;
    private View headerViewProfessor;

    private CadastroNovoUsuario usuarioProfessor;

    private TextView txtProfessorNome;
    private TextView txtProfessorMateria;
    private TextView txtProfessorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_professor_content2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_professor);
        navigationViewProfessor = findViewById(R.id.nav_view_professor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerViewProfessor = navigationViewProfessor.getHeaderView(0);
        txtProfessorNome  = headerViewProfessor.findViewById(R.id.nav_header_nome_professor);
        txtProfessorMateria  = headerViewProfessor.findViewById(R.id.nav_header_materia_professor);
        txtProfessorEmail = headerViewProfessor.findViewById(R.id.nav_header_email_professor);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_nova_atividade, R.id.nav_novo_conteudo,
                    R.id.nav_atividades_recebidas, R.id.nav_duvidas_atividades, R.id.nav_duvidas_conteudo)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle professor = getIntent().getExtras();
        if(professor != null){
            usuarioProfessor = professor.getParcelable("professor");
            txtProfessorNome.setText(usuarioProfessor.getNome());
            txtProfessorMateria.setText(usuarioProfessor.getMateria());
            txtProfessorEmail.setText(usuarioProfessor.getEmail());
        }else {
            Toast.makeText(getApplicationContext(), R.string.erro_informacoes_professor_bundle, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_professor_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out_professor:
                logOutProfessor();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOutProfessor() {
        Intent intentLogOut = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentLogOut);
        MainProfessorContentActivity.this.finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

    }
}