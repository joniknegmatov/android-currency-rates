package io.jonibek.revolut.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import io.jonibek.revolut.R
import io.jonibek.revolut.data.remote.json.BaseResult
import io.jonibek.revolut.di.component.AppComponent
import io.jonibek.revolut.di.component.DaggerRateListComponent
import io.jonibek.revolut.di.module.RateListModule
import io.jonibek.revolut.presenter.RateListContract
import io.jonibek.revolut.ui.adapter.CurrencyChangeCallback
import io.jonibek.revolut.ui.adapter.CurrencyRateAdapter
import io.jonibek.revolut.util.prettyText
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), RateListContract.RateListView, CurrencyChangeCallback {

    @Inject
    lateinit var presenter: RateListContract.RatePresenter

    private lateinit var adapter: CurrencyRateAdapter
    private lateinit var snackbar: Snackbar

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
        setupSnackbar()
        setupAdapter()
    }

    private fun setupSnackbar() {
        snackbar = Snackbar.make(rootView, R.string.no_network_connection, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.retry) { getCurrencyRates() }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun getCurrencyRates() {
        showProgressBar()
        presenter.getCurrencyRates()
    }

    private fun showProgressBar() {
        if (adapter.itemCount == 0)
            progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun setupAdapter() {
        adapter = CurrencyRateAdapter(this)
        currencyList.layoutManager = LinearLayoutManager(this)
        currencyList.adapter = adapter
    }

    override fun setCurrencyRateList(baseResult: BaseResult) {
        if (isAnyMessageOrIndicatorVisible()) hideAllMessagesAndIndicators()
        textViewLastUpdate.text = String.format(getString(R.string.last_update), Date().prettyText())
        adapter.setData(baseResult)
    }

    private fun isAnyMessageOrIndicatorVisible(): Boolean {
        return snackbar.isShown || textViewLastUpdate.visibility == View.VISIBLE || progressBar.visibility == View.VISIBLE
    }

    private fun hideAllMessagesAndIndicators() {
        snackbar.dismiss()
        textViewLastUpdate.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    override fun onCurrencyChange(currencyCode: String) {
        presenter.setCurrency(currencyCode)
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun noNetworkConnectionError() {
        showNoNetworkMessages()
    }

    private fun showNoNetworkMessages() {
        adapter.noNetworkConnection()
        hideProgressBar()
        snackbar.show()
        if (textViewLastUpdate.text.isNotEmpty())
            textViewLastUpdate.visibility = View.VISIBLE
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

