package io.jonibek.revolut.di.component

import dagger.Component
import io.jonibek.revolut.App
import io.jonibek.revolut.data.remote.HttpApi
import io.jonibek.revolut.data.remote.RemoteDataSourceImpl
import io.jonibek.revolut.di.module.AppModule
import io.jonibek.revolut.di.module.HttpClientModule
import io.jonibek.revolut.di.module.InteractorsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (HttpClientModule::class), (InteractorsModule::class)])
interface AppComponent {

    fun inject(application: App)

    fun getRemoteDataSource(): RemoteDataSourceImpl

    fun getHttpClient() : HttpApi

}