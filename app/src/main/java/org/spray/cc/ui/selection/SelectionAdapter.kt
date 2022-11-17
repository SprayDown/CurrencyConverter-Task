package org.spray.cc.ui.selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.spray.cc.R
import org.spray.cc.databinding.ItemCurrencyBinding
import org.spray.cc.model.Currency

class SelectionAdapter(
    private val dataSet: ArrayList<Currency> = ArrayList(),
    private var selectedId: String,
    private val listener: OnSelectedListener
) :
    RecyclerView.Adapter<SelectionAdapter.CurrencyHolder>() {

    override fun getItemCount(): Int = dataSet.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectionAdapter.CurrencyHolder {
        return CurrencyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SelectionAdapter.CurrencyHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            listener.onSelect(dataSet[position].id)
        }
        holder.bind(dataSet[position])
    }

    fun setDataSet(data: List<Currency>) {
        val diffResult = DiffUtil.calculateDiff(
            CurrencyDiffCallback(dataSet, data), true
        )
        dataSet.clear()
        dataSet.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CurrencyHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemCurrencyBinding.bind(item)

        fun bind(currency: Currency) = with(binding) {
            textViewName.text = currency.name
            textViewCharCode.text = currency.charCode

            imageViewCheck.visibility =
                if (currency.id == selectedId) View.VISIBLE else View.INVISIBLE
        }
    }

}