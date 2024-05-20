package com.training.architecturecomponents.mvi

sealed class AddNumberIntent {
    // action
    object AddNumber : AddNumberIntent()
}