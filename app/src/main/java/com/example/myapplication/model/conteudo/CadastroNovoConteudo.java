package com.example.myapplication.model.conteudo;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class CadastroNovoConteudo {

    private String UID;
    private String professor;
    private String titulo;
    private String materia;
    private String turma;
    private String descricao;
    private String documento;

    public CadastroNovoConteudo() { }

    public CadastroNovoConteudo(String UID, String professor, String titulo, String materia, String turma, String descricao, String documento) {
        this.UID = UID;
        this.professor = professor;
        this.titulo = titulo;
        this.materia = materia;
        this.turma = turma;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        if (!(o instanceof CadastroNovoConteudo)) return false;
        CadastroNovoConteudo that = (CadastroNovoConteudo) o;
        return getUID().equals(that.getUID()) &&
                getProfessor().equals(that.getProfessor()) &&
                getTitulo().equals(that.getTitulo()) &&
                getMateria().equals(that.getMateria()) &&
                getTurma().equals(that.getTurma()) &&
                getDescricao().equals(that.getDescricao()) &&
                getDocumento().equals(that.getDocumento());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getUID(), getProfessor(), getTitulo(), getMateria(), getTurma(), getDescricao(), getDocumento());
    }

    @Override
    public String toString() {
        return "CadastroNovoConteudo{" +
                "UID='" + UID + '\'' +
                ", professor='" + professor + '\'' +
                ", titulo='" + titulo + '\'' +
                ", materia='" + materia + '\'' +
                ", turma='" + turma + '\'' +
                ", descricao='" + descricao + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}

