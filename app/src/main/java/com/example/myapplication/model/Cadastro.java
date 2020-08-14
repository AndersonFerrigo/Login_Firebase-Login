package com.example.myapplication.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Cadastro {
    private String uid;
    private String email;
    private String senha;
    private String perfil;

    public Cadastro() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cadastro)) return false;
        Cadastro cadastro = (Cadastro) o;
        return getUid().equals(cadastro.getUid()) &&
                getEmail().equals(cadastro.getEmail()) &&
                getSenha().equals(cadastro.getSenha()) &&
                getPerfil().equals(cadastro.getPerfil());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getEmail(), getSenha(), getPerfil());
    }

    @Override
    public String toString() {
        return "Cadastro{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}
