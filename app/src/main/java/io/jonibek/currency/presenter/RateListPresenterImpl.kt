package io.jonibek.currency.presenter

import io.jonibek.currency.data.remote.RemoteDataSource
import io.jonibek.currency.data.remote.RemoteDataSourceCallback
import io.jonibek.currency.data.remote.json.BaseResult
import java.net.UnknownHostException

class RateListPresenterImpl(var baseCurrency: String = "EUR",
                            var rateListView: RateListContract.RateListView,
                            var remoteDataSource: RemoteDataSource)
    : RateListContract.RatePresenter, RemoteDataSourceCallback {

    init {
        remoteDataSource.setCallback(this)

    }

    override fun setCurrency(baseRate: String) {
        baseCurrency = baseRate
        getCurrencyRates()
    }

    override fun getCurrencyRates() {
        remoteDataSource.getRates(baseCurrency)
    }

    override fun setRatesResult(baseResult: BaseResult) {
        rateListView.setCurrencyRateList(baseResult)
    }

    override fun onError(exception: Exception) {
        if (exception is UnknownHostException){
            rateListView.noNetworkConnectionError()
        }else{
            rateListView.onError(exception.message!!)
        }
    }

    override fun onPause() {
        remoteDataSource.unsubscribe()
    }

}