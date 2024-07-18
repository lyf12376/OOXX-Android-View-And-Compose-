package com.example.battleship.const

import com.example.battleship.network.User

object User {
    var email:String = ""
    var password:String = ""
    var userName:String = ""

    fun setUser(user: User){
        email = user.email
        password = user.password
        userName = user.userName
    }
}