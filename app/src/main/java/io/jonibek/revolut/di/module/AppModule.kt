package io.jonibek.revolut.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.revolut.App
import javax.inject.Singleton

@Module
class AppModule(var application: App) {

    @Provides @Singleton
    fun provideApplication() : App {
        return application
    }

}