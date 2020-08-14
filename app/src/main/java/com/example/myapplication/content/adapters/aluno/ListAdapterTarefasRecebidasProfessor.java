package com.example.myapplication.content.adapters.aluno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.professor.ui.atividades_recebidas_aluno.AtividadesRecebidasAlunoViewModel;

import java.util.List;

public class ListAdapterTarefasRecebidasProfessor extends RecyclerView.Adapter<ListAdapterTarefasRecebidasProfessor.MyHolder> {
    String atividadeId;

    private AtividadesRecebidasAlunoViewModel atividadesRecebidasAlunoViewModel;
    private List<AtividadesRecebidasAlunoViewModel> atividadesRecebidas;
    private Context context;

    public ListAdapterTarefasRecebidasProfessor(List<AtividadesRecebidasAlunoViewModel> atividades, Context context) {
        this.atividadesRecebidas = atividades;
        this.context = context;
    }

    public ListAdapterTarefasRecebidasProfessor(Context context) {
        this.context = context;
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tituloTarefasRecebidas;
        public TextView nomeAlunoTarefaRecebida;
        public TextView materiaTarefaRecebida;

        public MyHolder(View v) {
            super(v);
            tituloTarefasRecebidas = v.findViewById(R.id.titulo_nova_atividade_recebida);
            nomeAlunoTarefaRecebida = v.findViewById(R.id.nome_do_aluno_ativ_recebida);
            materiaTarefaRecebida = v.findViewById(R.id.nome_materia_atividade_recebida);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public ListAdapterTarefasRecebidasProfessor.MyHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_atividades_recebidas_professor, parent, false);

        ListAdapterTarefasRecebidasProfessor.MyHolder atividadesRecebidas = new ListAdapterTarefasRecebidasProfessor.MyHolder(view);

        return atividadesRecebidas;
    }

    @Override
    public void onBindViewHolder(ListAdapterTarefasRecebidasProfessor.MyHolder viewHolder, final int position) {

        /*
        conteudosRecebidosViewModel = conteudos.get(position);
        MyHolder conteudoViewHolder = viewHolder;

        conteudoViewHolder.tituloNovoConteudo.setText(conteudosRecebidosViewModel.getTituloNovConteudo());
        conteudoViewHolder.nomeProfessorNovoConteudo.setText(conteudosRecebidosViewModel.getNomeProfessorNovoConteudo());
        conteudoViewHolder.materiaNovoConteudo.setText(conteudosRecebidosViewModel.getMateriaNovoConteudo());


        */
        MyHolder tarefasRecebidasViewHolder = viewHolder;

        tarefasRecebidasViewHolder.tituloTarefasRecebidas.setText("Equações");
        tarefasRecebidasViewHolder.nomeAlunoTarefaRecebida.setText("Marcos Afonso");
        tarefasRecebidasViewHolder.materiaTarefaRecebida.setText("Matemática");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                /*
                    Logica para recuperar uma atividade especifica do banco de dados
                 */

                atividadeId = String.valueOf(atividadesRecebidas.get(position).getuID());
                String id = atividadeId;

            /*
                Call<Atividade> buscaAtividadeId = new RetrofitConfig().getAtividadePeloId().buscarAtividadePeloId(id);
                buscaAtividadeId.enqueue(new Callback<Atividade>() {
                    @Override
                    public void onResponse(Call<Atividade> call, Response<Atividade> response) {
                        atividade = response.body();
                        if (atividade != null) {
                            bundle = new Bundle();
                            bundle.putParcelable("atividade", atividade);
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(v.getRootView(), "Atividade não encontrada", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(Color.WHITE);
                            snackbar.show();
                        }

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Fragment fragment = new MostraAtividadeAluno();
                        fragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.containerForFragmentAluno, fragment).addToBackStack(null).commit();
                    }

                    @Override
                    public void onFailure(Call<Atividade> call, Throwable t) {
                        Snackbar snackbar = Snackbar
                                .make(v.getRootView(), "Atividade não encontrada", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);
                        snackbar.show();
                    }
                });
            }
        */
            }
        });

    }

    public int getItemCount() {
        // retorna a quantidade de atividades cadastradas
        //return atividades.size();
        return 7;
    }
}
