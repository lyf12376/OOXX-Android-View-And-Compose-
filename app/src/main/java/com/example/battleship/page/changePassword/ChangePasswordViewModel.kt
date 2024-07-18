package com.example.battleship.page.changePassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.battleship.const.User
import com.example.battleship.network.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userService: UserService
):ViewModel(){
    private val _updateState = MutableStateFlow(0)
    val updateState = _updateState.asStateFlow()

    fun changePassword(oldPassword:String,password:String){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                try {
                    val changeResponse = userService.updatePassword(userName = User.userName,oldPassword, password)
                    when(changeResponse.code){
                        200->{
                            _updateState.value = 1
                        }
                        201->{
                            _updateState.value = 2
                        }
                        400->{
                            _updateState.value = 3
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }
    }
}