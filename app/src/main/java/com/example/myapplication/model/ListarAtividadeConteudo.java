package com.example.myapplication.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.UUID;

public class ListarAtividadeConteudo implements Parcelable {
    private String descricaoAtividade;
    private String materia;
    private String professor;
    private String caminhoArquivo;
    private String titulo;



    public ListarAtividadeConteudo(){} // Para Firebase

    public ListarAtividadeConteudo(String descricaoAtividade, String materia, String professor, String caminhoArquivo, String titulo) {
        this.descricaoAtividade = descricaoAtividade;
        this.materia = materia;
        this.professor = professor;
        this.caminhoArquivo = caminhoArquivo;
        this.titulo = titulo;
    }

    protected ListarAtividadeConteudo(Parcel in) {
        descricaoAtividade = in.readString();
        materia = in.readString();
        professor = in.readString();
        caminhoArquivo = in.readString();
        titulo = in.readString();
    }

    public static final Creator<ListarAtividadeConteudo> CREATOR = new Creator<ListarAtividadeConteudo>() {
        @Override
        public ListarAtividadeConteudo createFromParcel(Parcel in) {
            return new ListarAtividadeConteudo(in);
        }

        @Override
        public ListarAtividadeConteudo[] newArray(int size) {
            return new ListarAtividadeConteudo[size];
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListarAtividadeConteudo)) return false;
        ListarAtividadeConteudo that = (ListarAtividadeConteudo) o;
        return getDescricaoAtividade().equals(that.getDescricaoAtividade()) &&
                getMateria().equals(that.getMateria()) &&
                getProfessor().equals(that.getProfessor()) &&
                getCaminhoArquivo().equals(that.getCaminhoArquivo()) &&
                getTitulo().equals(that.getTitulo());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getDescricaoAtividade(), getMateria(), getProfessor(), getCaminhoArquivo(), getTitulo());
    }

    @Override
    public String toString() {
        return "ListarAtividadeConteudo{" +
                "descricaoAtividade='" + descricaoAtividade + '\'' +
                ", materia='" + materia + '\'' +
                ", professor='" + professor + '\'' +
                ", caminhoArquivo='" + caminhoArquivo + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(descricaoAtividade);
        parcel.writeString(materia);
        parcel.writeString(professor);
        parcel.writeString(caminhoArquivo);
        parcel.writeString(titulo);
    }
}
