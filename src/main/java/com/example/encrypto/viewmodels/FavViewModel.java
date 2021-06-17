package com.example.encrypto.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.encrypto.models.User;
import com.example.encrypto.repositories.UsersRepository;

import java.util.List;

public class FavViewModel extends AndroidViewModel {

    private UsersRepository repository;

    public FavViewModel(@NonNull Application application) {
        super(application);
        repository = new UsersRepository(application);
    }

    public LiveData<List<User>> getFavList(){
        return repository.getFavList();
    }

    public void deleteUser(User user){
        repository.deleteUser(user);
    }

}
