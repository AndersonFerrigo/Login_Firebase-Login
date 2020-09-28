package com.example.myapplication.content.adapters.professor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.DetalhesConteudoActivity;
import com.example.myapplication.content.professor.DetalhesDuvidasAtividadeActivity;
import com.example.myapplication.model.duvidas.ListarDuvidasAlunoAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterDuvidasAtividadesAluno extends FirebaseRecyclerAdapter<ListarDuvidasAlunoAtividadeConteudo, AdapterDuvidasAtividadesAluno.MyHolder> {

    public AdapterDuvidasAtividadesAluno(@NonNull FirebaseRecyclerOptions<ListarDuvidasAlunoAtividadeConteudo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull final ListarDuvidasAlunoAtividadeConteudo duvidasAlunoAtividade) {
        holder.tituloDuvidaAtividadeAluno.setText(duvidasAlunoAtividade.getTitulo());
        holder.nomeAlunoDuvidaAtividade.setText(duvidasAlunoAtividade.getAluno());
        holder.turmaAlunoDuvidaAtividade.setText(duvidasAlunoAtividade.getTurma());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intentDetalhesConteudo = new Intent(v.getContext(), DetalhesDuvidasAtividadeActivity.class);
                intentDetalhesConteudo.putExtra("duvidas_atividades_concluidas", duvidasAlunoAtividade);
                v.getContext().startActivity(intentDetalhesConteudo);
            }
        });
    }

    @Override
    public AdapterDuvidasAtividadesAluno.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_duvidas_atividades_recebidas, parent, false);

        AdapterDuvidasAtividadesAluno.MyHolder duvidasAtividadeViewHolder = new AdapterDuvidasAtividadesAluno.MyHolder(view);
        return duvidasAtividadeViewHolder;
    }


    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tituloDuvidaAtividadeAluno;
        public TextView nomeAlunoDuvidaAtividade;
        public TextView turmaAlunoDuvidaAtividade;

        public MyHolder(View v) {
            super(v);
            tituloDuvidaAtividadeAluno = v.findViewById(R.id.titulo_atividade_duvida_aluno);
            nomeAlunoDuvidaAtividade = v.findViewById(R.id.nome_do_aluno_ativ_duvida);
            turmaAlunoDuvidaAtividade = v.findViewById(R.id.turma_aluno_atividade_duvida);
        }
    }

}
