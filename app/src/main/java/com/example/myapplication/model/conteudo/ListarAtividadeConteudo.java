package com.example.myapplication.model.conteudo;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class ListarAtividadeConteudo implements Parcelable {

    /**
     * @since 22/09/2020
     */

    private String professor;
    private String materia;
    private String titulo;
    private String descricao;
    private String documento;

    public ListarAtividadeConteudo(){} // Para Firebase

    public ListarAtividadeConteudo(String professor, String materia, String titulo, String descricao, String documento) {
        this.professor = professor;
        this.materia = materia;
        this.titulo = titulo;
        this.descricao = descricao;
        this.documento = documento;
    }

    protected ListarAtividadeConteudo(Parcel in) {
        professor = in.readString();
        materia = in.readString();
        titulo = in.readString();
        descricao = in.readString();
        documento = in.readString();
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
        if (!(o instanceof ListarAtividadeConteudo)) return false;
        ListarAtividadeConteudo that = (ListarAtividadeConteudo) o;
        return getProfessor().equals(that.getProfessor()) &&
                getMateria().equals(that.getMateria()) &&
                getTitulo().equals(that.getTitulo()) &&
                getDescricao().equals(that.getDescricao()) &&
                getDocumento().equals(that.getDocumento());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getProfessor(), getMateria(), getTitulo(), getDescricao(), getDocumento());
    }

    @Override
    public String toString() {
        return "ListarAtividadeConteudo{" +
                "professor='" + professor + '\'' +
                ", materia='" + materia + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(professor);
        parcel.writeString(materia);
        parcel.writeString(titulo);
        parcel.writeString(descricao);
        parcel.writeString(documento);
    }
}
