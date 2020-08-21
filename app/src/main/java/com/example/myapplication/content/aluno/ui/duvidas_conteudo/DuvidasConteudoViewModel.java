package com.example.myapplication.content.aluno.ui.duvidas_conteudo;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.content.aluno.ui.duvidas_atividade.DuvidasAtividadesConcluidasViewModel;

import java.util.Objects;

public class DuvidasConteudoViewModel extends ViewModel {
    private String uId;
    private String nomeAlunoConteudoDuvida;
    private String tituloDuvidaConteudo;
    private String materiaProfessorConteudoDuvida;
    private String turmaProfessorConteudoDuvida;
    private String descricaoConteudoDuvida;
    private String pathDocumentoConteudoDuvida;

    public DuvidasConteudoViewModel() {    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNomeAlunoConteudoDuvida() {
        return nomeAlunoConteudoDuvida;
    }

    public void setNomeAlunoConteudoDuvida(String nomeAlunoConteudoDuvida) {
        this.nomeAlunoConteudoDuvida = nomeAlunoConteudoDuvida;
    }

    public String getTituloDuvidaConteudo() {
        return tituloDuvidaConteudo;
    }

    public void setTituloDuvidaConteudo(String tituloDuvidaConteudo) {
        this.tituloDuvidaConteudo = tituloDuvidaConteudo;
    }

    public String getMateriaProfessorConteudoDuvida() {
        return materiaProfessorConteudoDuvida;
    }

    public void setMateriaProfessorConteudoDuvida(String materiaProfessorConteudoDuvida) {
        this.materiaProfessorConteudoDuvida = materiaProfessorConteudoDuvida;
    }

    public String getTurmaProfessorConteudoDuvida() {
        return turmaProfessorConteudoDuvida;
    }

    public void setTurmaProfessorConteudoDuvida(String turmaProfessorConteudoDuvida) {
        this.turmaProfessorConteudoDuvida = turmaProfessorConteudoDuvida;
    }

    public String getDescricaoConteudoDuvida() {
        return descricaoConteudoDuvida;
    }

    public void setDescricaoConteudoDuvida(String descricaoConteudoDuvida) {
        this.descricaoConteudoDuvida = descricaoConteudoDuvida;
    }

    public String getPathDocumentoConteudoDuvida() {
        return pathDocumentoConteudoDuvida;
    }

    public void setPathDocumentoConteudoDuvida(String pathDocumentoConteudoDuvida) {
        this.pathDocumentoConteudoDuvida = pathDocumentoConteudoDuvida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuvidasConteudoViewModel)) return false;
        DuvidasConteudoViewModel that = (DuvidasConteudoViewModel) o;
        return getuId().equals(that.getuId()) &&
                getNomeAlunoConteudoDuvida().equals(that.getNomeAlunoConteudoDuvida()) &&
                getTituloDuvidaConteudo().equals(that.getTituloDuvidaConteudo()) &&
                getMateriaProfessorConteudoDuvida().equals(that.getMateriaProfessorConteudoDuvida()) &&
                getTurmaProfessorConteudoDuvida().equals(that.getTurmaProfessorConteudoDuvida()) &&
                getDescricaoConteudoDuvida().equals(that.getDescricaoConteudoDuvida()) &&
                getPathDocumentoConteudoDuvida().equals(that.getPathDocumentoConteudoDuvida());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getNomeAlunoConteudoDuvida(), getTituloDuvidaConteudo(), getMateriaProfessorConteudoDuvida(), getTurmaProfessorConteudoDuvida(), getDescricaoConteudoDuvida(), getPathDocumentoConteudoDuvida());
    }

    @Override
    public String toString() {
        return "DuvidasConteudoViewModel{" +
                "uId='" + uId + '\'' +
                ", nomeAlunoConteudoDuvida='" + nomeAlunoConteudoDuvida + '\'' +
                ", tituloDuvidaConteudo='" + tituloDuvidaConteudo + '\'' +
                ", materiaProfessorConteudoDuvida='" + materiaProfessorConteudoDuvida + '\'' +
                ", turmaProfessorConteudoDuvida='" + turmaProfessorConteudoDuvida + '\'' +
                ", descricaoConteudoDuvida='" + descricaoConteudoDuvida + '\'' +
                ", pathDocumentoConteudoDuvida='" + pathDocumentoConteudoDuvida + '\'' +
                '}';
    }
}