package com.example.submission3_mygithubapp.data.remote

import com.example.submission3_mygithubapp.api.DetailUser
import com.example.submission3_mygithubapp.api.User
import com.example.submission3_mygithubapp.api.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser (
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    suspend fun getDetailUser (
        @Path("username") username: String
    ) : DetailUser

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers (
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing (
        @Path("username") username: String
    ) : Call<ArrayList<User>>
}