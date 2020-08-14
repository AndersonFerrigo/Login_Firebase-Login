package com.example.myapplication.content.adapters.aluno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.ui.atividades_recebidas.AtividadesRecebidasViewModel;

import java.util.List;

public class ListAdapterNovasTarefasAluno extends RecyclerView.Adapter<ListAdapterNovasTarefasAluno.MyHolder> {

    //Bundle bundle;
    String atividadeId;

    private AtividadesRecebidasViewModel atividadesRecebidasViewModel;
    private List<AtividadesRecebidasViewModel> atividades;
    private Context context;

    public ListAdapterNovasTarefasAluno(List<AtividadesRecebidasViewModel> atividade, Context context) {
        this.atividades = atividade;
        this.context = context;
    }

    public ListAdapterNovasTarefasAluno(Context context) {
        this.context = context;
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tituloNovaAtividade;
        public TextView nomeProfessorNovaAtividade;
        public TextView materiaNovaAtividade;

        public MyHolder(View v) {
            super(v);
            tituloNovaAtividade = v.findViewById(R.id.titulo_nova_atividade);
            nomeProfessorNovaAtividade = v.findViewById(R.id.nome_do_professor);
            materiaNovaAtividade = v.findViewById(R.id.nome_materia_atividade);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public ListAdapterNovasTarefasAluno.MyHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_nova_tarefa_aluno, parent, false);

        ListAdapterNovasTarefasAluno.MyHolder atividadeViewHolder = new ListAdapterNovasTarefasAluno.MyHolder(view);

        return atividadeViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterNovasTarefasAluno.MyHolder viewHolder, final int position) {
       /*
        atividadesRecebidasViewModel = atividades.get(position);
        ListAdapterNovasTarefasAluno.MyHolder atividadeViewHolder = viewHolder;

        atividadeViewHolder.tituloNovaAtividade.setText(atividadesRecebidasViewModel.getTituloNovaAtividade());
        atividadeViewHolder.nomeProfessorNovaAtividade.setText(atividadesRecebidasViewModel.getNomeProfessorNovaAtividade());
        atividadeViewHolder.materiaNovaAtividade.setText(atividadesRecebidasViewModel.getMateriaNovaAtividade());


        */
        ListAdapterNovasTarefasAluno.MyHolder atividadeViewHolder = viewHolder;

        atividadeViewHolder.tituloNovaAtividade.setText("Equações");
        atividadeViewHolder.nomeProfessorNovaAtividade.setText("Marcos Afonso");
        atividadeViewHolder.materiaNovaAtividade.setText("Matemática");

        atividadeViewHolder.tituloNovaAtividade.setText("Funções");
        atividadeViewHolder.nomeProfessorNovaAtividade.setText("Marcos Afonso");
        atividadeViewHolder.materiaNovaAtividade.setText("Matemática");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                /*
                    Logica para recuperar uma atividade especifica do banco de dados
                 */

                atividadeId = String.valueOf(atividades.get(position).getuID());
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


