package com.example.myapplication.content.professor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;


public class HomeFragment extends Fragment {
    View profileProfessor;

    ImageView imageViewProfileProfessor;

    EditText txtRecebeEmailProfessor;
    EditText txtRecebeNomeProfessor;
    EditText txtRecebeMateriaProfessor;

    Button btnHabilitaEdicaoPerfil;
    Button btnAtualizaPerfil;


    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileProfessor = inflater.inflate(R.layout.fragment_home, container, false);

         imageViewProfileProfessor =  profileProfessor.findViewById(R.id.img_profile_professor);
         txtRecebeEmailProfessor =  profileProfessor.findViewById(R.id.txt_recebe_email_professor);
         txtRecebeNomeProfessor =  profileProfessor.findViewById(R.id.txt_recebe_nome_professor);
         txtRecebeMateriaProfessor =  profileProfessor.findViewById(R.id.txt_recebe_materia_professor);

         btnHabilitaEdicaoPerfil = profileProfessor.findViewById(R.id.btn_habilita_ed_perfil_professor);
         btnAtualizaPerfil = profileProfessor.findViewById(R.id.btn_atualiza_perfil_professor);

        return profileProfessor;
    }

    @Override
    public void onStart() {
        super.onStart();

        btnHabilitaEdicaoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRecebeEmailProfessor.setEnabled(true);
                txtRecebeNomeProfessor.setEnabled(true);
                txtRecebeMateriaProfessor.setEnabled(true);
            }
        });

        btnAtualizaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(profileProfessor.getContext(), "Atualizado", Toast.LENGTH_SHORT).show();
                txtRecebeEmailProfessor.setEnabled(false);
                txtRecebeNomeProfessor.setEnabled(false);
                txtRecebeMateriaProfessor.setEnabled(false);
            }
        });

    }
}