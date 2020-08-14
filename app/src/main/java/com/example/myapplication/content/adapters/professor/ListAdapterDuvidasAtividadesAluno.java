package com.example.myapplication.content.adapters.professor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.professor.ui.duvidas_alunos_atividades.DuvidasAlunosAtividadesViewModel;

import java.util.List;

public class ListAdapterDuvidasAtividadesAluno extends RecyclerView.Adapter<ListAdapterDuvidasAtividadesAluno.MyHolder> {

    //Bundle bundle;
    String duvidasAtividadesAlunoId;

    private DuvidasAlunosAtividadesViewModel duvidasAtividadesConcluidasViewModel;
    private List<DuvidasAlunosAtividadesViewModel> duvidasAtividadesConcluidas;
    private Context context;

    public ListAdapterDuvidasAtividadesAluno(List<DuvidasAlunosAtividadesViewModel> atividadeSConcluidasVM, Context context) {
        this.duvidasAtividadesConcluidas = atividadeSConcluidasVM;
        this.context = context;
    }

    public ListAdapterDuvidasAtividadesAluno(Context context) {
        this.context = context;
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tituloDuvidaAtividadeAluno;
        public TextView nomeAlunoDuvidaAtividade;
        public TextView materiaAlunoDuvidaAtividade;
        public TextView turmaAlunoDuvidaAtividade;

        public MyHolder(View v) {
            super(v);
            tituloDuvidaAtividadeAluno = v.findViewById(R.id.titulo_atividade_duvida_aluno);
            nomeAlunoDuvidaAtividade = v.findViewById(R.id.nome_do_aluno_ativ_duvida);
            materiaAlunoDuvidaAtividade = v.findViewById(R.id.nome_materia_atividade_duvida);
            turmaAlunoDuvidaAtividade = v.findViewById(R.id.turma_aluno_atividade_duvida);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public ListAdapterDuvidasAtividadesAluno.MyHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_duvidas_atividades_recebidas, parent, false);

        ListAdapterDuvidasAtividadesAluno.MyHolder duvidasAtividadeViewHolder = new ListAdapterDuvidasAtividadesAluno.MyHolder(view);

        return duvidasAtividadeViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterDuvidasAtividadesAluno.MyHolder viewHolder, final int position) {
       /*
        atividadesRecebidasViewModel = atividades.get(position);
        ListAdapterNovasTarefasAluno.MyHolder atividadeViewHolder = viewHolder;

        atividadeViewHolder.tituloNovaAtividade.setText(atividadesRecebidasViewModel.getTituloNovaAtividade());
        atividadeViewHolder.nomeProfessorNovaAtividade.setText(atividadesRecebidasViewModel.getNomeProfessorNovaAtividade());
        atividadeViewHolder.materiaNovaAtividade.setText(atividadesRecebidasViewModel.getMateriaNovaAtividade());


        */
        ListAdapterDuvidasAtividadesAluno.MyHolder duvidasAtividadeViewHolder = viewHolder;

        duvidasAtividadeViewHolder.tituloDuvidaAtividadeAluno.setText("Algebra Linear");
        duvidasAtividadeViewHolder.nomeAlunoDuvidaAtividade.setText("Jorge de Mello");
        duvidasAtividadeViewHolder.materiaAlunoDuvidaAtividade.setText("Matemática");
        duvidasAtividadeViewHolder.turmaAlunoDuvidaAtividade.setText("1° Ensino Médio");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                /*
                    Logica para recuperar uma atividade especifica do banco de dados
                 */

         duvidasAtividadesAlunoId = String.valueOf(duvidasAtividadesConcluidas.get(position).getuIDAlunoAtividadeDuvida());
                String id = duvidasAtividadesAlunoId;

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
