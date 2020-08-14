package com.example.myapplication.content.aluno.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileAlunoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfileAlunoViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}