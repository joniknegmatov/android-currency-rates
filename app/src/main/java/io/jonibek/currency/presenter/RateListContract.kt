package io.jonibek.currency.presenter

import java.math.BigDecimal

class RateListContract {

    interface RatePresenter {

        fun setCurrency(baseRate: String, inputAmount : String)

        fun getCurrencyRates()

        fun changeAmount(inputAmount: String)

        fun onPause()
    }

    interface RateListView{

        fun setResult(baseCurrency : String, currencies : Map<String,BigDecimal>)

        fun onError(message : String)

        fun noNetworkConnectionError()
    }
}
