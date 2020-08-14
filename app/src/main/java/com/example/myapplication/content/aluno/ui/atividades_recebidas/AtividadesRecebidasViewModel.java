package com.example.myapplication.content.aluno.ui.atividades_recebidas;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class AtividadesRecebidasViewModel extends ViewModel {

  private String uID;
  private String tituloNovaAtividade;
  private String nomeProfessorNovaAtividade;
  private String materiaNovaAtividade;

  public AtividadesRecebidasViewModel() { }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getTituloNovaAtividade() {
        return tituloNovaAtividade;
    }

    public void setTituloNovaAtividade(String tituloNovaAtividade) {
        this.tituloNovaAtividade = tituloNovaAtividade;
    }

    public String getNomeProfessorNovaAtividade() {
        return nomeProfessorNovaAtividade;
    }

    public void setNomeProfessorNovaAtividade(String nomeProfessorNovaAtividade) {
        this.nomeProfessorNovaAtividade = nomeProfessorNovaAtividade;
    }

    public String getMateriaNovaAtividade() {
        return materiaNovaAtividade;
    }

    public void setMateriaNovaAtividade(String materiaNovaAtividade) {
        this.materiaNovaAtividade = materiaNovaAtividade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AtividadesRecebidasViewModel)) return false;
        AtividadesRecebidasViewModel that = (AtividadesRecebidasViewModel) o;
        return getuID().equals(that.getuID()) &&
                getTituloNovaAtividade().equals(that.getTituloNovaAtividade()) &&
                getNomeProfessorNovaAtividade().equals(that.getNomeProfessorNovaAtividade()) &&
                getMateriaNovaAtividade().equals(that.getMateriaNovaAtividade());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getuID(), getTituloNovaAtividade(), getNomeProfessorNovaAtividade(), getMateriaNovaAtividade());
    }

    @Override
    public String toString() {
        return "AtividadesRecebidasViewModel{" +
                "uID='" + uID + '\'' +
                ", tituloNovaAtividade='" + tituloNovaAtividade + '\'' +
                ", nomeProfessorNovaAtividade='" + nomeProfessorNovaAtividade + '\'' +
                ", materiaNovaAtividade='" + materiaNovaAtividade + '\'' +
                '}';
    }
}