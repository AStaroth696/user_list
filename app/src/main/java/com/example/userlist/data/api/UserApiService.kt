package com.example.userlist.data.api

import com.example.userlist.data.api.model.UserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET(".")
    fun getUserList(@Query("page") page: Int, @Query("results") perPage: Int): Call<UserListResponse>

}