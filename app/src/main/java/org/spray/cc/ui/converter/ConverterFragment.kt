package org.spray.cc.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import org.spray.cc.databinding.FragmentConverterBinding
import org.spray.cc.ui.main.MainActivity

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    private val currencyList by lazy {
        (requireActivity() as MainActivity).currencyList
    }

    private val viewModel: ConverterViewModel by lazy {
        ViewModelProvider(
            this,
            ConverterModelFactory()
        )[ConverterViewModel::class.java]
    }

    private var reversed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Initialize default currency
         */
        if (currencyList.isNotEmpty())
            viewModel.setCurrency(currencyList.first { it.charCode == MainActivity.DEFAULT_INPUT_CURRENCY },
                currencyList.first { it.charCode == MainActivity.DEFAULT_OUTPUT_CURRENCY })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Получаем результат при выборе валюты из SelectionFragment
         */
        setFragmentResultListener(RESULT_FROM_SELECTION) { _, bundle ->
            val currencyIndex = bundle.getInt("currencyIndex")
            val currency = currencyList.first { it.id == bundle.getString("id") }
            if (currencyIndex == 0) {
                viewModel.inputCurrency = currency
            } else {
                viewModel.outputCurrency = currency
            }

            updateOutput()
            update()
        }
    }

    private fun update() = with(binding) {
        textViewInputChar.text = viewModel.inputCurrency.charCode
        textViewOutputChar.text = viewModel.outputCurrency.charCode
    }

    private fun init() = with(binding) {
        update()

        editTextInputValue.doOnTextChanged { text, _, _, _ ->
            if (editTextInputValue.hasFocus()) {
                if (text != null && text.isNotEmpty())
                    editTextOutputValue.setText(
                        viewModel.convertToOutput(text.toString().toFloat())
                    ) else
                    editTextOutputValue.text?.clear()
            }
        }

        editTextOutputValue.doOnTextChanged { text, _, _, _ ->
            if (editTextOutputValue.hasFocus()) {
                if (text != null && text.isNotEmpty())
                    editTextInputValue.setText(
                        viewModel.convertToInput(text.toString().toFloat())
                    ) else
                    editTextInputValue.text?.clear()
            }
        }

        buttonEditInput.setOnClickListener {
            it.findNavController().navigate(
                ConverterFragmentDirections.actionConverterFragmentToCurrencySelectionFragment(
                    viewModel.inputCurrency.id,
                    0
                )
            )
        }

        buttonEditOutput.setOnClickListener {
            it.findNavController().navigate(
                ConverterFragmentDirections.actionConverterFragmentToCurrencySelectionFragment(
                    viewModel.outputCurrency.id,
                    1
                )
            )
        }

        buttonReverse.setOnClickListener {
            reversed = !reversed
            buttonReverse.animate().rotation(if (reversed) 225f else 45f)
            viewModel.reverse()

            update()
            updateOutput()
        }
    }

    private fun updateOutput() {
        if (binding.editTextInputValue.text != null && binding.editTextInputValue.text!!.isNotEmpty())
            binding.editTextOutputValue.setText(
                viewModel.convertToOutput(
                    binding.editTextInputValue.text.toString().toFloat()
                )
            )
    }

    companion object {
        const val RESULT_FROM_SELECTION = "RESULT_FROM_SELECTION"
    }

}