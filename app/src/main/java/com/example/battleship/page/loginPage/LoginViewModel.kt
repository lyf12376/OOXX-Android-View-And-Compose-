package com.example.battleship.page.loginPage

import android.accounts.Account
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.battleship.const.User
import com.example.battleship.localDatabase.savedUser.SavedUser
import com.example.battleship.localDatabase.savedUser.SavedUserDao
import com.example.battleship.network.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedUserDao: SavedUserDao,
    private val userService: UserService
) : ViewModel(){

    val savedUserList:Flow<List<SavedUser>> = getSavedUser()

    private fun getSavedUser () = savedUserDao.getSavedUsers()

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess = _isLoginSuccess.asStateFlow()

    private val _isLoginFailed = MutableStateFlow(false)
    val isLoginFailed = _isLoginFailed.asStateFlow()

    fun login(account:String,password:String){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                try {
                    val userResponse = userService.login(account,password)
                    if (userResponse.code == 200){
                        User.userName = userResponse.data.userName
                        User.email = userResponse.data.email
                        _isLoginSuccess.value = true
                    }else{
                        _isLoginFailed.value = true
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    fun dismissDialog(){
        _isLoginSuccess.value = false
        _isLoginFailed.value = false
    }

    fun rememberAccount(account: String,password: String){
        viewModelScope.launch {
            savedUserDao.saveUsers(SavedUser(account = account, password = password))
        }
    }

    fun unRememberAccount(account: String){
        viewModelScope.launch {
            savedUserDao.unRemember(account = account)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            savedUserDao.deleteAll()
        }
    }
}