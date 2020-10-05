package com.example.myapplication.content.adapters.aluno;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.DetalhesConteudoActivity;
import com.example.myapplication.model.conteudo.ListarAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterConteudosRecebidoProfessor extends FirebaseRecyclerAdapter<ListarAtividadeConteudo , AdapterConteudosRecebidoProfessor.MyHolder> {

    /**
     * @since 22/09/2020
     * @param options
     */

    public AdapterConteudosRecebidoProfessor(@NonNull FirebaseRecyclerOptions<ListarAtividadeConteudo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterConteudosRecebidoProfessor.MyHolder holder, final int position, @NonNull final ListarAtividadeConteudo listarAtividadeConteudo) {

        holder.tituloConteudoRecebido.setText(listarAtividadeConteudo.getTitulo());
        holder.nomeProfessorConteudoRecebido.setText(listarAtividadeConteudo.getProfessor());
        holder.materiaConteudoRecebido.setText(listarAtividadeConteudo.getMateria());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Intent intentDetalhesConteudo = new Intent(v.getContext(), DetalhesConteudoActivity.class);
                intentDetalhesConteudo.putExtra("conteudo", listarAtividadeConteudo);
                v.getContext().startActivity(intentDetalhesConteudo);
            }
        });
    }

    @Override
    public AdapterConteudosRecebidoProfessor.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_novo_conteudo_aluno, parent, false);

        return new AdapterConteudosRecebidoProfessor.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView tituloConteudoRecebido;
        public TextView nomeProfessorConteudoRecebido;
        public TextView materiaConteudoRecebido;

        public MyHolder(View v) {
            super(v);
            tituloConteudoRecebido = v.findViewById(R.id.titulo_novo_conteudo);
            nomeProfessorConteudoRecebido = v.findViewById(R.id.nome_do_professor_novo_conteudo);
            materiaConteudoRecebido = v.findViewById(R.id.materia_novo_conteudo);
        }
    }
}


