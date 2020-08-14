package com.example.myapplication.content.aluno.ui.atividades_concluidas;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.CadastarAtividade;

import java.util.Objects;

public class EnviarAtividadesConcluidasViewModel extends ViewModel {
    private String uId;
    private String turma;
    private String tituloAtividadeConcluida;
    private String pathDocumentoAtividadeConcluida;

    public EnviarAtividadesConcluidasViewModel(){}


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getTituloAtividadeConcluida() {
        return tituloAtividadeConcluida;
    }

    public void setTituloAtividadeConcluida(String tituloAtividadeConcluida) {
        this.tituloAtividadeConcluida = tituloAtividadeConcluida;
    }

    public String getPathDocumentoAtividadeConcluida() {
        return pathDocumentoAtividadeConcluida;
    }

    public void setPathDocumentoAtividadeConcluida(String pathDocumentoAtividadeConcluida) {
        this.pathDocumentoAtividadeConcluida = pathDocumentoAtividadeConcluida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CadastarAtividade)) return false;
        CadastarAtividade that = (CadastarAtividade) o;
        return getuId().equals(that.getuId()) &&
                getTurma().equals(that.getTurma()) &&
                getTituloAtividadeConcluida().equals(that.getTitulo()) &&
                getPathDocumentoAtividadeConcluida().equals(that.getPathDocumentoAtividade());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getTurma(), getTituloAtividadeConcluida(), getPathDocumentoAtividadeConcluida());
    }

    @Override
    public String toString() {
        return "CadastarAtividade{" +
                "uId='" + uId + '\'' +
                ", turma='" + turma + '\'' +
                ", titulo='" + tituloAtividadeConcluida + '\'' +
                ", pathDocumentoAtividade='" + pathDocumentoAtividadeConcluida + '\'' +
                '}';
    }
}