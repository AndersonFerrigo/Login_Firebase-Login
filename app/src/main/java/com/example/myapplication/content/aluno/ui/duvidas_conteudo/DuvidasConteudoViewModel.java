package com.example.myapplication.content.aluno.ui.duvidas_conteudo;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.content.aluno.ui.duvidas_atividade.DuvidasAtividadesConcluidasViewModel;

import java.util.Objects;

public class DuvidasConteudoViewModel extends ViewModel {
    private String uId;
    private String turma;
    private String tituloAtividadeConcluida;
    private String pathDocumentoAtividadeConcluida;

    public DuvidasConteudoViewModel() {    }

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
        if (!(o instanceof DuvidasAtividadesConcluidasViewModel)) return false;
        DuvidasAtividadesConcluidasViewModel that = (DuvidasAtividadesConcluidasViewModel) o;
        return getuId().equals(that.getuId()) &&
                getTurma().equals(that.getTurma()) &&
                getTituloAtividadeConcluida().equals(that.getTituloAtividadeConcluida()) &&
                getPathDocumentoAtividadeConcluida().equals(that.getPathDocumentoAtividadeConcluida());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getTurma(), getTituloAtividadeConcluida(), getPathDocumentoAtividadeConcluida());
    }

    @Override
    public String toString() {
        return "DuvidasAtividadesConcluidasViewModel{" +
                "uId='" + uId + '\'' +
                ", turma='" + turma + '\'' +
                ", tituloAtividadeConcluida='" + tituloAtividadeConcluida + '\'' +
                ", pathDocumentoAtividadeConcluida='" + pathDocumentoAtividadeConcluida + '\'' +
                '}';
    }
}