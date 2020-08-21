package com.example.myapplication.content.professor.ui.novo_conteudo;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class NovoConteudoViewModel extends ViewModel {

    /**
     * @since 2020
     */

    private String uId;
    private String nomeProfessor;
    private String materiaProfessor;
    private String turmaProfessor;
    private String tituloNovoConteudoProfessor;
    private String DescriçãoNovoConteudoProfessor;
    private String pathDocumentoNovoConteudoProfessor;

    public NovoConteudoViewModel() {}

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getMateriaProfessor() {
        return materiaProfessor;
    }

    public void setMateriaProfessor(String materiaProfessor) {
        this.materiaProfessor = materiaProfessor;
    }

    public String getTurmaProfessor() {
        return turmaProfessor;
    }

    public void setTurmaProfessor(String turmaProfessor) {
        this.turmaProfessor = turmaProfessor;
    }

    public String getTituloNovoConteudoProfessor() {
        return tituloNovoConteudoProfessor;
    }

    public void setTituloNovoConteudoProfessor(String tituloNovoConteudoProfessor) {
        this.tituloNovoConteudoProfessor = tituloNovoConteudoProfessor;
    }

    public String getDescriçãoNovoConteudoProfessor() {
        return DescriçãoNovoConteudoProfessor;
    }

    public void setDescriçãoNovoConteudoProfessor(String descriçãoNovoConteudoProfessor) {
        DescriçãoNovoConteudoProfessor = descriçãoNovoConteudoProfessor;
    }

    public String getPathDocumentoNovoConteudoProfessor() {
        return pathDocumentoNovoConteudoProfessor;
    }

    public void setPathDocumentoNovoConteudoProfessor(String pathDocumentoNovoConteudoProfessor) {
        this.pathDocumentoNovoConteudoProfessor = pathDocumentoNovoConteudoProfessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NovoConteudoViewModel)) return false;
        NovoConteudoViewModel that = (NovoConteudoViewModel) o;
        return getuId().equals(that.getuId()) &&
                getNomeProfessor().equals(that.getNomeProfessor()) &&
                getMateriaProfessor().equals(that.getMateriaProfessor()) &&
                getTurmaProfessor().equals(that.getTurmaProfessor()) &&
                getTituloNovoConteudoProfessor().equals(that.getTituloNovoConteudoProfessor()) &&
                getDescriçãoNovoConteudoProfessor().equals(that.getDescriçãoNovoConteudoProfessor()) &&
                getPathDocumentoNovoConteudoProfessor().equals(that.getPathDocumentoNovoConteudoProfessor());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getNomeProfessor(), getMateriaProfessor(), getTurmaProfessor(), getTituloNovoConteudoProfessor(), getDescriçãoNovoConteudoProfessor(), getPathDocumentoNovoConteudoProfessor());
    }

    @Override
    public String toString() {
        return "NovoConteudoViewModel{" +
                "uId='" + uId + '\'' +
                ", nomeProfessor='" + nomeProfessor + '\'' +
                ", materiaProfessor='" + materiaProfessor + '\'' +
                ", turmaProfessor='" + turmaProfessor + '\'' +
                ", tituloNovoConteudoProfessor='" + tituloNovoConteudoProfessor + '\'' +
                ", DescriçãoNovoConteudoProfessor='" + DescriçãoNovoConteudoProfessor + '\'' +
                ", pathDocumentoNovoConteudoProfessor='" + pathDocumentoNovoConteudoProfessor + '\'' +
                '}';
    }
}