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
import com.example.myapplication.model.ListarDuvidasAlunoAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterDuvidasConteudoAluno extends FirebaseRecyclerAdapter<ListarDuvidasAlunoAtividadeConteudo, AdapterDuvidasConteudoAluno.MyHolder> {

    public AdapterDuvidasConteudoAluno(@NonNull FirebaseRecyclerOptions<ListarDuvidasAlunoAtividadeConteudo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull final ListarDuvidasAlunoAtividadeConteudo duvidasAlunoConteudo) {
        holder.tituloDuvidaConteudoAluno.setText(duvidasAlunoConteudo.getTitulo());
        holder.nomeAlunoDuvidaConteudo.setText(duvidasAlunoConteudo.getAluno());
        holder.materiaAlunoDuvidaConteudo.setText(duvidasAlunoConteudo.getMateria());
        holder.turmaAlunoDuvidaConteudo.setText(duvidasAlunoConteudo.getTurma());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intentDetalhesConteudo = new Intent(v.getContext(), DetalhesConteudoActivity.class);
                intentDetalhesConteudo.putExtra("duvidas_conteudos_aulas", duvidasAlunoConteudo);
                v.getContext().startActivity(intentDetalhesConteudo);
            }
        });
    }

    @Override
    public AdapterDuvidasConteudoAluno.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_duvidas_conteudo_recebidos, parent, false);

        AdapterDuvidasConteudoAluno.MyHolder duvidasConteudoViewHolder = new AdapterDuvidasConteudoAluno.MyHolder(view);
        return duvidasConteudoViewHolder;
    }


    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tituloDuvidaConteudoAluno;
        public TextView nomeAlunoDuvidaConteudo;
        public TextView materiaAlunoDuvidaConteudo;
        public TextView turmaAlunoDuvidaConteudo;

        public MyHolder(View v) {
            super(v);
            tituloDuvidaConteudoAluno = v.findViewById(R.id.titulo_conteudo_duvida_aluno);
            nomeAlunoDuvidaConteudo = v.findViewById(R.id.nome_do_aluno_conteudo_duvida);
            materiaAlunoDuvidaConteudo = v.findViewById(R.id.nome_materia_conteudo_duvida);
            turmaAlunoDuvidaConteudo = v.findViewById(R.id.turma_aluno_conteudo_duvida);
        }
    }

}
