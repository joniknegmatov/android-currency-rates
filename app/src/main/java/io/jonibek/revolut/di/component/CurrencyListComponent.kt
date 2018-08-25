package io.jonibek.revolut.di.component

import dagger.Component
import io.jonibek.revolut.di.ActivityScope
import io.jonibek.revolut.di.module.RateListModule
import io.jonibek.revolut.presenter.RateListContract
import io.jonibek.revolut.ui.activity.MainActivity
import org.jetbrains.annotations.NotNull

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(RateListModule::class)])
interface RateListComponent {

    var mainPresenter: RateListContract.RatePresenter?

    fun inject(@NotNull categoryActivity: MainActivity)

}