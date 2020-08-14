package com.example.myapplication.content.professor.ui.atividades_recebidas_aluno;

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
import com.example.myapplication.content.adapters.aluno.ListAdapterNovasTarefasAluno;

public class AtividadesRecebidasAlunoFragment extends Fragment {

    View viewAtividadesRecebidasProfessor;
    RecyclerView recyclerViewNovosConteudos;

    private Spinner spnMateriaProfessor;

    private AtividadesRecebidasAlunoViewModel mViewModel;

    public static AtividadesRecebidasAlunoFragment newInstance() {
        return new AtividadesRecebidasAlunoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         viewAtividadesRecebidasProfessor =  inflater.inflate(R.layout.fragment_atividades_recebidas_aluno, container, false);


        spnMateriaProfessor = viewAtividadesRecebidasProfessor.findViewById(R.id.spn_materia_professor);
        // Carrega o conteudo do spinner materia do professor
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);


        recyclerViewNovosConteudos = viewAtividadesRecebidasProfessor.findViewById(R.id.recycler_view_tarefas_recebidas);

        recyclerViewNovosConteudos.setAdapter(new ListAdapterNovasTarefasAluno(getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerViewNovosConteudos.setLayoutManager(layoutManager);



        return viewAtividadesRecebidasProfessor;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AtividadesRecebidasAlunoViewModel.class);
        // TODO: Use the ViewModel
    }

}