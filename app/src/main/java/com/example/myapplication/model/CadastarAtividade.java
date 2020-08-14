package com.example.myapplication.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class CadastarAtividade {

    private String uId;
    private String turma;
    private String titulo;
    private String descricaoAtividade;
    private String pathDocumentoAtividade;

    public CadastarAtividade() {}

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public String getPathDocumentoAtividade() {
        return pathDocumentoAtividade;
    }

    public void setPathDocumentoAtividade(String pathDocumentoAtividade) {
        this.pathDocumentoAtividade = pathDocumentoAtividade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CadastarAtividade)) return false;
        CadastarAtividade that = (CadastarAtividade) o;
        return getuId().equals(that.getuId()) &&
                getTurma().equals(that.getTurma()) &&
                getTitulo().equals(that.getTitulo()) &&
                getDescricaoAtividade().equals(that.getDescricaoAtividade()) &&
                getPathDocumentoAtividade().equals(that.getPathDocumentoAtividade());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuId(), getTurma(), getTitulo(), getDescricaoAtividade(), getPathDocumentoAtividade());
    }

    @Override
    public String toString() {
        return "CadastarAtividade{" +
                "uId='" + uId + '\'' +
                ", turma='" + turma + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricaoAtividade='" + descricaoAtividade + '\'' +
                ", pathDocumentoAtividade='" + pathDocumentoAtividade + '\'' +
                '}';
    }
}
