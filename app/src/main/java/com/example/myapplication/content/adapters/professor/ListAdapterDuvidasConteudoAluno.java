package com.example.myapplication.content.adapters.professor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.professor.ui.duvidas_alunos_conteudos.DuvidasAlunosConteudoViewModel;

import java.util.List;

public class ListAdapterDuvidasConteudoAluno extends RecyclerView.Adapter<ListAdapterDuvidasConteudoAluno.MyHolder>{

    //Bundle bundle;
    String duvidasConteudoAlunoId;

    private DuvidasAlunosConteudoViewModel duvidasConteudoConcluidasViewModel;
    private List<DuvidasAlunosConteudoViewModel> duvidasConteudoConcluidas;
    private Context context;

    public ListAdapterDuvidasConteudoAluno(List<DuvidasAlunosConteudoViewModel> conteudosConcluidasVM, Context context) {
        this.duvidasConteudoConcluidas = conteudosConcluidasVM;
        this.context = context;
    }

    public ListAdapterDuvidasConteudoAluno(Context context) {
        this.context = context;
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public ListAdapterDuvidasConteudoAluno.MyHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_duvidas_conteudo_recebidos, parent, false);

        ListAdapterDuvidasConteudoAluno.MyHolder duvidasConteudoViewHolder = new ListAdapterDuvidasConteudoAluno.MyHolder(view);

        return duvidasConteudoViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterDuvidasConteudoAluno.MyHolder viewHolder, final int position) {
       /*
        atividadesRecebidasViewModel = atividades.get(position);
        ListAdapterNovasTarefasAluno.MyHolder atividadeViewHolder = viewHolder;

        atividadeViewHolder.tituloNovaAtividade.setText(atividadesRecebidasViewModel.getTituloNovaAtividade());
        atividadeViewHolder.nomeProfessorNovaAtividade.setText(atividadesRecebidasViewModel.getNomeProfessorNovaAtividade());
        atividadeViewHolder.materiaNovaAtividade.setText(atividadesRecebidasViewModel.getMateriaNovaAtividade());


        */
        ListAdapterDuvidasConteudoAluno.MyHolder duvidasConteudoViewHolder = viewHolder;

        duvidasConteudoViewHolder.tituloDuvidaConteudoAluno.setText("Sujeito Composto");
        duvidasConteudoViewHolder.nomeAlunoDuvidaConteudo.setText("Emanuel de Souza");
        duvidasConteudoViewHolder.materiaAlunoDuvidaConteudo.setText("Português");
        duvidasConteudoViewHolder.turmaAlunoDuvidaConteudo.setText("6° Ensino Fundamental");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                /*
                    Logica para recuperar uma atividade especifica do banco de dados
                 */

                duvidasConteudoAlunoId = String.valueOf(duvidasConteudoConcluidas.get(position).getuIDAlunoConteudoDuvida());
                String id = duvidasConteudoAlunoId;

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
