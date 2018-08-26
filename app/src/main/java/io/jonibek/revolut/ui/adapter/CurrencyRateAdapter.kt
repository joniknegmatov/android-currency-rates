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
import io.jonibek.revolut.util.CurrencyHelper
import java.text.DecimalFormat
import android.support.v7.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.list_item_currency.view.*


class CurrencyRateAdapter(private var currencyChangeCallback: CurrencyChangeCallback) : RecyclerView.Adapter<CurrencyRateAdapter.CurrencyViewHolder>(), CurrencyCallback, TextWatcher {

    private lateinit var currentCurrency: String
    private var currencyContainer: CurrencyContainer? = null
    private var amount: Float = 1f

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }

    fun setData(baseResult: BaseResult) {
        if (currencyContainer == null) {
            currencyContainer = CurrencyContainer(baseResult.base, baseResult.rates)
            currentCurrency = baseResult.base
        } else {
            currencyContainer!!.updateValues(baseResult.rates)
            notifyItemRangeChanged(1, currencyContainer!!.getCurrenciesAmount() - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_currency, parent, false)
        return CurrencyViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return if (currencyContainer == null) 0 else currencyContainer!!.getCurrenciesAmount()
    }

    override fun onBindViewHolder(viewHolder: CurrencyViewHolder, position: Int) {
        if (position == 0) {
            viewHolder.itemAmount.removeTextChangedListener(this)
            viewHolder.itemAmount.setText(DecimalFormat("#.##").format(amount))
            viewHolder.itemCode.text = currentCurrency
            viewHolder.itemAmount.addTextChangedListener(this)
        } else {
            viewHolder.itemAmount.removeTextChangedListener(this)
            val pair = currencyContainer!!.getCurrencyName(position)
            val currencyName = pair.first
            val rate = pair.second!! * amount
            viewHolder.itemCode.text = currencyName
            viewHolder.itemAmount.setText(DecimalFormat("#.##").format(rate))
        }

        val currencyInfo = CurrencyHelper.getCurrencyInfo(if (position == 0) currentCurrency else currencyContainer!!.getCurrencyName(position).first)
        viewHolder.itemName.text = currencyInfo.first
        viewHolder.itemSymbol.text = currencyInfo.second
    }

    override fun onViewDetachedFromWindow(holder: CurrencyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.itemCode.text.toString() == currentCurrency) {
            holder.itemAmount.removeTextChangedListener(this)
        }
    }


    override fun changeCurrency(currencyCode: String, currencyAmount: String) {
        if (currencyCode != currentCurrency) {
            currentCurrency = currencyCode
            amount = if (currencyAmount.isEmpty()) 1.0f else currencyAmount.toFloat()
            val index = currencyContainer!!.moveCurrencyToTop(currencyCode)
            notifyItemMoved(index, 0)
            notifyItemChanged(index)
            notifyItemChanged(0)
            currencyChangeCallback.onCurrencyChange(currentCurrency)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        amount = if (text!!.isEmpty()) 0f else text.toString().toFloat()
        notifyItemRangeChanged(1, currencyContainer!!.getCurrenciesAmount() - 1)
    }

    inner class CurrencyViewHolder(itemView: View, private var currencyCallback: CurrencyCallback) : RecyclerView.ViewHolder(itemView), View.OnFocusChangeListener {
        override fun onFocusChange(p0: View?, hasFocus: Boolean) {
            if (hasFocus)
                currencyCallback.changeCurrency(itemCode.text.toString(), itemAmount.text.toString())
        }

        val itemCode: TextView = itemView.textViewCurrencyCode
        val itemAmount: EditText = itemView.editTextAmount
        val itemName: TextView = itemView.textViewCurrencyName
        val itemSymbol: TextView = itemView.textViewCurrencySymbol

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
