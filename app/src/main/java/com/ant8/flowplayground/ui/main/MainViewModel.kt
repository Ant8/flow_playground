package com.ant8.flowplayground.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val INITIAL_VALUE = 0
    private var counter = INITIAL_VALUE
    private val _stateFlow = MutableStateFlow(INITIAL_VALUE)
    val stateFlow = _stateFlow.asStateFlow()

    fun increment() {
        viewModelScope.launch {
            counter++
            _stateFlow.emit(counter)
        }
    }

}