package com.example.githubfavoritesapp.data.local.room

import android.content.Context
import androidx.room.*
import com.example.githubfavoritesapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var instance: UsersDatabase ? = null
        fun getInstance(context: Context): UsersDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java, "Users.db"
                ).build()
            }
    }
}