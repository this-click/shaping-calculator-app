package com.example.shapingcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shapingcalculator.databinding.FragmentCalculateShapingBinding

class CalculateShapingFragment : Fragment() {
    private var _binding: FragmentCalculateShapingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculateShapingBinding.inflate(inflater, container, false)
        return binding.root
    }
}

//    private val viewModel: KnittingViewModel by viewModels {
//        KnittingViewModelFactory(
//            (application as KnittingApplication).database
//                .shapedItemDao()
//        )
//    }

//TODO: display last saved values in Gauge etc if there's anything in the DB
//TODO: display a toast after adding new items

//        binding.calculateButton.setOnClickListener {
//            val view: View? = this.currentFocus
//            closeSoftKeyboard(view)
//
//            if (isEntryValid()) {
//                viewModel.addNewItem(
//                    binding.gaugeEditText.text.toString(),
//                    binding.lengthEditText.text.toString(),
//                    binding.incsTotalEditText.text.toString(),
//                    binding.incsPerRowEditText.text.toString()
//                )
//            }
//        }


//    private fun isEntryValid(): Boolean {
//        return viewModel.isEntryValid(
//            binding.gaugeEditText.text.toString(),
//            binding.lengthEditText.text.toString(),
//            binding.incsTotalEditText.text.toString(),
//            binding.incsPerRowEditText.text.toString()
//        )
//    }

//    private fun closeSoftKeyboard(view:View?) {
//        if (view != null) {
//            val imm: InputMethodManager =
//                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//    }