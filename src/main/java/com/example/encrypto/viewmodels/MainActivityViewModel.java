package com.example.encrypto.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.encrypto.models.User;
import com.example.encrypto.repositories.UsersRepository;

public class MainActivityViewModel extends AndroidViewModel {

    private UsersRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new UsersRepository(application);
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

}
