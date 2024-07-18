package com.example.battleship.page.gamePage

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.battleship.bean.GameHistory
import com.example.battleship.const.User
import com.example.battleship.localDatabase.game.Game
import com.example.battleship.localDatabase.game.GameDao
import com.example.battleship.network.GameHistoryService
import com.example.battleship.network.UserService
import com.example.battleship.utils.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameDao: GameDao,
    private val gameHistoryService: GameHistoryService
) : ViewModel() {

    private var gamesSize = 0

    init {
        viewModelScope.launch {
            gamesSize = gameDao.getGamesSize()
        }
    }

    private var gameList: Flow<List<Game>> = getAllGames()

    var target = ""

    private val _gameSuccess = MutableStateFlow(false)
    val gameSuccess: StateFlow<Boolean> = _gameSuccess

    private val _submitFailed = MutableStateFlow(false)
    val submitFailed: StateFlow<Boolean> = _submitFailed


    fun reSubmit() {
        _submitFailed.value = false
    }

    private fun getAllGames(): Flow<List<Game>> {
        return gameDao.getAllGames()
    }


    private val _gameDetail = MutableStateFlow<Game?>(null)
    val gameDetail: StateFlow<Game?> = _gameDetail

    fun getGame(level: Int) {
        viewModelScope.launch {
            gameList.collect { games ->
                if (games.isNotEmpty() && level - 1 < games.size) {
                    _gameDetail.value = games[level - 1]
                } else {
                    _gameDetail.value = null
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun check(now: String, time: Int, level: Int) {
        viewModelScope.launch {
            Log.d("TAG", "check: $now \n $target")
            if (now == target) {
                val time1 = LocalDateTime.now()
                val year = time1.year
                val month = time1.monthValue
                val day = time1.dayOfMonth
                val hour = time1.hour
                val minute = time1.minute
                val second = time1.second
                _gameSuccess.value = true
                gameHistoryService.insertGameHistory(
                    GameHistory(
                        email = User.email,
                        gameId = "$level",
                        startTime = "$year-$month-$day $hour:${if (minute <= 9) "0$minute" else minute}:${if (second <= 9) "0$second" else second}",
                        gameTime = time,
                        state = 1
                    )
                )
            } else {
                _submitFailed.value = true
            }
        }

    }

    fun loadMusic(context: Context, musicId: Int) {
        SoundManager.loadSound(context, musicId)
    }

    fun playMusic() {
        SoundManager.playSound()
    }

    fun releaseMusic() {
        SoundManager.release()
    }


}