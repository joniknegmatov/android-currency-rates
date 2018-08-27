package io.jonibek.currency.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.currency.data.remote.RemoteDataSourceImpl
import io.jonibek.currency.presenter.RateListContract
import io.jonibek.currency.presenter.RateListPresenterImpl
import org.jetbrains.annotations.NotNull

@Module
class RateListModule(var listView : RateListContract.RateListView) {

    @Provides
    fun provideView() : RateListContract.RateListView {
        return listView
    }

    @Provides
    fun providePresenter(@NotNull remoteDataSourceImpl: RemoteDataSourceImpl) : RateListContract.RatePresenter {
       return RateListPresenterImpl(rateListView =  listView, remoteDataSource =  remoteDataSourceImpl)
    }

}