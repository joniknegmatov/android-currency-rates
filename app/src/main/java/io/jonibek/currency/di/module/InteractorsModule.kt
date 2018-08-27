package io.jonibek.currency.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.currency.data.remote.HttpApi
import io.jonibek.currency.data.remote.RemoteDataSourceImpl

@Module
class InteractorsModule {

    @Provides
    fun provideFindItemsInteractor(httpApi: HttpApi): RemoteDataSourceImpl {
        return RemoteDataSourceImpl(httpApi)
    }

}