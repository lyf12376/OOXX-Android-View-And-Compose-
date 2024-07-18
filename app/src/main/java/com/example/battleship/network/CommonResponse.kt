package com.example.battleship.network

data class CommonResponse<T>(
    val code:Int,
    val message:String,
    val data:T
)
