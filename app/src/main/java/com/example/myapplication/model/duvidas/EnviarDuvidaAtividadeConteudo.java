package com.example.myapplication.model.duvidas;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class EnviarDuvidaAtividadeConteudo {

    private String UID;
    private String aluno;
    private String titulo;
    private String materia;
    private String turma;
    private String duvida;
    private String documento;

    public EnviarDuvidaAtividadeConteudo() {   }

    public EnviarDuvidaAtividadeConteudo(String UID, String aluno, String titulo, String materia, String turma, String duvida, String documento) {
        this.UID = UID;
        this.aluno = aluno;
        this.titulo = titulo;
        this.materia = materia;
        this.turma = turma;
        this.duvida = duvida;
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

    public String getDuvida() {
        return duvida;
    }

    public void setDuvida(String duvida) {
        this.duvida = duvida;
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
        if (!(o instanceof EnviarDuvidaAtividadeConteudo)) return false;
        EnviarDuvidaAtividadeConteudo that = (EnviarDuvidaAtividadeConteudo) o;
        return getUID().equals(that.getUID()) &&
                getAluno().equals(that.getAluno()) &&
                getTitulo().equals(that.getTitulo()) &&
                getMateria().equals(that.getMateria()) &&
                getTurma().equals(that.getTurma()) &&
                getDuvida().equals(that.getDuvida()) &&
                getDocumento().equals(that.getDocumento());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getUID(), getAluno(), getTitulo(), getMateria(), getTurma(), getDuvida(), getDocumento());
    }

    @Override
    public String toString() {
        return "EnviarDuvidaAtividadeConteudo{" +
                "UID='" + UID + '\'' +
                ", aluno='" + aluno + '\'' +
                ", titulo='" + titulo + '\'' +
                ", materia='" + materia + '\'' +
                ", turma='" + turma + '\'' +
                ", duvida='" + duvida + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}
