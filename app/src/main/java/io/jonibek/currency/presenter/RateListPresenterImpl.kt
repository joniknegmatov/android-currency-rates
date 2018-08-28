package io.jonibek.currency.presenter

import io.jonibek.currency.data.remote.RemoteDataSource
import io.jonibek.currency.data.remote.RemoteDataSourceCallback
import io.jonibek.currency.data.remote.json.BaseResult
import java.math.BigDecimal
import java.net.UnknownHostException

class RateListPresenterImpl(var baseCurrency: String = "EUR",
                            var amount : BigDecimal = BigDecimal.ZERO,
                            var rateListView: RateListContract.RateListView,
                            var remoteDataSource: RemoteDataSource)
    : RateListContract.RatePresenter, RemoteDataSourceCallback {


    private var lastRates = mapOf<String, BigDecimal>()

    init {
        remoteDataSource.setCallback(this)
    }

    override fun setCurrency(baseRate: String,inputAmount : String) {
        changeAmount(inputAmount)
        baseCurrency = baseRate
        getCurrencyRates()
    }

    override fun changeAmount(inputAmount: String) {
        amount = if(inputAmount.isEmpty()) BigDecimal.ZERO else inputAmount.toBigDecimal()
    }

    override fun getCurrencyRates() {
        remoteDataSource.getRates(baseCurrency)
    }

    override fun setRatesResult(baseResult: BaseResult) {
        lastRates = baseResult.rates
        val result = mutableMapOf<String, String>()
        for (s : Map.Entry<String,BigDecimal> in lastRates){
            result[s.key] = (amount * s.value).toString()
        }
        rateListView.setResult(result)
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