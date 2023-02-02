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
import java.math.RoundingMode
import kotlin.math.floor


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
    private var shapingValues: MutableList<String> = mutableListOf()

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

        // Displays data from DB, if any
        viewModel.getItem().observe(this.viewLifecycleOwner) { selectedItem ->
            if (selectedItem != null) {
                item = selectedItem
                //TODO: Implement refresh data after entering new values and tapping Calculate
                shapingValues = calculateShaping(
                    item.rowGauge.toDouble(),
                    item.shapingLength.toDouble(),
                    item.increasesTotal.toInt(),
                    item.increasesRow.toInt()
                )
                // TODO: display placeholder while data is loaded from DB
                binding.result1TextView.text = resources.getString(
                    R.string.shaping_rate,
                    shapingValues[2], shapingValues[1]
                )
                binding.result2TextView.text = resources.getString(
                    R.string.shaping_rate,
                    shapingValues[3], shapingValues[0]
                )
            }
        }

        // Adds new data in DB
        binding.calculateButton.setOnClickListener {
            if (isEntryValid()) {
                viewModel.addNewItem(
                    binding.gaugeEditText.text.toString(),
                    binding.lengthEditText.text.toString(),
                    binding.incsTotalEditText.text.toString(),
                    binding.incsPerRowEditText.text.toString()
                )

                getView()?.rootView?.windowToken
                // Close the keyboard after clicking Calculate, if data entered is valid
                closeSoftKeyboard()
            }
        }
    }

    // Checks all fields have values before inserting them in the database
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.gaugeEditText.text.toString(),
            binding.lengthEditText.text.toString(),
            binding.incsTotalEditText.text.toString(),
            binding.incsPerRowEditText.text.toString()
        )
    }

    // Close soft keyboard when clicking on Calculate. Called if entries are valid
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

    /* Determines the shaping rate, depending on:
        - row gauge
        - shaping length
        - total number of stitches to increase or decrease
        - number of stitches to increase or decrease per row
        Returns:
        - frequencyA, how many times to work shapingRateItrA
        - frequencyB, shapingRateItrB
        - shapingRateItrA, basic number of how many rows between increases or decreases
        - shapingRateItrB, remainder of how many rows between increases or decreases
        Since you can't work decimal stitches and rows, Ints are needed
    */
    private fun calculateShaping(
        rowGauge: Double,
        shapingLength: Double,
        totalIncs: Int,
        incsPerRow: Int
    ): MutableList<String> {
        val overRows = shapingLength * rowGauge
        var nbIncsRow = incsPerRow
        if (incsPerRow == 0) {
            nbIncsRow += 1
        }
        var shapingCalculated = totalIncs / nbIncsRow
        if (shapingCalculated == 0) {
            shapingCalculated += 1
        }
        val basicShapingRate = roundDownToTwoDecimals(overRows / shapingCalculated)
        val shapingRateItrA = floor(basicShapingRate)
        val shapingRateItrB = shapingRateItrA + 1
        val remainder = roundDownToTwoDecimals(overRows % shapingCalculated)
        val frequencyA = roundDownToTwoDecimals(shapingCalculated - remainder)
        val frequencyB = remainder

        shapingValues.addAll(
            arrayListOf(
                frequencyA.toInt().toString(),
                frequencyB.toInt().toString(),
                shapingRateItrA.toInt().toString(),
                shapingRateItrB.toInt().toString()
            )
        )

        return shapingValues
    }

    // It is recommended to use 2 decimals for better shaping accuracy
    private fun roundDownToTwoDecimals(amount: Double): Double {
        // Need BigDec to format with 2 decimals
        val bigDecimal = amount.toBigDecimal().setScale(2, RoundingMode.FLOOR)
        return bigDecimal.toDouble()
    }
}