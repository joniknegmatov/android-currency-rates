package io.jonibek.currency.di.component

import dagger.Component
import io.jonibek.currency.App
import io.jonibek.currency.data.remote.HttpApi
import io.jonibek.currency.data.remote.RemoteDataSourceImpl
import io.jonibek.currency.di.module.AppModule
import io.jonibek.currency.di.module.HttpClientModule
import io.jonibek.currency.di.module.InteractorsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (HttpClientModule::class), (InteractorsModule::class)])
interface AppComponent {

    fun inject(application: App)

    fun getRemoteDataSource(): RemoteDataSourceImpl

    fun getHttpClient() : HttpApi

}