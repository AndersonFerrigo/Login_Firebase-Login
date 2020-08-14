package com.example.myapplication.content.professor.ui.duvidas_alunos_conteudos;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class DuvidasAlunosConteudoViewModel extends ViewModel {
    private String uIDAlunoConteudoDuvida;
    private String tituloAlunoConteudoDuvida;
    private String nomeAlunoConteudoDuvida;
    private String materiaAlunoConteudoDuvida;
    private String turmaAlunoConteudoDuvida;

    public DuvidasAlunosConteudoViewModel() { }

    public String getuIDAlunoConteudoDuvida() {
        return uIDAlunoConteudoDuvida;
    }

    public void setuIDAlunoConteudoDuvida(String uIDAlunoConteudoDuvida) {
        this.uIDAlunoConteudoDuvida = uIDAlunoConteudoDuvida;
    }

    public String getTituloAlunoConteudoDuvida() {
        return tituloAlunoConteudoDuvida;
    }

    public void setTituloAlunoConteudoDuvida(String tituloAlunoConteudoDuvida) {
        this.tituloAlunoConteudoDuvida = tituloAlunoConteudoDuvida;
    }

    public String getNomeAlunoConteudoDuvida() {
        return nomeAlunoConteudoDuvida;
    }

    public void setNomeAlunoConteudoDuvida(String nomeAlunoConteudoDuvida) {
        this.nomeAlunoConteudoDuvida = nomeAlunoConteudoDuvida;
    }

    public String getMateriaAlunoConteudoDuvida() {
        return materiaAlunoConteudoDuvida;
    }

    public void setMateriaAlunoConteudoDuvida(String materiaAlunoConteudoDuvida) {
        this.materiaAlunoConteudoDuvida = materiaAlunoConteudoDuvida;
    }

    public String getTurmaAlunoConteudoDuvida() {
        return turmaAlunoConteudoDuvida;
    }

    public void setTurmaAlunoConteudoDuvida(String turmaAlunoConteudoDuvida) {
        this.turmaAlunoConteudoDuvida = turmaAlunoConteudoDuvida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuvidasAlunosConteudoViewModel)) return false;
        DuvidasAlunosConteudoViewModel that = (DuvidasAlunosConteudoViewModel) o;
        return getuIDAlunoConteudoDuvida().equals(that.getuIDAlunoConteudoDuvida()) &&
                getTituloAlunoConteudoDuvida().equals(that.getTituloAlunoConteudoDuvida()) &&
                getNomeAlunoConteudoDuvida().equals(that.getNomeAlunoConteudoDuvida()) &&
                getMateriaAlunoConteudoDuvida().equals(that.getMateriaAlunoConteudoDuvida()) &&
                getTurmaAlunoConteudoDuvida().equals(that.getTurmaAlunoConteudoDuvida());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuIDAlunoConteudoDuvida(), getTituloAlunoConteudoDuvida(), getNomeAlunoConteudoDuvida(), getMateriaAlunoConteudoDuvida(), getTurmaAlunoConteudoDuvida());
    }

    @Override
    public String toString() {
        return "DuvidasAlunosConteudoViewModel{" +
                "uIDAlunoConteudoDuvida='" + uIDAlunoConteudoDuvida + '\'' +
                ", tituloAlunoConteudoDuvida='" + tituloAlunoConteudoDuvida + '\'' +
                ", nomeAlunoConteudoDuvida='" + nomeAlunoConteudoDuvida + '\'' +
                ", materiaAlunoConteudoDuvida='" + materiaAlunoConteudoDuvida + '\'' +
                ", turmaAlunoConteudoDuvida='" + turmaAlunoConteudoDuvida + '\'' +
                '}';
    }
}