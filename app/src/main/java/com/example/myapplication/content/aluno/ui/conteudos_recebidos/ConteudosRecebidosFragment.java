package com.example.myapplication.content.aluno.ui.conteudos_recebidos;

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
import com.example.myapplication.content.adapters.aluno.AdapterConteudosRecebidoProfessor;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.model.conteudo.ListarAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConteudosRecebidosFragment extends Fragment {

    /**
     * @since 22/09/2020
     */

    private CadastroNovoUsuario alunoUsuario;

    private String recebeTurmaBundle;

    private View viewConteudosRecebidos;

    private RecyclerView recyclerViewNovosConteudos;
    private AdapterConteudosRecebidoProfessor adapterConteudosRecebidoProfessor;

    private ImageView imgNotificacaoSemNovosConteudosRecebidos;
    private TextView txtInformaNotificacaoSemNovosConteudosRecebidos;
    private Spinner spnMateriaProfessor;

    private Query queryBuscaNovosConteudos;
    private FirebaseRecyclerOptions options;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewConteudosRecebidos = inflater.inflate(R.layout.fragment_conteudo_recebido, container, false);

        spnMateriaProfessor = viewConteudosRecebidos.findViewById(R.id.spn_materia_professor);
        recyclerViewNovosConteudos = viewConteudosRecebidos.findViewById(R.id.recycler_view_tarefas_aluno);
        imgNotificacaoSemNovosConteudosRecebidos = viewConteudosRecebidos.findViewById(R.id.img_notifica_sem_novos_conteudos);
        txtInformaNotificacaoSemNovosConteudosRecebidos = viewConteudosRecebidos.findViewById(R.id.txt_informa_sem_novos_conteudos);

        return viewConteudosRecebidos;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter arrayAdapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        arrayAdapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(arrayAdapterMateriaProfessor);
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle aluno = getActivity().getIntent().getExtras();
        if(aluno != null){
            alunoUsuario = aluno.getParcelable("aluno");
            recebeTurmaBundle = alunoUsuario.getTurma();
        }else {
            Toast.makeText(getContext(), R.string.erro_informacoes_aluno_bundle, Toast.LENGTH_LONG).show();
        }

        queryBuscaNovosConteudos = FirebaseDatabase.getInstance().getReference()
                .child("conteudos_adicionados")
                .orderByChild("turma")
                .equalTo(recebeTurmaBundle);

        queryBuscaNovosConteudos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    imgNotificacaoSemNovosConteudosRecebidos.setVisibility(View.GONE);
                    txtInformaNotificacaoSemNovosConteudosRecebidos.setVisibility(View.GONE);
                    options = new FirebaseRecyclerOptions.Builder<ListarAtividadeConteudo>()
                            .setQuery(queryBuscaNovosConteudos, new SnapshotParser<ListarAtividadeConteudo>() {

                                @NonNull
                                @Override
                                public ListarAtividadeConteudo parseSnapshot(@NonNull DataSnapshot snapshot) {
                                    return new ListarAtividadeConteudo(
                                            snapshot.child("professor").getValue().toString(),
                                            snapshot.child("materia").getValue().toString(),
                                            snapshot.child("titulo").getValue().toString(),
                                            snapshot.child("descricao").getValue().toString(),
                                            snapshot.child("documento").getValue().toString()
                                    );
                                }
                            }).build();

                    recyclerViewNovosConteudos.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterConteudosRecebidoProfessor = new AdapterConteudosRecebidoProfessor(options);
                    recyclerViewNovosConteudos.setAdapter(adapterConteudosRecebidoProfessor);
                    adapterConteudosRecebidoProfessor.startListening();
                }else{
                    recyclerViewNovosConteudos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() { super.onStop(); }
}