package com.example.battleship.bean

data class GameHistory (
    val email : String,
    val gameId : String,
    val startTime : String,
    val gameTime : Int,
    val state : Int
)