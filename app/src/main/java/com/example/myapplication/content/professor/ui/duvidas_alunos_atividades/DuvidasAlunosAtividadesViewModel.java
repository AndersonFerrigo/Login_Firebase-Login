package com.example.myapplication.content.professor.ui.duvidas_alunos_atividades;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class DuvidasAlunosAtividadesViewModel extends ViewModel {
    private String uIDAlunoAtividadeDuvida;
    private String tituloAlunoAtividadeDuvida;
    private String nomeAlunoAtividadeDuvida;
    private String materiaAlunoAtividadeDuvida;
    private String turmaAlunoAtividadeDuvida;

    public DuvidasAlunosAtividadesViewModel() {    }

    public String getuIDAlunoAtividadeDuvida() {
        return uIDAlunoAtividadeDuvida;
    }

    public void setuIDAlunoAtividadeDuvida(String uIDAlunoAtividadeDuvida) {
        this.uIDAlunoAtividadeDuvida = uIDAlunoAtividadeDuvida;
    }

    public String getTituloAlunoAtividadeDuvida() {
        return tituloAlunoAtividadeDuvida;
    }

    public void setTituloAlunoAtividadeDuvida(String tituloAlunoAtividadeDuvida) {
        this.tituloAlunoAtividadeDuvida = tituloAlunoAtividadeDuvida;
    }

    public String getNomeAlunoAtividadeDuvida() {
        return nomeAlunoAtividadeDuvida;
    }

    public void setNomeAlunoAtividadeDuvida(String nomeAlunoAtividadeDuvida) {
        this.nomeAlunoAtividadeDuvida = nomeAlunoAtividadeDuvida;
    }

    public String getMateriaAlunoAtividadeDuvida() {
        return materiaAlunoAtividadeDuvida;
    }

    public void setMateriaAlunoAtividadeDuvida(String materiaAlunoAtividadeDuvida) {
        this.materiaAlunoAtividadeDuvida = materiaAlunoAtividadeDuvida;
    }

    public String getTurmaAlunoAtividadeDuvida() {
        return turmaAlunoAtividadeDuvida;
    }

    public void setTurmaAlunoAtividadeDuvida(String turmaAlunoAtividadeDuvida) {
        this.turmaAlunoAtividadeDuvida = turmaAlunoAtividadeDuvida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuvidasAlunosAtividadesViewModel)) return false;
        DuvidasAlunosAtividadesViewModel that = (DuvidasAlunosAtividadesViewModel) o;
        return getuIDAlunoAtividadeDuvida().equals(that.getuIDAlunoAtividadeDuvida()) &&
                getTituloAlunoAtividadeDuvida().equals(that.getTituloAlunoAtividadeDuvida()) &&
                getNomeAlunoAtividadeDuvida().equals(that.getNomeAlunoAtividadeDuvida()) &&
                getMateriaAlunoAtividadeDuvida().equals(that.getMateriaAlunoAtividadeDuvida()) &&
                getTurmaAlunoAtividadeDuvida().equals(that.getTurmaAlunoAtividadeDuvida());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuIDAlunoAtividadeDuvida(), getTituloAlunoAtividadeDuvida(), getNomeAlunoAtividadeDuvida(), getMateriaAlunoAtividadeDuvida(), getTurmaAlunoAtividadeDuvida());
    }

    @Override
    public String toString() {
        return "DuvidasAlunosAtividadesViewModel{" +
                "uIDAlunoAtividadeDuvida='" + uIDAlunoAtividadeDuvida + '\'' +
                ", tituloAlunoAtividadeDuvida='" + tituloAlunoAtividadeDuvida + '\'' +
                ", nomeAlunoAtividadeDuvida='" + nomeAlunoAtividadeDuvida + '\'' +
                ", materiaAlunoAtividadeDuvida='" + materiaAlunoAtividadeDuvida + '\'' +
                ", turmaAlunoAtividadeDuvida='" + turmaAlunoAtividadeDuvida + '\'' +
                '}';
    }
}