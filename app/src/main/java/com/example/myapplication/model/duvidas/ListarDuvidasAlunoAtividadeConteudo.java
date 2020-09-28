package com.example.myapplication.model.duvidas;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class ListarDuvidasAlunoAtividadeConteudo implements Parcelable {

    /**
     * @since 22/09/2020
     */

    private String aluno;
    private String turma;
    private String materia;
    private String titulo;
    private String descricao;
    private String documento;

    //Construtor criado para recuperar as atividades concluidas do aluno
    public ListarDuvidasAlunoAtividadeConteudo(String aluno, String turma, String titulo, String documento) {
        this.aluno = aluno;
        this.turma = turma;
        this.titulo = titulo;
        this.documento = documento;

    }

    //Construtor criado para recuperar as duvidas sobre atividades e conteudos
    public ListarDuvidasAlunoAtividadeConteudo( String aluno, String turma, String titulo, String descricao,String documento) {
        this.aluno = aluno;
        this.turma = turma;
        this.titulo = titulo;
        this.descricao = descricao;
        this.documento = documento;

    }

    public ListarDuvidasAlunoAtividadeConteudo(Parcel in) {
        aluno = in.readString();
        turma = in.readString();
        materia = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        documento = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aluno);
        dest.writeString(turma);
        dest.writeString(materia);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(documento);
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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
        return getDescricao().equals(that.getDescricao()) &&
                getMateria().equals(that.getMateria()) &&
                getAluno().equals(that.getAluno()) &&
                getDocumento().equals(that.getDocumento()) &&
                getTitulo().equals(that.getTitulo())&&
                getTurma().equals(that.getTurma());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getDescricao(), getMateria(), getAluno(), getDocumento(), getTitulo(), getTurma());
    }

    @Override
    public String toString() {
        return "ListarDuvidasAlunoAtividadeConteudo{" +
                "aluno='" + aluno + '\'' +
                ", turma='" + turma + '\'' +
                ", materia='" + materia + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}
