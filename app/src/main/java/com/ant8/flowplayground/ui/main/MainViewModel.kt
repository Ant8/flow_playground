package com.ant8.flowplayground.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
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
                .flatMapConcat { mapIntent(it) }
                .scan(INITIAL_VALUE, ::reduceIntent)
                .onEach { _stateFlow.emit(it) }
                .collect()
        }
    }

    fun sendIntent(intent: Intent) {
        viewModelScope.launch {
            channel.send(intent)
        }
    }

    private suspend fun mapIntent(intent: Intent): Flow<Int> =
        when (intent) {
            Intent.MULTIPLE_MINUS -> flow {
                delay(600)
                emit(-1)
                delay(600)
                emit(-1)
            }
            Intent.PLUS -> flow { emit(1) }
        }

    private suspend fun reduceIntent(accumulator: Int, value: Int): Int {
        delay(100)
        return accumulator + value
    }

    enum class Intent {
        MULTIPLE_MINUS, PLUS
    }
}