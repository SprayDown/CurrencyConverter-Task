package org.spray.cc.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConverterModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            ConverterViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}