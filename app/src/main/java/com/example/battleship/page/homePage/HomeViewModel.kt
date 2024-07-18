package com.example.battleship.page.homePage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _level = MutableStateFlow(0)
    val level = _level.asStateFlow()

    fun setLevel(level: Int) {
        _level.value = level
    }
}