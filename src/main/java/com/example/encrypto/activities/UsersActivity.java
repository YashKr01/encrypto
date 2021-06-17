package com.example.encrypto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.encrypto.adapters.UsersAdapter;
import com.example.encrypto.databinding.ActivityUsersBinding;
import com.example.encrypto.listeners.UserClickListener;
import com.example.encrypto.models.User;
import com.example.encrypto.utils.Constants;
import com.example.encrypto.viewmodels.UsersViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements UserClickListener {

    private ActivityUsersBinding binding;
    private UsersViewModel viewModel;

    private UsersAdapter usersAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialise view model
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        // setup list, adapter and recyclerview
        userList = new ArrayList<>();
        usersAdapter = new UsersAdapter(this, userList, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(usersAdapter);

        // observe data
        viewModel.getUsersList().observe(this, users -> {
            userList.clear();
            userList.addAll(users);
            usersAdapter.notifyDataSetChanged();
            binding.progressBar.setVisibility(View.GONE);
        });


    }

    @Override
    public void onUserItemClicked(@NotNull User user) {

        // redirect to messaging activity taking username and profile image url
        Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
        intent.putExtra(Constants.USER, user);
        startActivity(intent);

    }

}