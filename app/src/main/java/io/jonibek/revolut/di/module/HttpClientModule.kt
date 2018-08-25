package io.jonibek.revolut.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.revolut.data.remote.CurrencyApi
import io.jonibek.revolut.data.remote.HttpApi

@Module
class HttpClientModule {

    @Provides fun provideHttpApiClient() : HttpApi {
        return CurrencyApi.getClient()!!.create(HttpApi::class.java)
    }
}