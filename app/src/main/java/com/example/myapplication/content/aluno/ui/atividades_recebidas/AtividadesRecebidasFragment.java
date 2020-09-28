package com.example.myapplication.content.aluno.ui.atividades_recebidas;

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
import com.example.myapplication.content.adapters.aluno.AdapterTarefasRecebidasProfessor;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.model.conteudo.ListarAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AtividadesRecebidasFragment extends Fragment {

    /**
     * @since 22/09/2020
     */

    private CadastroNovoUsuario alunoUsuario;

    private String recebeTurmaBundle;

    private ImageView imgNotificacaoSemNovasAtividadesRecebidas;
    private TextView txtInformaNotificacaoSemNovasAtividadeRecebidas;
    private Spinner spnMateriaProfessor;

    private View atividadesRecebidas;

    private RecyclerView recyclerViewAtividadesRecebidas;
    private AdapterTarefasRecebidasProfessor adapterTarefasRecebidasProfessor;

    private Query queryBuscaNovasAtividades;
    private FirebaseRecyclerOptions options;

    public AtividadesRecebidasFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        atividadesRecebidas = inflater.inflate(R.layout.fragment_atividades_recebidas, container, false);

        spnMateriaProfessor = atividadesRecebidas.findViewById(R.id.spn_materia_professor);
        recyclerViewAtividadesRecebidas = atividadesRecebidas.findViewById(R.id.recycler_view_tarefas_aluno);
        imgNotificacaoSemNovasAtividadesRecebidas = atividadesRecebidas.findViewById(R.id.img_notifica_sem_novas_atividade);
        txtInformaNotificacaoSemNovasAtividadeRecebidas = atividadesRecebidas.findViewById(R.id.txt_informa_sem_novas_atividade);

        return atividadesRecebidas;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);
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

        queryBuscaNovasAtividades = FirebaseDatabase.getInstance().getReference()
                .child("atividades_adicionadas")
                .orderByChild("turma")
                .equalTo(recebeTurmaBundle);

        queryBuscaNovasAtividades.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imgNotificacaoSemNovasAtividadesRecebidas.setVisibility(View.GONE);
                    txtInformaNotificacaoSemNovasAtividadeRecebidas.setVisibility(View.GONE);

                    options =  new FirebaseRecyclerOptions.Builder<ListarAtividadeConteudo>()
                    .setQuery(queryBuscaNovasAtividades, new SnapshotParser<ListarAtividadeConteudo>() {
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

                    recyclerViewAtividadesRecebidas.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterTarefasRecebidasProfessor = new AdapterTarefasRecebidasProfessor(options);
                    recyclerViewAtividadesRecebidas.setAdapter(adapterTarefasRecebidasProfessor);
                    adapterTarefasRecebidasProfessor.startListening();

                }else{
                    recyclerViewAtividadesRecebidas.setVisibility(View.GONE);
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
    public void onStop() {
        super.onStop();
    }

}