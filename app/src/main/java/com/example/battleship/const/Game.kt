package com.example.battleship.const

object Game {
    private val game1 = "212122" +
                        "111111" +
                        "111122" +
                        "112111" +
                        "112121" +
                        "112111"

    private val game2 = "21111112" +
                        "21211212" +
                        "11211112" +
                        "21212111" +
                        "11211121" +
                        "11111121" +
                        "21222111" +
                        "21111112"

    val gameList = listOf(game1, game2)
    val ship = listOf(listOf(3,2,2,1,1,1), listOf(4,3,3,2,2,2,1,1,1,1))
}