package com.example.battleship.network

import com.example.battleship.bean.GameHistory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameHistoryService {
    @GET("/history/getHistories")
    suspend fun getGameHistoryByEmail(@Query("email") userAccount: String): CommonResponse<List<GameHistory>>

    @POST("/history/insertHistory")
    suspend fun insertGameHistory(@Body gameHistory: GameHistory): CommonResponse<String>
}