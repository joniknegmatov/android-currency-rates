package io.jonibek.revolut.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.revolut.data.remote.RemoteDataSourceImpl
import io.jonibek.revolut.presenter.RateListContract
import io.jonibek.revolut.presenter.RateListPresenterImpl
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