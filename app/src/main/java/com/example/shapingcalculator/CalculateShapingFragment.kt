package com.example.shapingcalculator

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shapingcalculator.data.ShapedItem
import com.example.shapingcalculator.databinding.FragmentCalculateShapingBinding


class CalculateShapingFragment : Fragment() {
    private var _binding: FragmentCalculateShapingBinding? = null
    private val binding get() = _binding!!
    lateinit var item: ShapedItem
    private val viewModel: KnittingViewModel by viewModels {
        KnittingViewModelFactory(
            (activity?.application as KnittingApplication).database
                .shapedItemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculateShapingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calculateButton.setOnClickListener {
            if (isEntryValid()) {
                viewModel.addNewItem(
                    binding.gaugeEditText.text.toString(),
                    binding.lengthEditText.text.toString(),
                    binding.incsTotalEditText.text.toString(),
                    binding.incsPerRowEditText.text.toString()
                )

                getView()?.rootView?.windowToken
                closeSoftKeyboard()
            }
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.gaugeEditText.text.toString(),
            binding.lengthEditText.text.toString(),
            binding.incsTotalEditText.text.toString(),
            binding.incsPerRowEditText.text.toString()
        )
    }

    private fun closeSoftKeyboard() {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = requireActivity().currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}