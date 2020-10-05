package com.example.myapplication.content.professor.ui.duvidas_alunos_atividades;

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
import com.example.myapplication.content.adapters.professor.AdapterDuvidasAtividadesAluno;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.model.duvidas.ListarDuvidasAlunoAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DuvidasAlunosAtividadesFragment extends Fragment {
    /**
     * @since 26/09/2020
     */
    private CadastroNovoUsuario professorUsuario;

    private String recebeMateriaProfessor;

    private View alunoDuvidasAtividades;
    private RecyclerView recyclerViewDuvidasAtividadesRecebidas;

    private Spinner spnMateriaProfessor;
    private ImageView imgNotificacaoSemDuvidasAtividades;
    private TextView txtInformaNotificacaoSemDuvidasAtividade;

    private AdapterDuvidasAtividadesAluno adapterDuvidasAtividadesAluno;
    private Query queryListarDuvidasAtividadesAluno;
    private FirebaseRecyclerOptions options;

    public DuvidasAlunosAtividadesFragment (){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        alunoDuvidasAtividades =  inflater.inflate(R.layout.duvidas_alunos_atividades_fragment, container, false);

        spnMateriaProfessor = alunoDuvidasAtividades.findViewById(R.id.spn_materia_professor);
        recyclerViewDuvidasAtividadesRecebidas = alunoDuvidasAtividades.findViewById(R.id.recycler_view_duvidas_aluno_tarefas);
        imgNotificacaoSemDuvidasAtividades = alunoDuvidasAtividades.findViewById(R.id.img_notifica_sem_duvidas_atividade);
        txtInformaNotificacaoSemDuvidasAtividade = alunoDuvidasAtividades.findViewById(R.id.txt_informa_sem_duvidas_atividade);

        return alunoDuvidasAtividades;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        final ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);

         */
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

        queryListarDuvidasAtividadesAluno = FirebaseDatabase.getInstance().getReference().child("duvidas_atividades_concluidas")
                .orderByChild("materia").equalTo(recebeMateriaProfessor);

        queryListarDuvidasAtividadesAluno.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imgNotificacaoSemDuvidasAtividades.setVisibility(View.GONE);
                    txtInformaNotificacaoSemDuvidasAtividade.setVisibility(View.GONE);

                    options = new FirebaseRecyclerOptions.Builder<ListarDuvidasAlunoAtividadeConteudo>()
                    .setQuery(queryListarDuvidasAtividadesAluno, new SnapshotParser<ListarDuvidasAlunoAtividadeConteudo>() {

                    @NonNull
                    @Override
                        public ListarDuvidasAlunoAtividadeConteudo parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new ListarDuvidasAlunoAtividadeConteudo(
                                snapshot.child("aluno").getValue().toString(),
                                snapshot.child("turma").getValue().toString(),
                                snapshot.child("titulo").getValue().toString(),
                                snapshot.child("duvida").getValue().toString(),
                                snapshot.child("documento").getValue().toString()
                            );
                        }
                    }).build();

                    recyclerViewDuvidasAtividadesRecebidas.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterDuvidasAtividadesAluno = new AdapterDuvidasAtividadesAluno(options);
                    recyclerViewDuvidasAtividadesRecebidas.setAdapter(adapterDuvidasAtividadesAluno);
                    adapterDuvidasAtividadesAluno.startListening();
                }else{
                    recyclerViewDuvidasAtividadesRecebidas.setVisibility(View.GONE);
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
