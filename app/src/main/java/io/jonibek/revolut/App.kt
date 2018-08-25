package io.jonibek.revolut

import android.app.Application
import android.content.Context
import io.jonibek.revolut.di.component.AppComponent
import io.jonibek.revolut.di.component.DaggerAppComponent
import io.jonibek.revolut.di.module.AppModule

class App : Application() {

    lateinit var component: AppComponent

    companion object {
        fun get(context: Context): App {
            return context.applicationContext as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupGraph()
    }

    private fun setupGraph() {
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
    }

    fun component(): AppComponent? {
        return component
    }
}