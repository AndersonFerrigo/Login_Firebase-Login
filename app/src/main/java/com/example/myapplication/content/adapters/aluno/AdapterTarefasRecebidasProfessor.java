package com.example.myapplication.content.adapters.aluno;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.DetalhesAtividadeActivity;
import com.example.myapplication.model.conteudo.ListarAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterTarefasRecebidasProfessor extends FirebaseRecyclerAdapter<ListarAtividadeConteudo, AdapterTarefasRecebidasProfessor.MyHolder>{

    /**
     * @since 22/09/2020
     * @param options
     */

    public AdapterTarefasRecebidasProfessor(@NonNull FirebaseRecyclerOptions<ListarAtividadeConteudo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, final int position, @NonNull final ListarAtividadeConteudo listarAtividadeConteudo) {

        holder.txtTituloNovaAtividadeRecebida.setText(listarAtividadeConteudo.getTitulo());
        holder.txtNomeProfessorTarefaRecebida.setText(listarAtividadeConteudo.getProfessor());
        holder.txtMateriaNovaAtividadeRecebida.setText(listarAtividadeConteudo.getMateria());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intentDetalhesConteudo = new Intent(v.getContext(), DetalhesAtividadeActivity.class);
                intentDetalhesConteudo.putExtra("atividade", listarAtividadeConteudo);
                v.getContext().startActivity(intentDetalhesConteudo);
            }
        });
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_atividades_recebidas_professor, parent, false);

        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView txtTituloNovaAtividadeRecebida;
        public TextView txtNomeProfessorTarefaRecebida;
        public TextView txtMateriaNovaAtividadeRecebida;

        public MyHolder(View v) {
            super(v);
            txtTituloNovaAtividadeRecebida = v.findViewById(R.id.titulo_nova_atividade_recebida);
            txtNomeProfessorTarefaRecebida = v.findViewById(R.id.nome_do_professor_ativ_recebida);
            txtMateriaNovaAtividadeRecebida = v.findViewById(R.id.nome_materia_atividade_recebida);
        }
    }
}
