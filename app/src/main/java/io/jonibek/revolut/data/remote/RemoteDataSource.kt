package io.jonibek.revolut.data.remote

import io.jonibek.revolut.data.remote.json.BaseResult

interface RemoteDataSource {

    fun getRates(baseCurrency : String)

    fun setCallback(remoteDataSourceCallback: RemoteDataSourceCallback)

    fun unsubscribe()
}

interface RemoteDataSourceCallback{
    fun setRatesResult(baseResult: BaseResult)

    fun onError(message : Exception)
}