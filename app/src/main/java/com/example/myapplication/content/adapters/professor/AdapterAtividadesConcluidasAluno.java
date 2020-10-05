package com.example.myapplication.content.adapters.professor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.professor.DetalhesAtividadesRecebidasAlunoActivity;
import com.example.myapplication.model.duvidas.ListarDuvidasAlunoAtividadeConteudo;
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
        holder.txtTituloAtividadeConcluida.setText(alunoAtividadeConteudo.getTitulo());
        holder.txtNomeAlunoAtividadeConcluida.setText(alunoAtividadeConteudo.getAluno());
        holder.txtTurmaAtividadeConcluida.setText(alunoAtividadeConteudo.getTurma());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intentDetalhesConteudo = new Intent(v.getContext(), DetalhesAtividadesRecebidasAlunoActivity.class);
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

        public TextView txtTituloAtividadeConcluida;
        public TextView txtNomeAlunoAtividadeConcluida;
        public TextView txtTurmaAtividadeConcluida;

        public MyHolder(View v) {
            super(v);
            txtTituloAtividadeConcluida = v.findViewById(R.id.titulo_atividade_concluida_aluno);
            txtNomeAlunoAtividadeConcluida = v.findViewById(R.id.nome_do_aluno_atividade_concluida);
            txtTurmaAtividadeConcluida = v.findViewById(R.id.turma_aluno_atividade_concluida);
        }
    }
}
