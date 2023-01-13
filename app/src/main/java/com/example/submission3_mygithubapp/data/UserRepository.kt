package com.example.submission3_mygithubapp.data
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.submission3_mygithubapp.data.lokal.room.UserDao
import com.example.submission3_mygithubapp.data.lokal.entity.UserEntity
import com.example.submission3_mygithubapp.data.remote.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) {

    fun getDetailUser(username: String): LiveData<Result<UserEntity>> = liveData {
        emit(Result.Loading)
        try {
            val detailUser = apiService.getDetailUser(username)
            val isFavorite = userDao.isUserFavorite(detailUser.login)
            userDao.deleteAll()
            userDao.addUser(UserEntity(
                detailUser.login,
                detailUser.avatarUrl,
                detailUser.name,
                detailUser.followers,
                detailUser.following,
                detailUser.publicRepos,
                isFavorite
            ))
        } catch (e: Exception) {
            Log.e("UserRepository", "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<UserEntity>> = userDao.getUser(username).map {Result.Success(it) }
        emitSource(localData)
    }

    fun getFavoriteUser(): LiveData<List<UserEntity>> {
        return userDao.getFavoriteUser()
    }

    suspend fun setFavoriteUser(user: UserEntity, favoriteState: Boolean) {
        user.isFavorite = favoriteState
        userDao.updateUser(user)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, newsDao)
            }.also { instance = it }
    }
}