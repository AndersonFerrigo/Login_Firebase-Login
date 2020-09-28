package com.example.myapplication.model.atividades;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class CadastoNovaAtividade {

    /**
     * @since 22/09/2020
     */

    private String UID;
    private String professor;
    private String materia;
    private String turma;
    private String titulo;
    private String descricao;
    private String documento;

    public CadastoNovaAtividade() {}

    public CadastoNovaAtividade(String UID, String professor, String materia, String turma, String titulo, String descricao, String documento) {
        this.UID = UID;
        this.professor = professor;
        this.materia = materia;
        this.turma = turma;
        this.titulo = titulo;
        this.descricao = descricao;
        this.documento = documento;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CadastoNovaAtividade)) return false;
        CadastoNovaAtividade that = (CadastoNovaAtividade) o;
        return getUID().equals(that.getUID()) &&
                getProfessor().equals(that.getProfessor()) &&
                getMateria().equals(that.getMateria()) &&
                getTurma().equals(that.getTurma()) &&
                getTitulo().equals(that.getTitulo()) &&
                getDescricao().equals(that.getDescricao()) &&
                getDocumento().equals(that.getDocumento());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getUID(), getProfessor(), getMateria(), getTurma(), getTitulo(), getDescricao(), getDocumento());
    }

    @Override
    public String toString() {
        return "CadastoNovaAtividade{" +
                "UID='" + UID + '\'' +
                ", professor='" + professor + '\'' +
                ", materia='" + materia + '\'' +
                ", turma='" + turma + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}
