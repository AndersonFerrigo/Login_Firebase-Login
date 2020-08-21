package com.example.myapplication.content.professor;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.CadastroNovoUsuario;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainProfessorContentActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationViewProfessor;
    private View headerViewProfessor;

    private CadastroNovoUsuario usuarioProfessor;

    private String recebeNomeBundle;
    private String recebeMateriaBundle;
    private String recebeEmailBundle;

    private TextView txtProfessorNome;
    private TextView txtProfessorMateria;
    private TextView txtProfessorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_professor_content2);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_professor);

        navigationViewProfessor = findViewById(R.id.nav_view_professor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Bundle professor = getIntent().getExtras();
        if(professor.isEmpty()){
            Toast.makeText(getApplicationContext(), "Erro ao recuperar informações do professor", Toast.LENGTH_LONG).show();
        }else {

            usuarioProfessor = professor.getParcelable("professor");
            recebeNomeBundle = usuarioProfessor.getNome();
            recebeMateriaBundle = usuarioProfessor.getMateria();
            recebeEmailBundle = usuarioProfessor.getEmail();
        }

        headerViewProfessor = navigationViewProfessor.getHeaderView(0);

        txtProfessorNome  = headerViewProfessor.findViewById(R.id.nav_header_nome_professor);
        txtProfessorMateria  = headerViewProfessor.findViewById(R.id.nav_header_materia_professor);
        txtProfessorEmail = headerViewProfessor.findViewById(R.id.nav_header_email_professor);

        txtProfessorNome.setText(recebeNomeBundle);
        txtProfessorMateria.setText(recebeMateriaBundle);
        txtProfessorEmail.setText(recebeEmailBundle);

        //R.id.nav_home
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_professor_content, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}