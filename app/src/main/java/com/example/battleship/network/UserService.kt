package com.example.battleship.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("/user/code")
    suspend fun sendVerificationCode(@Query("email") email: String): CommonResponse<String>

    @GET("/user/login")
    suspend fun login(
        @Query("userName") userName: String,
        @Query("password") password: String
    ): CommonResponse<User>

    @POST("/user/register")
    suspend fun register(@Body user: User): CommonResponse<String>

    @GET("/user/updateEmail")
    suspend fun updateEmail(
        @Query("userName") userName: String,
        @Query("email") email: String
    ): CommonResponse<String>

    @GET("/user/updateUserName")
    suspend fun updateUserName(
        @Query("newUserName") newUserName: String,
        @Query("userName") userName: String
    ): CommonResponse<String>

    @GET("/user/updatePassword")
    suspend fun updatePassword(
        @Query("userName") userName: String,
        @Query("oldPassword") oldPassword:String,
        @Query("password") password: String
    ): CommonResponse<String>
}