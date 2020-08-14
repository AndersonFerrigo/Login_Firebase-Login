package com.example.myapplication.content.professor.ui.duvidas_alunos_atividades;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.content.adapters.professor.ListAdapterDuvidasAtividadesAluno;

public class DuvidasAlunosAtividadesFragment extends Fragment {
    View alunoDuvidasAtividades;

    private Spinner spnMateriaProfessor;
    RecyclerView recyclerViewDuvidasAtividadesRecebidas;


    private DuvidasAlunosAtividadesViewModel mViewModel;

    public static DuvidasAlunosAtividadesFragment newInstance() {
        return new DuvidasAlunosAtividadesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         alunoDuvidasAtividades =  inflater.inflate(R.layout.duvidas_alunos_atividades_fragment, container, false);

        spnMateriaProfessor = alunoDuvidasAtividades.findViewById(R.id.spn_materia_professor);

        // Carrega o conteudo do spinner materia do professor
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);


        recyclerViewDuvidasAtividadesRecebidas = alunoDuvidasAtividades.findViewById(R.id.recycler_view_duvidas_aluno_tarefas);

        recyclerViewDuvidasAtividadesRecebidas.setAdapter(new ListAdapterDuvidasAtividadesAluno(getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerViewDuvidasAtividadesRecebidas.setLayoutManager(layoutManager);



        return alunoDuvidasAtividades;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DuvidasAlunosAtividadesViewModel.class);
        // TODO: Use the ViewModel
    }

}