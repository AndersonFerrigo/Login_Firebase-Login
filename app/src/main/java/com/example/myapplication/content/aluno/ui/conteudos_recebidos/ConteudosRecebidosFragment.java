package com.example.myapplication.content.aluno.ui.conteudos_recebidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.adapters.aluno.AdapterConteudosRecebidoProfessor;
import com.example.myapplication.model.CadastroNovoUsuario;
import com.example.myapplication.model.ListarAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;


public class ConteudosRecebidosFragment extends Fragment {
    private CadastroNovoUsuario alunoUsuario;
    private String recebeTurmaBundle;
    private View viewConteudosRecebidos;
    private RecyclerView recyclerViewNovosConteudos;
    private Spinner spnMateriaProfessor;
    AdapterConteudosRecebidoProfessor adapterConteudosRecebidoProfessor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewConteudosRecebidos = inflater.inflate(R.layout.fragment_conteudo_recebido, container, false);
        spnMateriaProfessor = viewConteudosRecebidos.findViewById(R.id.spn_materia_professor);
        recyclerViewNovosConteudos = viewConteudosRecebidos.findViewById(R.id.recycler_view_tarefas_aluno);

        ArrayAdapter arrayAdapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        arrayAdapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(arrayAdapterMateriaProfessor);

        Bundle aluno = getActivity().getIntent().getExtras();
        if(aluno.isEmpty()){
            Toast.makeText(getContext(), "Erro ao recuperar informações do aluno", Toast.LENGTH_LONG).show();
        }else {
            alunoUsuario = aluno.getParcelable("aluno");
            recebeTurmaBundle = alunoUsuario.getTurma();
            Toast.makeText(getContext(), "Turma = " + recebeTurmaBundle , Toast.LENGTH_SHORT).show();

        }

        FirebaseRecyclerOptions<ListarAtividadeConteudo> options =
                new FirebaseRecyclerOptions.Builder<ListarAtividadeConteudo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("conteudos_adicionados")
                                        .orderByChild("turmaProfessor")
                                        .equalTo(recebeTurmaBundle)
                                , new SnapshotParser<ListarAtividadeConteudo>() {
                                    @NonNull
                                    @Override
                                    public ListarAtividadeConteudo parseSnapshot(@NonNull DataSnapshot snapshot) {
                                        return new ListarAtividadeConteudo(
                                                snapshot.child("descriçãoNovoConteudoProfessor").getValue().toString(),
                                                snapshot.child("materiaProfessor").getValue().toString(),
                                                snapshot.child("nomeProfessor").getValue().toString(),
                                                snapshot.child("pathDocumentoNovoConteudoProfessor").getValue().toString(),
                                                snapshot.child("tituloNovoConteudoProfessor").getValue().toString()

                                        );
                                    }
                                })
                        .build();

        recyclerViewNovosConteudos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterConteudosRecebidoProfessor = new AdapterConteudosRecebidoProfessor(options);
        recyclerViewNovosConteudos.setAdapter(adapterConteudosRecebidoProfessor);

        return viewConteudosRecebidos;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterConteudosRecebidoProfessor.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterConteudosRecebidoProfessor.stopListening();
    }
}