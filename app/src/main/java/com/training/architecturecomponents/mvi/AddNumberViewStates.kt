package com.training.architecturecomponents.mvi

sealed class AddNumberViewStates {
    // results -> function -> model
    object Idle : AddNumberViewStates()
    data class AddResult(val number:Int):AddNumberViewStates()
    data class Error(val mess:String):AddNumberViewStates()
}