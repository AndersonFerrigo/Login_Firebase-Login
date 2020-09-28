package com.example.myapplication.content.professor.ui.atividades_recebidas_aluno;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.adapters.professor.AdapterAtividadesConcluidasAluno;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.model.duvidas.ListarDuvidasAlunoAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AtividadesRecebidasAlunoFragment extends Fragment {
    /**
     * @since 18/09/2020
     *
     */
    private CadastroNovoUsuario professorUsuario;

    private AdapterAtividadesConcluidasAluno adapterAtividadesConcluidasAluno;
    private FirebaseRecyclerOptions<ListarDuvidasAlunoAtividadeConteudo> options;
    private Query queryBuscaAtividadesConcluidas;

    private View viewAtividadesConcluidasAluno;

    private RecyclerView recyclerViewAtividadesConcluidasAluno;

    private Spinner spnMateriaProfessor;
    private ImageView imgNotificacaoSemAtividadesConcluidas;
    private TextView txtInformaNotificacaoSemAtividadeConcluida;

    private String recebeMateriaProfessor;

    public AtividadesRecebidasAlunoFragment(){ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewAtividadesConcluidasAluno =  inflater.inflate(R.layout.fragment_atividades_recebidas_aluno, container, false);

        spnMateriaProfessor = viewAtividadesConcluidasAluno.findViewById(R.id.spn_materia_professor);
        recyclerViewAtividadesConcluidasAluno = viewAtividadesConcluidasAluno.findViewById(R.id.recycler_view_tarefas_recebidas);
        imgNotificacaoSemAtividadesConcluidas = viewAtividadesConcluidasAluno.findViewById(R.id.img_notifica_sem_atividade_concluida);
        txtInformaNotificacaoSemAtividadeConcluida = viewAtividadesConcluidasAluno.findViewById(R.id.txt_informa_sem_atividade_concluida);

        return viewAtividadesConcluidasAluno;
    }

    @Override
    public void onStart() {
        super.onStart();
        final ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle professor = getActivity().getIntent().getExtras();
        if(professor != null){
            professorUsuario = professor.getParcelable("professor");
            recebeMateriaProfessor = professorUsuario.getMateria();
        }else {
            Toast.makeText(getContext(), R.string.erro_informacoes_professor_bundle, Toast.LENGTH_LONG).show();
        }

        queryBuscaAtividadesConcluidas = FirebaseDatabase.getInstance().getReference().child("atividades_concluidas")
                .orderByChild("materia").equalTo(recebeMateriaProfessor);

        queryBuscaAtividadesConcluidas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imgNotificacaoSemAtividadesConcluidas.setVisibility(View.GONE);
                    txtInformaNotificacaoSemAtividadeConcluida.setVisibility(View.GONE);

                    options = new FirebaseRecyclerOptions.Builder<ListarDuvidasAlunoAtividadeConteudo>()
                    .setQuery(queryBuscaAtividadesConcluidas, new SnapshotParser<ListarDuvidasAlunoAtividadeConteudo>() {

                        @NonNull
                        @Override
                        public ListarDuvidasAlunoAtividadeConteudo parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new ListarDuvidasAlunoAtividadeConteudo(
                                snapshot.child("aluno").getValue().toString(),
                                snapshot.child("turma").getValue().toString(),
                                snapshot.child("titulo").getValue().toString(),
                                snapshot.child("documento").getValue().toString()
                            );
                        }
                    }).build();

                    recyclerViewAtividadesConcluidasAluno.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterAtividadesConcluidasAluno = new AdapterAtividadesConcluidasAluno(options);
                    recyclerViewAtividadesConcluidasAluno.setAdapter(adapterAtividadesConcluidasAluno);
                    adapterAtividadesConcluidasAluno.startListening();
                }else{
                    recyclerViewAtividadesConcluidasAluno.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() { super.onStop(); }
}