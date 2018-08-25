package io.jonibek.revolut.di.module

import dagger.Module
import dagger.Provides
import io.jonibek.revolut.data.remote.HttpApi
import io.jonibek.revolut.data.remote.RemoteDataSourceImpl

@Module
class InteractorsModule {

    @Provides
    fun provideFindItemsInteractor(httpApi: HttpApi): RemoteDataSourceImpl {
        return RemoteDataSourceImpl(httpApi)
    }

}