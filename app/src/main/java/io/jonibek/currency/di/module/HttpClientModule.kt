package io.jonibek.currency.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.currency.data.remote.CurrencyApi
import io.jonibek.currency.data.remote.HttpApi

@Module
class HttpClientModule {

    @Provides fun provideHttpApiClient() : HttpApi {
        return CurrencyApi.getClient()!!.create(HttpApi::class.java)
    }
}