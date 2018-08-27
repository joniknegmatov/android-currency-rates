package io.jonibek.currency.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.currency.App
import javax.inject.Singleton

@Module
class AppModule(var application: App) {

    @Provides @Singleton
    fun provideApplication() : App {
        return application
    }

}