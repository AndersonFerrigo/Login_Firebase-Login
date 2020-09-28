package com.example.myapplication.model.atividades;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class EnviarAtividadeConcluida {
    private String UID;
    private String aluno;
    private String materia;
    private String turma;
    private String titulo;
    private String documento;

    public EnviarAtividadeConcluida() {    }

    public EnviarAtividadeConcluida(String UID, String aluno, String materia, String turma, String titulo, String documento) {
        this.UID = UID;
        this.aluno = aluno;
        this.materia = materia;
        this.turma = turma;
        this.titulo = titulo;
        this.documento = documento;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnviarAtividadeConcluida)) return false;
        EnviarAtividadeConcluida that = (EnviarAtividadeConcluida) o;
        return getUID().equals(that.getUID()) &&
                getAluno().equals(that.getAluno()) &&
                getMateria().equals(that.getMateria()) &&
                getTurma().equals(that.getTurma()) &&
                getTitulo().equals(that.getTitulo()) &&
                getDocumento().equals(that.getDocumento());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getUID(), getAluno(), getMateria(), getTurma(), getTitulo(), getDocumento());
    }

    @Override
    public String toString() {
        return "EnviarAtividadeConcluida{" +
                "UID='" + UID + '\'' +
                ", aluno='" + aluno + '\'' +
                ", materia='" + materia + '\'' +
                ", turma='" + turma + '\'' +
                ", titulo='" + titulo + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}
