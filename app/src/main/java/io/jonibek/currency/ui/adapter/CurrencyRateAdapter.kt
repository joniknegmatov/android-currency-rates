package io.jonibek.currency.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import io.jonibek.currency.R
import io.jonibek.currency.data.local.CurrencyContainer
import io.jonibek.currency.util.CurrencyHelper
import java.text.DecimalFormat
import android.support.v7.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.list_item_currency.view.*
import java.math.BigDecimal


class CurrencyRateAdapter(private var currencyChangeCallback: CurrencyChangeCallback) : RecyclerView.Adapter<CurrencyRateAdapter.CurrencyViewHolder>(), CurrencyCallback, TextWatcher {

    private lateinit var currentCurrency: String
    private var currencyContainer: CurrencyContainer? = null
    private var hasConnection: Boolean = true

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    fun noNetworkConnection() {
        hasConnection = false
    }

    fun setData(base : String, values : Map<String,BigDecimal>){
        hasConnection = true
        if (currencyContainer == null) {
            currencyContainer = CurrencyContainer(base, values)
            currentCurrency = base
            notifyDataSetChanged()
        } else if(toChangeValues != null){
            changeCurrency(toChangeValues!!.first, toChangeValues!!.second)
            toChangeValues = null
        } else {
            currencyContainer!!.updateValues(values)
            notifyAllItemsChangedExpectFirst()
        }
    }

    private fun notifyAllItemsChangedExpectFirst(){
        notifyItemRangeChanged(1, itemCount - 1)
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
            viewHolder.itemCode.text = currentCurrency
            viewHolder.itemAmount.addTextChangedListener(this)
        } else {
            viewHolder.itemAmount.removeTextChangedListener(this)
            val pair = currencyContainer!!.getCurrencyNameAndRate(position)
            val currencyName = pair.first
            viewHolder.itemCode.text = currencyName
            viewHolder.itemAmount.setText(DecimalFormat("#.##").format(pair.second))
        }

        val currencyInfo = CurrencyHelper.getCurrencyInfo(if (position == 0) currentCurrency else currencyContainer!!.getCurrencyNameAndRate(position).first)
        viewHolder.itemName.text = currencyInfo.first
        viewHolder.itemSymbol.text = currencyInfo.second
    }

    override fun onViewDetachedFromWindow(holder: CurrencyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.itemCode.text.toString() == currentCurrency) {
            holder.itemAmount.removeTextChangedListener(this)
        }
    }


    private var toChangeValues: Pair<CharSequence, CharSequence>? = null
    override fun changeCurrency(currencyCode: CharSequence, currencyAmount: CharSequence) {
        if (hasConnection) {
            if (currencyCode != currentCurrency) {
                currentCurrency = currencyCode.toString()
                val index = currencyContainer!!.moveCurrencyToTop(currencyCode.toString())
                notifyItemMoved(index, 0)
                notifyItemChanged(index)
                notifyItemChanged(0)
                currencyChangeCallback.onCurrencyChange(currentCurrency, currencyAmount.toString())
            }
        } else
            toChangeValues = currencyCode to currencyAmount
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        currencyChangeCallback.onAmountChange(text.toString())
    }

    inner class CurrencyViewHolder(itemView: View, private var currencyCallback: CurrencyCallback) :
            RecyclerView.ViewHolder(itemView), View.OnFocusChangeListener {

        val itemCode: TextView = itemView.textViewCurrencyCode
        val itemAmount: EditText = itemView.editTextAmount
        val itemName: TextView = itemView.textViewCurrencyName
        val itemSymbol: TextView = itemView.textViewCurrencySymbol


        init {
            itemAmount.onFocusChangeListener = this
        }

        override fun onFocusChange(p0: View?, hasFocus: Boolean) {
            if (hasFocus)
                currencyCallback.changeCurrency(itemCode.text, itemAmount.text)
        }
    }
}


interface CurrencyCallback {
    fun changeCurrency(currencyCode: CharSequence, currencyAmount: CharSequence)
}

interface CurrencyChangeCallback {
    fun onCurrencyChange(currencyCode: String, amount : String)
    fun onAmountChange(amount : String)
}
