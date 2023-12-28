package com.example.githubfavoritesapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubfavoritesapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getUsers() : LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users: UserEntity)

    @Delete
    fun delete(users: UserEntity)

    @Query("SELECT * FROM users WHERE favorite = 1")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

}