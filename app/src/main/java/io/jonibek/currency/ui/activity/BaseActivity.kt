package io.jonibek.currency.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.jonibek.currency.App
import io.jonibek.currency.di.component.AppComponent

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComponent(App.get(this).component() as AppComponent)

    }

    abstract fun  setupComponent(app : AppComponent)
}