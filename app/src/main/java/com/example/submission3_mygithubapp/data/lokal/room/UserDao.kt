package com.example.submission3_mygithubapp.data.lokal.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submission3_mygithubapp.data.lokal.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE login = :login")
    fun getUser(login: String): LiveData<UserEntity>

    @Query("SELECT * FROM User WHERE favorite = 1")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user WHERE favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE login = :login AND favorite = 1)")
    fun isUserFavorite(login: String): Boolean
}