package com.adesire.kmmdemo.shared.repository

import com.adesire.kmmdemo.shared.Restaurant
import com.adesire.kmmdemo.shared.api.RestaurantApi
import io.github.aakira.napier.Napier

class Repository(private val api: RestaurantApi) {

    suspend fun getRestaurantList(): NetworkResponse<List<Restaurant>> =
        makeRepositoryRequest {
            val resp = api.getRestaurantList()
            resp
        }

    private suspend fun <T> makeRepositoryRequest(req: suspend () -> T): NetworkResponse<T> = try {
        Success(req())
    } catch (e: Exception) {
        Napier.e(e.stackTraceToString())
        println(e.stackTraceToString())
        /*when (e) {
            is IOException -> NetworkError
            is HttpException -> {
                HttpError(e.code(), e.getMessageFromHttpError())
            }
            else -> HttpError(2020, e.localizedMessage ?: "Unexpected error")
        }*/
        NetworkError
    }

}