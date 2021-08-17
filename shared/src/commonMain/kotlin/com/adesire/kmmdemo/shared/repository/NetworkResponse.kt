package com.adesire.kmmdemo.shared.repository

sealed class NetworkResponse<out T>
data class Success<T>(val data: T) : NetworkResponse<T>()
data class HttpError(val code: Int? = null, val message: String? = null): NetworkResponse<Nothing>()
object NetworkError : NetworkResponse<Nothing>()