package io.jonibek.revolut.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.jonibek.revolut.App
import io.jonibek.revolut.di.component.AppComponent

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComponent(App.get(this).component() as AppComponent)

    }

    abstract fun  setupComponent(app : AppComponent)
}