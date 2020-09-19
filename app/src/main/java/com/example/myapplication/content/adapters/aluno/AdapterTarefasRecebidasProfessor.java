package com.example.myapplication.content.adapters.aluno;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.DetalhesAtividadeActivity;
import com.example.myapplication.content.aluno.DetalhesConteudoActivity;
import com.example.myapplication.model.ListarAtividadeConteudo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterTarefasRecebidasProfessor extends FirebaseRecyclerAdapter<ListarAtividadeConteudo, AdapterTarefasRecebidasProfessor.MyHolder>{

    public AdapterTarefasRecebidasProfessor(@NonNull FirebaseRecyclerOptions<ListarAtividadeConteudo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, final int position, @NonNull final ListarAtividadeConteudo listarAtividadeConteudo) {

        holder.tituloTarefasRecebidas.setText(listarAtividadeConteudo.getTitulo());
        holder.nomeAlunoTarefaRecebida.setText(listarAtividadeConteudo.getProfessor());
        holder.materiaTarefaRecebida.setText(listarAtividadeConteudo.getMateria());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                /*
                    Logica para recuperar uma atividade especifica do banco de dados

                 */
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

            public TextView tituloTarefasRecebidas;
            public TextView nomeAlunoTarefaRecebida;
            public TextView materiaTarefaRecebida;

            public MyHolder(View v) {
                super(v);
                tituloTarefasRecebidas = v.findViewById(R.id.titulo_nova_atividade_recebida);
                nomeAlunoTarefaRecebida = v.findViewById(R.id.nome_do_aluno_ativ_recebida);
                materiaTarefaRecebida = v.findViewById(R.id.nome_materia_atividade_recebida);
            }
        }
}
