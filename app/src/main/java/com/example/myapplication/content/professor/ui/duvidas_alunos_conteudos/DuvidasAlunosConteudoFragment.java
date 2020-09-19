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
import com.example.myapplication.content.adapters.professor.AdapterDuvidasAtividadesAluno;
import com.example.myapplication.content.adapters.professor.AdapterDuvidasConteudoAluno;
import com.example.myapplication.model.CadastroNovoUsuario;
import com.example.myapplication.model.ListarDuvidasAlunoAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DuvidasAlunosConteudoFragment extends Fragment {

    private  View duvidasConteudoAluno;
    private CadastroNovoUsuario professorUsuario;
    private String recebeMateriaProfessor;
    private Spinner spnMateriaProfessor;
    private Query queryListarDuvidasConteudoAluno;
    private FirebaseRecyclerOptions options;
    private ImageView imgNotificacaoSemDuvidasConteudo;
    private TextView txtInformaNotificacaoSemDuvidasConteudo;
    private RecyclerView recyclerViewDuvidasAtividadesRecebidas;
    private AdapterDuvidasConteudoAluno adapterDuvidasConteudoAluno;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle professor = getActivity().getIntent().getExtras();
        if(professor.isEmpty()){
            Toast.makeText(getContext(), "Erro ao recuperar informações do professor", Toast.LENGTH_LONG).show();
        }else {
            professorUsuario = professor.getParcelable("professor");
            recebeMateriaProfessor = professorUsuario.getMateria();
        }

        // Carrega o conteudo do spinner materia do professor
        ArrayAdapter adapterMateriaProfessor = ArrayAdapter.createFromResource(getContext(),
                R.array.materias_escola, R.layout.spinner_text_adapter);
        adapterMateriaProfessor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMateriaProfessor.setAdapter(adapterMateriaProfessor);

        queryListarDuvidasConteudoAluno = FirebaseDatabase.getInstance().getReference().child("duvidas_conteudo_aluno")
                .orderByChild("materiaProfessor").equalTo(recebeMateriaProfessor);



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
                                            snapshot.child("tituloAtividadeConcluidaAluno").getValue().toString(),
                                            snapshot.child("nomeAluno").getValue().toString(),
                                            snapshot.child("turmaAluno").getValue().toString(),
                                            snapshot.child("pathDocumentoAtividadeConcluidaAluno").getValue().toString());

                                }

                            }).build();

                    recyclerViewDuvidasAtividadesRecebidas.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterDuvidasConteudoAluno = new AdapterDuvidasConteudoAluno(options);
                    recyclerViewDuvidasAtividadesRecebidas.setAdapter(adapterDuvidasConteudoAluno);
                    adapterDuvidasConteudoAluno.startListening();
                }else{
                    //Caso não tenha nenhuma atividade deixo a RecyclerView invisivel e coloco imagem informando no lugar.
                    recyclerViewDuvidasAtividadesRecebidas.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }
    @Override
    public void onStart() { super.onStart(); }

    @Override
    public void onStop() { super.onStop(); }

}