package io.jonibek.revolut.data.remote

import io.jonibek.revolut.data.remote.json.BaseResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RemoteDataSourceImpl(var mHttpApi: HttpApi) : RemoteDataSource {

    private var currentCurrency: String = "EUR"
    private var remoteDataSourceCallback: RemoteDataSourceCallback? = null
    private var subscribtion: Disposable? = null

    override fun unsubscribe() {
        if (subscribtion != null)
            subscribtion!!.dispose()
    }


    override fun setCallback(remoteDataSourceCallback: RemoteDataSourceCallback) {
        this.remoteDataSourceCallback = remoteDataSourceCallback
    }

    private fun getObservable(): Observable<BaseResult> {
        return Observable.interval(1, TimeUnit.SECONDS)
                .flatMap { mHttpApi.getRates(currentCurrency) }
                .observeOn(AndroidSchedulers.mainThread())
    }


    override fun getRates(baseCurrency: String) {
        currentCurrency = baseCurrency
        if (subscribtion != null)
            subscribtion!!.dispose()
        subscribtion = getObservable()
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    remoteDataSourceCallback!!.setRatesResult(result)
                }, { error ->
                    remoteDataSourceCallback!!.onError(error as Exception)
                })
    }
}