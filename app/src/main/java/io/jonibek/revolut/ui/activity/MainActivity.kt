package io.jonibek.revolut.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import io.jonibek.revolut.R
import io.jonibek.revolut.data.remote.json.BaseResult
import io.jonibek.revolut.di.component.AppComponent
import io.jonibek.revolut.di.component.DaggerRateListComponent
import io.jonibek.revolut.di.module.RateListModule
import io.jonibek.revolut.presenter.RateListContract
import io.jonibek.revolut.ui.adapter.CurrencyChangeCallback
import io.jonibek.revolut.ui.adapter.CurrencyRateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), RateListContract.RateListView, CurrencyChangeCallback {

    @Inject
    lateinit var presenter : RateListContract.RatePresenter

    lateinit var adapter: CurrencyRateAdapter

    override fun setupComponent(appComponent: AppComponent) {
        DaggerRateListComponent.builder()
                .appComponent(appComponent)
                .rateListModule(RateListModule(this))
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAdapter()
    }

    private fun getCurrencyRates(){
        presenter.getCurrencyRates()
    }

    private fun setupAdapter(){
        adapter = CurrencyRateAdapter(this)
        currencyList.layoutManager = LinearLayoutManager(this)
        currencyList.adapter = adapter
    }

    override fun setCurrencyRateList(baseResult: BaseResult) {
        adapter.setData(baseResult)
    }

    override fun onCurrencyChange(currencyCode: String) {
        presenter.setCurrency(currencyCode)
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun noNetworkConnectionError() {
        Toast.makeText(this, "No network", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        getCurrencyRates()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}
