package com.ant8.flowplayground.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val INITIAL_VALUE = 0
    private var counter = INITIAL_VALUE
    private val _stateFlow = MutableStateFlow<Int>(INITIAL_VALUE)
    val stateFlow = _stateFlow.asStateFlow()

    suspend fun increment() {
        counter++
        _stateFlow.emit(counter)
    }


}