package com.ant8.flowplayground.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val INITIAL_VALUE = 0
    private val channel = Channel<Intent>()
    private val _stateFlow = MutableStateFlow(INITIAL_VALUE)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            channel.consumeAsFlow()
                .map {
                    when (it) {
                        Intent.MINUS -> -1
                        Intent.PLUS -> 1
                    }
                }
                .scan(INITIAL_VALUE) { accumulator, value -> accumulator + value }
                .onEach { _stateFlow.emit(it) }
                .collect()
        }
    }

    fun sendIntent(intent: Intent) {
        viewModelScope.launch {
            channel.send(intent)
        }
    }

    enum class Intent {
        MINUS, PLUS
    }
}