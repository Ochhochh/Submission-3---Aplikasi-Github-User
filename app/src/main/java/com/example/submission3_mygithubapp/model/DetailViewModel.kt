package com.example.submission3_mygithubapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission3_mygithubapp.data.UserRepository
import com.example.submission3_mygithubapp.data.lokal.entity.UserEntity
import kotlinx.coroutines.launch

class  DetailViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun getDetailUser(username: String) = userRepository.getDetailUser(username)

    fun addFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavoriteUser(user, true)
        }
    }

    fun deleteFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavoriteUser(user, false)
        }
    }
}