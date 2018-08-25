package io.jonibek.revolut.presenter

import io.jonibek.revolut.data.remote.json.BaseResult

class RateListContract {

    interface RatePresenter {

        fun setCurrency(baseRate: String)

        fun getCurrencyRates()

        fun onPause()
    }

    interface RateListView{
        fun setCurrencyRateList(baseResult: BaseResult)

        fun onError(message : String)

        fun noNetworkConnectionError()
    }
}
