package com.example.myapplication.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class RecuperarSenhaUsuario {

    private String recuperaNomeUsuario;
    private String recuperaEmailUsuario;
    private String recuperaPerfilUsuario;

    public RecuperarSenhaUsuario() {}

    public String getRecuperaNomeUsuario() {
        return recuperaNomeUsuario;
    }

    public void setRecuperaNomeUsuario(String recuperaNomeUsuario) {
        this.recuperaNomeUsuario = recuperaNomeUsuario;
    }

    public String getRecuperaEmailUsuario() {
        return recuperaEmailUsuario;
    }

    public void setRecuperaEmailUsuario(String recuperaEmailUsuario) {
        this.recuperaEmailUsuario = recuperaEmailUsuario;
    }

    public String getRecuperaPerfilUsuario() {
        return recuperaPerfilUsuario;
    }

    public void setRecuperaPerfilUsuario(String recuperaPerfilUsuario) {
        this.recuperaPerfilUsuario = recuperaPerfilUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecuperarSenhaUsuario)) return false;
        RecuperarSenhaUsuario that = (RecuperarSenhaUsuario) o;
        return getRecuperaNomeUsuario().equals(that.getRecuperaNomeUsuario()) &&
                getRecuperaEmailUsuario().equals(that.getRecuperaEmailUsuario()) &&
                getRecuperaPerfilUsuario().equals(that.getRecuperaPerfilUsuario());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getRecuperaNomeUsuario(), getRecuperaEmailUsuario(), getRecuperaPerfilUsuario());
    }

    @Override
    public String toString() {
        return "RecuperarSenhaUsuario{" +
                "recuperaNomeUsuario='" + recuperaNomeUsuario + '\'' +
                ", recuperaEmailUsuario='" + recuperaEmailUsuario + '\'' +
                ", recuperaPerfilUsuario='" + recuperaPerfilUsuario + '\'' +
                '}';
    }
}
