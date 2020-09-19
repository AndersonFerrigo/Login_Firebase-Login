package com.example.myapplication.content.adapters.professor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.DetalhesConteudoActivity;
import com.example.myapplication.model.ListarDuvidasAlunoAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterAtividadesConcluidasAluno extends FirebaseRecyclerAdapter<ListarDuvidasAlunoAtividadeConteudo, AdapterAtividadesConcluidasAluno.MyHolder> {

    /**
     * @since 18/09/2020
     * @param options
     */
    public AdapterAtividadesConcluidasAluno(@NonNull FirebaseRecyclerOptions<ListarDuvidasAlunoAtividadeConteudo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull final ListarDuvidasAlunoAtividadeConteudo alunoAtividadeConteudo) {

        holder.tituloAtividadeConcluida.setText(alunoAtividadeConteudo.getTitulo());
        holder.nomeAlunoAtividadeConcluida.setText(alunoAtividadeConteudo.getAluno());
        holder.materiaAlunoAtividadeConcluida.setText(alunoAtividadeConteudo.getMateria());
        holder.turmaAlunoAtividadeConcluida.setText(alunoAtividadeConteudo.getTurma());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intentDetalhesConteudo = new Intent(v.getContext(), DetalhesConteudoActivity.class);
                intentDetalhesConteudo.putExtra("atividades_concluidas", alunoAtividadeConteudo);
                v.getContext().startActivity(intentDetalhesConteudo);
            }
        });
    }

    @Override
    public AdapterAtividadesConcluidasAluno.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_atividades_concluidas_aluno, parent, false);

        AdapterAtividadesConcluidasAluno.MyHolder conteudoViewHolder = new AdapterAtividadesConcluidasAluno.MyHolder(view);
        return conteudoViewHolder;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView tituloAtividadeConcluida;
        public TextView nomeAlunoAtividadeConcluida;
        public TextView materiaAlunoAtividadeConcluida;
        public TextView turmaAlunoAtividadeConcluida;

        public MyHolder(View v) {
            super(v);
            tituloAtividadeConcluida = v.findViewById(R.id.titulo_atividade_concluida_aluno);
            nomeAlunoAtividadeConcluida = v.findViewById(R.id.nome_do_aluno_atividade_concluida);
            materiaAlunoAtividadeConcluida = v.findViewById(R.id.materia_aluno_atividade_concluida);
            turmaAlunoAtividadeConcluida = v.findViewById(R.id.turma_aluno_atividade_concluida);
        }
    }
}
