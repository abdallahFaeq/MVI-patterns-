package com.training.architecturecomponents.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.io.IOException

class AddNumberViewModel:ViewModel() {
    // activity call viewModel by channel
    var intentChannel = Channel<AddNumberIntent>(Channel.UNLIMITED)

    // viewModel call activity by flow
    private var _states = MutableStateFlow<AddNumberViewStates>(AddNumberViewStates.Idle)
    val states : StateFlow<AddNumberViewStates> get() =  _states

    private var number = 0

    init {
        processActions()
    }
    // process
    fun processActions(){
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collectLatest {
                when (it){
                    is AddNumberIntent.AddNumber -> {
                        reduceResults()
                    }
                }
            }
        }

    }
    // reduce
    fun reduceResults(){
        viewModelScope.launch {
            _states.value
                try {
                    AddNumberViewStates.AddResult(++number)
                    Log.e("number", "number is ${number}" )
                }catch (e:IOException){
                    AddNumberViewStates.Error(e.message.toString())
                }
        }
    }
}