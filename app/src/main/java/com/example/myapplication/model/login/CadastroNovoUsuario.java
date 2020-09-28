package com.example.myapplication.model.login;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class CadastroNovoUsuario  implements Parcelable {

    /**
     * @since 22/09/2020
     */

    private String uid;
    private String nome;
    private String email;
    private String senha;
    private String perfil;
    private String materia;
    private String turma;

    public CadastroNovoUsuario() {}

    protected CadastroNovoUsuario(Parcel in) {
        uid = in.readString();
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
        perfil = in.readString();
        materia = in.readString();
        turma = in.readString();
    }

    public static final Creator<CadastroNovoUsuario> CREATOR = new Creator<CadastroNovoUsuario>() {
        @Override
        public CadastroNovoUsuario createFromParcel(Parcel in) {
            return new CadastroNovoUsuario(in);
        }

        @Override
        public CadastroNovoUsuario[] newArray(int size) {
            return new CadastroNovoUsuario[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CadastroNovoUsuario)) return false;
        CadastroNovoUsuario cadastroNovoUsuario = (CadastroNovoUsuario) o;
        return getUid().equals(cadastroNovoUsuario.getUid()) &&
                getNome().equals(cadastroNovoUsuario.getNome()) &&
                getEmail().equals(cadastroNovoUsuario.getEmail()) &&
                getSenha().equals(cadastroNovoUsuario.getSenha()) &&
                getPerfil().equals(cadastroNovoUsuario.getPerfil()) &&
                getMateria().equals(cadastroNovoUsuario.getMateria()) &&
                getTurma().equals(cadastroNovoUsuario.getTurma());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getNome(), getEmail(), getSenha(), getPerfil(), getMateria(), getTurma());
    }

    @Override
    public String toString() {
        return "Cadastro{" +
                "uid='" + uid + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", perfil='" + perfil + '\'' +
                ", materia='" + materia + '\'' +
                ", turma='" + turma + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(nome);
        parcel.writeString(email);
        parcel.writeString(senha);
        parcel.writeString(perfil);
        parcel.writeString(materia);
        parcel.writeString(turma);
    }
}
