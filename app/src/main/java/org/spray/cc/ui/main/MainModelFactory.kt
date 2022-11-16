package org.spray.cc.ui.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectionModelFactory(private val url: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SelectionViewModel::class.java)) {
            SelectionViewModel(url) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}