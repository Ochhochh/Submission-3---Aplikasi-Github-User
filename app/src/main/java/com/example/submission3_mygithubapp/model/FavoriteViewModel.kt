package com.example.submission3_mygithubapp.model

import androidx.lifecycle.ViewModel
import com.example.submission3_mygithubapp.data.UserRepository

class FavoriteViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getFavoriteUser() = userRepository.getFavoriteUser()
}