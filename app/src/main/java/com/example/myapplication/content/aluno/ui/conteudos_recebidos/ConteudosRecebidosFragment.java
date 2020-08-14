package com.example.myapplication.content.aluno.ui.conteudos_recebidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.adapters.aluno.ListAdapterNovasTarefasAluno;


public class ConteudosRecebidosFragment extends Fragment {
    View viewConteudosRecebidos;
    RecyclerView recyclerViewNovosConteudos;

    private Spinner spnMateriaProfessor;
    private ConteudosRecebidosViewModel conteudosRecebidosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewConteudosRecebidos = inflater.inflate(R.layout.fragment_conteudo_recebido, container, false);


        spnMateriaProfessor = viewConteudosRecebidos.findViewById(R.id.spn_materia_professor);
        // Carrega o conteudo do spinner materia do professor
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);


        recyclerViewNovosConteudos = viewConteudosRecebidos.findViewById(R.id.recycler_view_tarefas_aluno);

        recyclerViewNovosConteudos.setAdapter(new ListAdapterNovasTarefasAluno(getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerViewNovosConteudos.setLayoutManager(layoutManager);

        return viewConteudosRecebidos;
    }
}