package com.example.myapplication.content.aluno.ui.atividades_recebidas;

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
import com.example.myapplication.content.adapters.aluno.ListAdapterTarefasRecebidasProfessor;

public class AtividadesRecebidasFragment extends Fragment {

    private Spinner spnMateriaProfessor;
    View atividadesRecebidas;
    RecyclerView recyclerViewAtividadesRecebidas;

    private AtividadesRecebidasViewModel atividadesRecebidasViewModel;

    public AtividadesRecebidasFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        atividadesRecebidas = inflater.inflate(R.layout.fragment_atividades_recebidas, container, false);

        spnMateriaProfessor = atividadesRecebidas.findViewById(R.id.spn_materia_professor);

        // Carrega o conteudo do spinner materia do professor
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);


        recyclerViewAtividadesRecebidas = atividadesRecebidas.findViewById(R.id.recycler_view_tarefas_aluno);

        recyclerViewAtividadesRecebidas.setAdapter(new ListAdapterTarefasRecebidasProfessor(getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                                                              LinearLayoutManager.VERTICAL, false);

        recyclerViewAtividadesRecebidas.setLayoutManager(layoutManager);

        return atividadesRecebidas;
    }
}