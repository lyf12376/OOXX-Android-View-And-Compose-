package com.example.battleship.page.registerPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.battleship.network.User
import com.example.battleship.network.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userService: UserService
):ViewModel() {
    private val _registerState = MutableStateFlow(0)
    val registerState = _registerState.asStateFlow()

    var errorMessage = ""

    fun register(user: User)
    {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val registerResponse = userService.register(user)
                if (registerResponse.code == 200){
                    _registerState.value = 1
                    com.example.battleship.const.User.setUser(user)
                }else{
                    errorMessage = registerResponse.message
                    _registerState.value = 2
                }
            }
        }
    }
}