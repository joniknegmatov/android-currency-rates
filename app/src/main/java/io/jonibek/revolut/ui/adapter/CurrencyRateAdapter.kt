package io.jonibek.revolut.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import io.jonibek.revolut.R
import io.jonibek.revolut.data.local.CurrencyContainer
import io.jonibek.revolut.data.remote.json.BaseResult
import kotlinx.android.synthetic.main.list_item_currency.view.*
import java.text.DecimalFormat
import java.util.*

class CurrencyRateAdapter(var currencyChangeCallback: CurrencyChangeCallback) : RecyclerView.Adapter<CurrencyRateAdapter.CurrencyViewHolder>(), CurrencyCallback, TextWatcher {

    private lateinit var currentCurrency: String
    private var currencyContainer  : CurrencyContainer? = null
    private var amount: Float = 0f

    fun setData(baseResult: BaseResult) {
        if (currencyContainer == null) {
            currencyContainer = CurrencyContainer(baseResult.base,baseResult.rates)
            currentCurrency = baseResult.base
        } else {
            currencyContainer!!.updateValues(baseResult.rates)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_currency, parent, false)
        return CurrencyViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return if(currencyContainer == null) 0 else currencyContainer!!.getCurrenciesAmount()
    }

    override fun onBindViewHolder(viewHolder: CurrencyViewHolder, position: Int) {
        if (position == 0) {
            viewHolder.itemAmount.removeTextChangedListener(this)
            viewHolder.itemAmount.setText(DecimalFormat("#.##").format(amount))
            viewHolder.itemName.text = currentCurrency
            viewHolder.itemAmount.addTextChangedListener(this)
        } else {
            viewHolder.itemAmount.removeTextChangedListener(this)
            val pair = currencyContainer!!.getCurrencyName(position)
            val currencyName = pair.first
            val rate = pair.second!! * amount
            viewHolder.itemName.text = currencyName
            viewHolder.itemAmount.setText(DecimalFormat("#.##").format(rate))
        }

    }

    override fun onViewDetachedFromWindow(holder: CurrencyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.itemName.text.toString() == currentCurrency) {
            holder.itemAmount.removeTextChangedListener(this)
        }
    }


    override fun changeCurrency(currencyCode: String, currencyAmount : String) {
        if (currencyCode != currentCurrency) {
            currentCurrency = currencyCode
            amount = if(currencyAmount.isNullOrEmpty()) 1.0f else currencyAmount.toFloat()
            val index = currencyContainer!!.moveCurrencyToTop(currencyCode)
            notifyItemMoved(index, 0)
            currencyChangeCallback.onCurrencyChange(currentCurrency)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (!text.isNullOrEmpty() && amount != text.toString().toFloat()) {
            amount = text.toString().toFloat()
            notifyDataSetChanged()
        }
    }

    inner class CurrencyViewHolder(itemView: View, var currencyCallback: CurrencyCallback) : RecyclerView.ViewHolder(itemView), View.OnFocusChangeListener {
        override fun onFocusChange(p0: View?, hasFocus: Boolean) {
            if (hasFocus)
                currencyCallback.changeCurrency(itemName.text.toString(), itemAmount.text.toString())
        }

        val itemName: TextView = itemView.textViewCurrencyName
        val itemAmount: EditText = itemView.editTextAmount

        init {
            itemAmount.onFocusChangeListener = this
        }
    }
}


interface CurrencyCallback {
    fun changeCurrency(currencyCode: String, currencyAmount: String)
}

interface CurrencyChangeCallback {
    fun onCurrencyChange(currencyCode: String)
}
