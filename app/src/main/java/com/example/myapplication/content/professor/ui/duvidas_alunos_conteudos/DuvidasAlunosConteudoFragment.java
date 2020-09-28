package com.example.myapplication.content.professor.ui.duvidas_alunos_conteudos;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.content.adapters.professor.AdapterDuvidasConteudoAluno;
import com.example.myapplication.model.login.CadastroNovoUsuario;
import com.example.myapplication.model.duvidas.ListarDuvidasAlunoAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DuvidasAlunosConteudoFragment extends Fragment {
    /**
     * @since 26/09/2020
     */
    private  View duvidasConteudoAluno;
    private RecyclerView recyclerViewDuvidasAtividadesRecebidas;
    private AdapterDuvidasConteudoAluno adapterDuvidasConteudoAluno;

    private CadastroNovoUsuario professorUsuario;

    private String recebeMateriaProfessor;

    private Spinner spnMateriaProfessor;
    private ImageView imgNotificacaoSemDuvidasConteudo;
    private TextView txtInformaNotificacaoSemDuvidasConteudo;

    private Query queryListarDuvidasConteudoAluno;
    private FirebaseRecyclerOptions options;

    public DuvidasAlunosConteudoFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        duvidasConteudoAluno = inflater.inflate(R.layout.duvidas_alunos_conteudo_fragment, container, false);

        spnMateriaProfessor = duvidasConteudoAluno.findViewById(R.id.spn_materia_professor);
        recyclerViewDuvidasAtividadesRecebidas = duvidasConteudoAluno.findViewById(R.id.recycler_view_duvidas_aluno_conteudo);
        imgNotificacaoSemDuvidasConteudo = duvidasConteudoAluno.findViewById(R.id.img_notifica_sem_duvidas_conteudo);
        txtInformaNotificacaoSemDuvidasConteudo = duvidasConteudoAluno.findViewById(R.id.txt_informa_sem_duvidas_conteudo);

        return duvidasConteudoAluno;
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
        Bundle professor = getActivity().getIntent().getExtras();
        if(professor != null){
            professorUsuario = professor.getParcelable("professor");
            recebeMateriaProfessor = professorUsuario.getMateria();
        }else {
            Toast.makeText(getContext(), R.string.erro_informacoes_professor_bundle, Toast.LENGTH_LONG).show();
        }

        queryListarDuvidasConteudoAluno = FirebaseDatabase.getInstance().getReference().child("duvidas_conteudo_aulas")
                .orderByChild("materia").equalTo(recebeMateriaProfessor);

        queryListarDuvidasConteudoAluno.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imgNotificacaoSemDuvidasConteudo.setVisibility(View.GONE);
                    txtInformaNotificacaoSemDuvidasConteudo.setVisibility(View.GONE);

                    options = new FirebaseRecyclerOptions.Builder<ListarDuvidasAlunoAtividadeConteudo>()
                    .setQuery(queryListarDuvidasConteudoAluno, new SnapshotParser<ListarDuvidasAlunoAtividadeConteudo>() {

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
                    adapterDuvidasConteudoAluno = new AdapterDuvidasConteudoAluno(options);
                    recyclerViewDuvidasAtividadesRecebidas.setAdapter(adapterDuvidasConteudoAluno);
                    adapterDuvidasConteudoAluno.startListening();
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