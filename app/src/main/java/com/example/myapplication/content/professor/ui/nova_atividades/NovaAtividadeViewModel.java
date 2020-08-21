package com.example.myapplication.content.professor.ui.nova_atividades;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.CadastarAtividade;

import java.util.Objects;

public class NovaAtividadeViewModel extends ViewModel {
    /**
     * @since 2020
     */

    private String uId;
    private String nomeProfessor;
    private String materiaProfessor;
    private String turmaProfessor;
    private String tituloNovaAtividadeProfessor;
    private String DescriçãoNovaAtividadeProfessor;
    private String pathDocumentoNovaAtividadeProfessor;

    public NovaAtividadeViewModel() { }

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

    public String getTituloNovaAtividadeProfessor() {
        return tituloNovaAtividadeProfessor;
    }

    public void setTituloNovaAtividadeProfessor(String tituloNovaAtividadeProfessor) {
        this.tituloNovaAtividadeProfessor = tituloNovaAtividadeProfessor;
    }

    public String getDescriçãoNovaAtividadeProfessor() {
        return DescriçãoNovaAtividadeProfessor;
    }

    public void setDescriçãoNovaAtividadeProfessor(String descriçãoNovaAtividadeProfessor) {
        DescriçãoNovaAtividadeProfessor = descriçãoNovaAtividadeProfessor;
    }

    public String getPathDocumentoNovaAtividadeProfessor() {
        return pathDocumentoNovaAtividadeProfessor;
    }

    public void setPathDocumentoNovaAtividadeProfessor(String pathDocumentoNovaAtividadeProfessor) {
        this.pathDocumentoNovaAtividadeProfessor = pathDocumentoNovaAtividadeProfessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NovaAtividadeViewModel)) return false;
        NovaAtividadeViewModel that = (NovaAtividadeViewModel) o;
        return getuId().equals(that.getuId()) &&
                getNomeProfessor().equals(that.getNomeProfessor()) &&
                getMateriaProfessor().equals(that.getMateriaProfessor()) &&
                getTurmaProfessor().equals(that.getTurmaProfessor()) &&
                getTituloNovaAtividadeProfessor().equals(that.getTituloNovaAtividadeProfessor()) &&
                getDescriçãoNovaAtividadeProfessor().equals(that.getDescriçãoNovaAtividadeProfessor()) &&
                getPathDocumentoNovaAtividadeProfessor().equals(that.getPathDocumentoNovaAtividadeProfessor());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getNomeProfessor(), getMateriaProfessor(), getTurmaProfessor(), getTituloNovaAtividadeProfessor(), getDescriçãoNovaAtividadeProfessor(), getPathDocumentoNovaAtividadeProfessor());
    }

    @Override
    public String toString() {
        return "NovaAtividadeViewModel{" +
                "uId='" + uId + '\'' +
                ", nomeProfessor='" + nomeProfessor + '\'' +
                ", materiaProfessor='" + materiaProfessor + '\'' +
                ", turmaProfessor='" + turmaProfessor + '\'' +
                ", tituloNovaAtividadeProfessor='" + tituloNovaAtividadeProfessor + '\'' +
                ", DescriçãoNovaAtividadeProfessor='" + DescriçãoNovaAtividadeProfessor + '\'' +
                ", pathDocumentoNovaAtividadeProfessor='" + pathDocumentoNovaAtividadeProfessor + '\'' +
                '}';
    }
}