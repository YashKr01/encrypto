package com.example.encrypto.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.encrypto.database.UserDatabase;
import com.example.encrypto.models.Message;
import com.example.encrypto.models.User;
import com.example.encrypto.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UsersRepository {

    private MutableLiveData<List<User>> list = new MutableLiveData<>();
    private FirebaseUser firebaseUser;
    private UserDatabase userDatabase;
    private Executor executor;

    public UsersRepository(Context context) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userDatabase = UserDatabase.getDatabase(context);
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * get users list wrapped in live data for live changes
     */
    public MutableLiveData<List<User>> getUsersList() {

        // all data will be stored in this list
        List<User> userList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(Constants.DATABASE_USERS_KEY)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User item = dataSnapshot.getValue(User.class);
                            // add all users except the current user
                            if (!item.getUserId().equals(firebaseUser.getUid())) userList.add(item);
                        }
                        list.postValue(userList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        list.postValue(null);
                    }
                });

        return list;
    }

    /**
     * for retrieving messages
     */

    private MutableLiveData<List<Message>> messageList = new MutableLiveData<>();

    public MutableLiveData<List<Message>> getMessageList(String CURRENT_USER_ID, String RECEIVER_ID) {

        List<Message> messages = new ArrayList<>();

        // end point for messages
        FirebaseDatabase.getInstance().getReference(Constants.DATABASE_MESSAGES_KEY)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        messages.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Message message = dataSnapshot.getValue(Message.class);

                            // if current user is sender and opposite user is receiver or vice versa, add message to list
                            if ((message.getSenderId().equals(CURRENT_USER_ID) && message.getReceiverId().equals(RECEIVER_ID))
                                    || (message.getSenderId().equals(RECEIVER_ID) && message.getReceiverId().equals(CURRENT_USER_ID))) {
                                messages.add(message);
                            }
                        }
                        messageList.postValue(messages);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        messageList.postValue(null);
                    }
                });

        return messageList;
    }

    public void insertUser(User user) {
        executor.execute(() -> {
            userDatabase.getUserDao().insertUser(user);
        });
    }

    public LiveData<List<User>> getFavList() {
        return userDatabase.getUserDao().getList();
    }

    public void deleteUser(User user) {
        executor.execute(() -> {
            userDatabase.getUserDao().deleteUser(user);
        });
    }

}
