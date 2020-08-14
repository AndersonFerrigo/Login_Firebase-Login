package com.example.myapplication.content.aluno.ui.conteudos_recebidos;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class ConteudosRecebidosViewModel extends ViewModel {
    private String uID;
    private String tituloNovConteudo;
    private String nomeProfessorNovoConteudo;
    private String materiaNovoConteudo;

    public ConteudosRecebidosViewModel() { }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getTituloNovConteudo() {
        return tituloNovConteudo;
    }

    public void setTituloNovConteudo(String tituloNovConteudo) {
        this.tituloNovConteudo = tituloNovConteudo;
    }

    public String getNomeProfessorNovoConteudo() {
        return nomeProfessorNovoConteudo;
    }

    public void setNomeProfessorNovoConteudo(String nomeProfessorNovoConteudo) {
        this.nomeProfessorNovoConteudo = nomeProfessorNovoConteudo;
    }

    public String getMateriaNovoConteudo() {
        return materiaNovoConteudo;
    }

    public void setMateriaNovoConteudo(String materiaNovoConteudo) {
        this.materiaNovoConteudo = materiaNovoConteudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConteudosRecebidosViewModel)) return false;
        ConteudosRecebidosViewModel that = (ConteudosRecebidosViewModel) o;
        return getuID().equals(that.getuID()) &&
                getTituloNovConteudo().equals(that.getTituloNovConteudo()) &&
                getNomeProfessorNovoConteudo().equals(that.getNomeProfessorNovoConteudo()) &&
                getMateriaNovoConteudo().equals(that.getMateriaNovoConteudo());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuID(), getTituloNovConteudo(), getNomeProfessorNovoConteudo(), getMateriaNovoConteudo());
    }

    @Override
    public String toString() {
        return "ConteudosRecebidosViewModel{" +
                "uID='" + uID + '\'' +
                ", tituloNovConteudo='" + tituloNovConteudo + '\'' +
                ", nomeProfessorNovoConteudo='" + nomeProfessorNovoConteudo + '\'' +
                ", materiaNovoConteudo='" + materiaNovoConteudo + '\'' +
                '}';
    }
}