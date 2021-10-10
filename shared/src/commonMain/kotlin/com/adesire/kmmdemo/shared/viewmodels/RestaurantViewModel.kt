package com.adesire.kmmdemo.shared.viewmodels

import com.adesire.kmmdemo.shared.*
import com.adesire.kmmdemo.shared.repository.InjectionModule
import com.adesire.kmmdemo.shared.repository.NetworkError
import com.adesire.kmmdemo.shared.repository.Repository
import com.adesire.kmmdemo.shared.repository.Success
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import org.kodein.di.instance

class RestaurantViewModel: ViewModel() {

    private val repository by InjectionModule.instance<Repository>()
    private val _restaurants = MutableLiveData<List<Restaurant>>(ArrayList())

    fun restaurants(): LiveData<List<Restaurant>> = _restaurants

    fun getListOfRestaurants() = viewModelScope.launch {
        println("getting data from server")
        when (val response = repository.getRestaurantList()) {
            is Success -> {
                _restaurants.postValue(response.data)
            }
            NetworkError -> {
                println("Network Error")
            }
        }
    }
}