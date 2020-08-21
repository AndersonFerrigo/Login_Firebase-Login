package com.example.myapplication.content.aluno.ui.duvidas_atividade;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class DuvidasAtividadesConcluidasViewModel extends ViewModel {
    private String uId;
    private String nomeProfessorAtividadeDuvida;
    private String tituloDuvidaAtividade;
    private String materiaProfessorAtividadeDuvida;
    private String turmaProfessorAtividadeDuvida;
    private String descricaoAtividadeDuvida;
    private String pathDocumentoAtividadeDuvida;

    public DuvidasAtividadesConcluidasViewModel() {    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNomeProfessorAtividadeDuvida() {
        return nomeProfessorAtividadeDuvida;
    }

    public void setNomeProfessorAtividadeDuvida(String nomeProfessorAtividadeDuvida) {
        this.nomeProfessorAtividadeDuvida = nomeProfessorAtividadeDuvida;
    }

    public String getTituloDuvidaAtividade() {
        return tituloDuvidaAtividade;
    }

    public void setTituloDuvidaAtividade(String tituloDuvidaAtividade) {
        this.tituloDuvidaAtividade = tituloDuvidaAtividade;
    }

    public String getMateriaProfessorAtividadeDuvida() {
        return materiaProfessorAtividadeDuvida;
    }

    public void setMateriaProfessorAtividadeDuvida(String materiaProfessorAtividadeDuvida) {
        this.materiaProfessorAtividadeDuvida = materiaProfessorAtividadeDuvida;
    }

    public String getTurmaProfessorAtividadeDuvida() {
        return turmaProfessorAtividadeDuvida;
    }

    public void setTurmaProfessorAtividadeDuvida(String turmaProfessorAtividadeDuvida) {
        this.turmaProfessorAtividadeDuvida = turmaProfessorAtividadeDuvida;
    }

    public String getDescricaoAtividadeDuvida() {
        return descricaoAtividadeDuvida;
    }

    public void setDescricaoAtividadeDuvida(String descricaoAtividadeDuvida) {
        this.descricaoAtividadeDuvida = descricaoAtividadeDuvida;
    }

    public String getPathDocumentoAtividadeDuvida() {
        return pathDocumentoAtividadeDuvida;
    }

    public void setPathDocumentoAtividadeDuvida(String pathDocumentoAtividadeDuvida) {
        this.pathDocumentoAtividadeDuvida = pathDocumentoAtividadeDuvida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuvidasAtividadesConcluidasViewModel)) return false;
        DuvidasAtividadesConcluidasViewModel that = (DuvidasAtividadesConcluidasViewModel) o;
        return getuId().equals(that.getuId()) &&
                getNomeProfessorAtividadeDuvida().equals(that.getNomeProfessorAtividadeDuvida()) &&
                getTituloDuvidaAtividade().equals(that.getTituloDuvidaAtividade()) &&
                getMateriaProfessorAtividadeDuvida().equals(that.getMateriaProfessorAtividadeDuvida()) &&
                getTurmaProfessorAtividadeDuvida().equals(that.getTurmaProfessorAtividadeDuvida()) &&
                getDescricaoAtividadeDuvida().equals(that.getDescricaoAtividadeDuvida()) &&
                getPathDocumentoAtividadeDuvida().equals(that.getPathDocumentoAtividadeDuvida());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getNomeProfessorAtividadeDuvida(), getTituloDuvidaAtividade(), getMateriaProfessorAtividadeDuvida(), getTurmaProfessorAtividadeDuvida(), getDescricaoAtividadeDuvida(), getPathDocumentoAtividadeDuvida());
    }

    @Override
    public String toString() {
        return "DuvidasAtividadesConcluidasViewModel{" +
                "uId='" + uId + '\'' +
                ", nomeProfessorAtividadeDuvida='" + nomeProfessorAtividadeDuvida + '\'' +
                ", tituloDuvidaAtividade='" + tituloDuvidaAtividade + '\'' +
                ", materiaProfessorAtividadeDuvida='" + materiaProfessorAtividadeDuvida + '\'' +
                ", turmaProfessorAtividadeDuvida='" + turmaProfessorAtividadeDuvida + '\'' +
                ", descricaoAtividadeDuvida='" + descricaoAtividadeDuvida + '\'' +
                ", pathDocumentoAtividadeDuvida='" + pathDocumentoAtividadeDuvida + '\'' +
                '}';
    }
}