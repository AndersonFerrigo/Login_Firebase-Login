package com.example.myapplication.content.aluno.ui.atividades_concluidas;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.CadastarAtividade;

import java.util.Objects;

public class EnviarAtividadesConcluidasViewModel extends ViewModel {
    /**
     * @since2020
     */

    private String uId;
    private String nomeAluno;
    private String materiaAluno;
    private String turmaAluno;
    private String tituloAtividadeConcluidaAluno;
    private String pathDocumentoAtividadeConcluidaAluno;

    public EnviarAtividadesConcluidasViewModel(){}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getMateriaAluno() {
        return materiaAluno;
    }

    public void setMateriaAluno(String materiaAluno) {
        this.materiaAluno = materiaAluno;
    }

    public String getTurmaAluno() {
        return turmaAluno;
    }

    public void setTurmaAluno(String turmaAluno) {
        this.turmaAluno = turmaAluno;
    }

    public String getTituloAtividadeConcluidaAluno() {
        return tituloAtividadeConcluidaAluno;
    }

    public void setTituloAtividadeConcluidaAluno(String tituloAtividadeConcluidaAluno) {
        this.tituloAtividadeConcluidaAluno = tituloAtividadeConcluidaAluno;
    }

    public String getPathDocumentoAtividadeConcluidaAluno() {
        return pathDocumentoAtividadeConcluidaAluno;
    }

    public void setPathDocumentoAtividadeConcluidaAluno(String pathDocumentoAtividadeConcluidaAluno) {
        this.pathDocumentoAtividadeConcluidaAluno = pathDocumentoAtividadeConcluidaAluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnviarAtividadesConcluidasViewModel)) return false;
        EnviarAtividadesConcluidasViewModel that = (EnviarAtividadesConcluidasViewModel) o;
        return getuId().equals(that.getuId()) &&
                getNomeAluno().equals(that.getNomeAluno()) &&
                getMateriaAluno().equals(that.getMateriaAluno()) &&
                getTurmaAluno().equals(that.getTurmaAluno()) &&
                getTituloAtividadeConcluidaAluno().equals(that.getTituloAtividadeConcluidaAluno()) &&
                getPathDocumentoAtividadeConcluidaAluno().equals(that.getPathDocumentoAtividadeConcluidaAluno());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getNomeAluno(), getMateriaAluno(), getTurmaAluno(), getTituloAtividadeConcluidaAluno(), getPathDocumentoAtividadeConcluidaAluno());
    }

    @Override
    public String toString() {
        return "EnviarAtividadesConcluidasViewModel{" +
                "uId='" + uId + '\'' +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", materiaAluno='" + materiaAluno + '\'' +
                ", turmaAluno='" + turmaAluno + '\'' +
                ", tituloAtividadeConcluidaAluno='" + tituloAtividadeConcluidaAluno + '\'' +
                ", pathDocumentoAtividadeConcluidaAluno='" + pathDocumentoAtividadeConcluidaAluno + '\'' +
                '}';
    }
}