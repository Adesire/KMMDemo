package com.adesire.kmmdemo.shared.repository

import com.adesire.kmmdemo.shared.api.RestaurantApi
import org.kodein.di.*

val InjectionModule = DI {
    bind<RestaurantApi>() with provider { RestaurantApi() }

    bind<Repository>() with singleton { Repository(instance()) }
}
