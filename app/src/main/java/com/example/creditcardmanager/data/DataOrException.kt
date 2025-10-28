package com.example.creditcardmanager.data

data class DataOrException<T, Boolean, Exception>(
    val data: T,
    val loading: Boolean,
    val exception: Exception
)