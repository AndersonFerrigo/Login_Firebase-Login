package com.example.myapplication.content.adapters.aluno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.content.aluno.ui.conteudos_recebidos.ConteudosRecebidosViewModel;

import java.util.List;

public class ListAdapterNovosConteudosAluno extends RecyclerView.Adapter<ListAdapterNovosConteudosAluno.MyHolder>{
    //Bundle bundle;
    String atividadeId;

    private ConteudosRecebidosViewModel conteudosRecebidosViewModel;
    private List<ConteudosRecebidosViewModel> conteudos;
    private Context context;

    public ListAdapterNovosConteudosAluno(List<ConteudosRecebidosViewModel> conteudo, Context context) {
        this.conteudos = conteudo;
        this.context = context;
    }

    public ListAdapterNovosConteudosAluno(Context context) {
        this.context = context;
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tituloNovoConteudo;
        public TextView nomeProfessorNovoConteudo;
        public TextView materiaNovoConteudo;

        public MyHolder(View v) {
            super(v);
            tituloNovoConteudo = v.findViewById(R.id.titulo_nova_atividade);
            nomeProfessorNovoConteudo = v.findViewById(R.id.nome_do_professor);
            materiaNovoConteudo = v.findViewById(R.id.nome_materia_atividade);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public ListAdapterNovosConteudosAluno.MyHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_nova_tarefa_aluno, parent, false);

        ListAdapterNovosConteudosAluno.MyHolder conteudoViewHolder = new ListAdapterNovosConteudosAluno.MyHolder(view);

        return conteudoViewHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder viewHolder, final int position) {

        /*
        conteudosRecebidosViewModel = conteudos.get(position);
        MyHolder conteudoViewHolder = viewHolder;

        conteudoViewHolder.tituloNovoConteudo.setText(conteudosRecebidosViewModel.getTituloNovConteudo());
        conteudoViewHolder.nomeProfessorNovoConteudo.setText(conteudosRecebidosViewModel.getNomeProfessorNovoConteudo());
        conteudoViewHolder.materiaNovoConteudo.setText(conteudosRecebidosViewModel.getMateriaNovoConteudo());


        */
        MyHolder conteudoViewHolder = viewHolder;

        conteudoViewHolder.tituloNovoConteudo.setText("Equações");
        conteudoViewHolder.nomeProfessorNovoConteudo.setText("Marcos Afonso");
        conteudoViewHolder.materiaNovoConteudo.setText("Matemática");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                /*
                    Logica para recuperar uma atividade especifica do banco de dados
                 */

                atividadeId = String.valueOf(conteudos.get(position).getuID());
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
