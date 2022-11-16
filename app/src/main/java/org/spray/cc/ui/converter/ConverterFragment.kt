package org.spray.cc.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.spray.cc.databinding.FragmentConverterBinding

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(inflater, container, false)

        with(binding) {
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

        return binding.root
    }
}