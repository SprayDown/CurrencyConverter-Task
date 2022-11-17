package org.spray.cc.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import org.spray.cc.databinding.FragmentConverterBinding
import org.spray.cc.ui.main.MainActivity

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    private val viewModel: ConverterViewModel by lazy {
        ViewModelProvider(
            this,
            ConverterModelFactory()
        )[ConverterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        val mainActivity = requireActivity() as MainActivity
        if (mainActivity.currencyList.isNotEmpty())
            viewModel.setCurrency(mainActivity.currencyList.first { it.charCode == MainActivity.DEFAULT_INPUT_CURRENCY },
                mainActivity.currencyList.first { it.charCode == MainActivity.DEFAULT_OUTPUT_CURRENCY })

        init()
        return binding.root
    }

    private fun init() = with(binding) {
        textViewInputChar.text = viewModel.inputCurrency.charCode
        textViewOutputChar.text = viewModel.outputCurrency.charCode

            editTextInputValue.doOnTextChanged { text, _, _, _ ->
                if (editTextInputValue.hasFocus()) {
                    if (text != null && text.isNotEmpty())
                        editTextOutputValue.setText(
                            viewModel.calculateToOutput(text.toString().toFloat())
                        ) else
                        editTextOutputValue.text?.clear()
                }
            }

            editTextOutputValue.doOnTextChanged { text, _, _, _ ->
                if (editTextOutputValue.hasFocus()) {
                    if (text != null && text.isNotEmpty())
                        editTextInputValue.setText(
                            viewModel.calculateToInput(text.toString().toFloat())
                        ) else
                        editTextInputValue.text?.clear()
                }
            }

        buttonEditInput.setOnClickListener {
            it.findNavController().navigate(
                ConverterFragmentDirections.actionConverterFragmentToCurrencySelectionFragment(0)
            )
        }

        buttonEditOutput.setOnClickListener {
            it.findNavController().navigate(
                ConverterFragmentDirections.actionConverterFragmentToCurrencySelectionFragment(1)
            )
        }
    }
}