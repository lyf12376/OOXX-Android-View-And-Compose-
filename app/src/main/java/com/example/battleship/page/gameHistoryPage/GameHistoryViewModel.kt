package com.example.battleship.page.gameHistoryPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.battleship.bean.GameHistory
import com.example.battleship.const.User
import com.example.battleship.network.GameHistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GameHistoryViewModel @Inject constructor(
    private val gameHistoryService: GameHistoryService,
): ViewModel() {



    private val _historyList = MutableStateFlow<List<GameHistory>>(emptyList())
    val historyList:StateFlow<List<GameHistory>> = _historyList
    init {
        fetchHistory()
    }

    private fun fetchHistory(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                try {
                    val gameHistoryResponse = gameHistoryService.getGameHistoryByEmail(User.email)
                    _historyList.value = gameHistoryResponse.data
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

}