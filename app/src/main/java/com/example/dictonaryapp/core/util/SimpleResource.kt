package com.example.dictonaryapp.core.util

sealed class SimpleResource<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Loading<T>(data: T? = null) : SimpleResource<T>(data)
    class Success<T>(data: T?) : SimpleResource<T>(data)
    class Error<T>(message: String, data: T? = null) : SimpleResource<T>(data, message)
}