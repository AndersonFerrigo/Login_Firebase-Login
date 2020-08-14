package com.example.myapplication.content.professor.ui.novo_conteudo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NovoConteudoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NovoConteudoViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}