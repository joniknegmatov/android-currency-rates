package io.jonibek.currency.di.component

import dagger.Component
import io.jonibek.currency.di.ActivityScope
import io.jonibek.currency.di.module.RateListModule
import io.jonibek.currency.presenter.RateListContract
import io.jonibek.currency.ui.activity.MainActivity
import org.jetbrains.annotations.NotNull

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(RateListModule::class)])
interface RateListComponent {

    var mainPresenter: RateListContract.RatePresenter?

    fun inject(@NotNull categoryActivity: MainActivity)

}