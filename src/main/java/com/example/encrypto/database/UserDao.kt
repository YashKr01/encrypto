package com.example.encrypto.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.encrypto.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getList(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

}