package com.example.myapplication.content.professor.ui.atividades_recebidas_aluno;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class AtividadesRecebidasAlunoViewModel extends ViewModel {
    private String uID;
    private String tituloAtividadeRecebida;
    private String nomeAlunoAtividadeRecebida;
    private String materiaAtividadeRecebida;

    public AtividadesRecebidasAlunoViewModel() {    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getTituloAtividadeRecebida() {
        return tituloAtividadeRecebida;
    }

    public void setTituloAtividadeRecebida(String tituloAtividadeRecebida) {
        this.tituloAtividadeRecebida = tituloAtividadeRecebida;
    }

    public String getNomeAlunoAtividadeRecebida() {
        return nomeAlunoAtividadeRecebida;
    }

    public void setNomeAlunoAtividadeRecebida(String nomeAlunoAtividadeRecebida) {
        this.nomeAlunoAtividadeRecebida = nomeAlunoAtividadeRecebida;
    }

    public String getMateriaAtividadeRecebida() {
        return materiaAtividadeRecebida;
    }

    public void setMateriaAtividadeRecebida(String materiaAtividadeRecebida) {
        this.materiaAtividadeRecebida = materiaAtividadeRecebida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AtividadesRecebidasAlunoViewModel)) return false;
        AtividadesRecebidasAlunoViewModel that = (AtividadesRecebidasAlunoViewModel) o;
        return getuID().equals(that.getuID()) &&
                getTituloAtividadeRecebida().equals(that.getTituloAtividadeRecebida()) &&
                getNomeAlunoAtividadeRecebida().equals(that.getNomeAlunoAtividadeRecebida()) &&
                getMateriaAtividadeRecebida().equals(that.getMateriaAtividadeRecebida());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuID(), getTituloAtividadeRecebida(), getNomeAlunoAtividadeRecebida(), getMateriaAtividadeRecebida());
    }

    @Override
    public String toString() {
        return "AtividadesRecebidasAlunoViewModel{" +
                "uID='" + uID + '\'' +
                ", tituloAtividadeRecebida='" + tituloAtividadeRecebida + '\'' +
                ", nomeAlunoAtividadeRecebida='" + nomeAlunoAtividadeRecebida + '\'' +
                ", materiaAtividadeRecebida='" + materiaAtividadeRecebida + '\'' +
                '}';
    }
}