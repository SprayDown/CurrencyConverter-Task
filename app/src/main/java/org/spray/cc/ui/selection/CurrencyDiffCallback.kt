package org.spray.cc.ui.selection

import androidx.recyclerview.widget.DiffUtil
import org.spray.cc.model.Currency

class CurrencyDiffCallback(
    private val oldList: List<Currency>,
    private val newList: List<Currency>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].charCode == newList[newItemPosition].charCode
    }

    override fun getChangePayload(oldItem: Int, newItem: Int): Any? {
        if (oldList[oldItem] != newList[newItem]) {
            return newList[newItem]
        }
        return null
    }
}