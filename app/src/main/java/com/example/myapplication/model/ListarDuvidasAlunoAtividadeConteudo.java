package com.example.myapplication.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class ListarDuvidasAlunoAtividadeConteudo implements Parcelable {

    private String descricaoAtividade;
    private String materia;
    private String aluno;
    private String caminhoArquivo;
    private String titulo;
    private String turma;

    //Construtor criado para recuperar as atividades concluidas do aluno
    public ListarDuvidasAlunoAtividadeConteudo(String aluno, String caminhoArquivo, String titulo, String turma) {
        this.aluno = aluno;
        this.caminhoArquivo = caminhoArquivo;
        this.titulo = titulo;
        this.turma = turma;
    }

    //Construtor criado para recuperar as duvidas sobre atividades e conteudos
    public ListarDuvidasAlunoAtividadeConteudo(String descricaoAtividade, String aluno, String caminhoArquivo, String titulo, String turma) {
        this.descricaoAtividade = descricaoAtividade;
        this.aluno = aluno;
        this.caminhoArquivo = caminhoArquivo;
        this.titulo = titulo;
        this.turma = turma;
    }

    public ListarDuvidasAlunoAtividadeConteudo(Parcel in) {
        descricaoAtividade = in.readString();
        materia = in.readString();
        aluno = in.readString();
        caminhoArquivo = in.readString();
        titulo = in.readString();
        turma = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descricaoAtividade);
        dest.writeString(materia);
        dest.writeString(aluno);
        dest.writeString(caminhoArquivo);
        dest.writeString(titulo);
        dest.writeString(turma);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListarDuvidasAlunoAtividadeConteudo> CREATOR = new Creator<ListarDuvidasAlunoAtividadeConteudo>() {
        @Override
        public ListarDuvidasAlunoAtividadeConteudo createFromParcel(Parcel in) {
            return new ListarDuvidasAlunoAtividadeConteudo(in);
        }

        @Override
        public ListarDuvidasAlunoAtividadeConteudo[] newArray(int size) {
            return new ListarDuvidasAlunoAtividadeConteudo[size];
        }
    };

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListarDuvidasAlunoAtividadeConteudo)) return false;
        ListarDuvidasAlunoAtividadeConteudo that = (ListarDuvidasAlunoAtividadeConteudo) o;
        return getDescricaoAtividade().equals(that.getDescricaoAtividade()) &&
                getMateria().equals(that.getMateria()) &&
                getAluno().equals(that.getAluno()) &&
                getCaminhoArquivo().equals(that.getCaminhoArquivo()) &&
                getTitulo().equals(that.getTitulo())&&
                getTurma().equals(that.getTurma());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getDescricaoAtividade(), getMateria(), getAluno(), getCaminhoArquivo(), getTitulo(), getTurma());
    }

    @Override
    public String toString() {
        return "ListarDuvidasAlunoAtividadeConteudo{" +
                "descricaoAtividade='" + descricaoAtividade + '\'' +
                ", materia='" + materia + '\'' +
                ", aluno='" + aluno + '\'' +
                ", caminhoArquivo='" + caminhoArquivo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", turma='" + turma + '\'' +
                '}';
    }
}
