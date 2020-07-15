package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.onReceiveOrNull
import kotlinx.coroutines.flow.*

class MainActivityViewModel : ViewModel() {
    private val _state = MutableStateFlow(0)
    val state: Flow<Int> = _state
    private val _state2 = MutableStateFlow(0)

    val flow = flow {
        (1..99).forEach {
            emit(it)
        }
    }
    val channel = Channel<Int>(capacity = Channel.UNLIMITED).apply {
        viewModelScope.launch {
            (0..99).forEach {
                send(it)
                delay(1000)
            }
        }
    }

    fun increment() {
//        viewModelScope.launch {
//            (1..100).forEach {
//                if (it == 10)
//                    currentCoroutineContext().cancel()
//
//                delay(1000)
//                _state.value++
//            }
//        }


        viewModelScope.launch {
            channel.consumeEach {
                Log.d("Teste", "Collector 2: $it")
            }

        }
    }

}
