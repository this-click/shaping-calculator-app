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

        viewModel.getItem().observe(this.viewLifecycleOwner) { selectedItem ->
            if (selectedItem != null) {
                item = selectedItem
                //FIXME: Cards with new values after entering new values and tapping Calculate
                shapingValues = calculateShaping(
                    item.rowGauge.toDouble(),
                    item.shapingLength.toDouble(),
                    item.increasesTotal.toInt(),
                    item.increasesRow.toInt()
                )
                // TODO: display placeholder while data is loaded from DB. Sometimes I can see $s
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

    private fun calculateShaping(
        rowGauge: Double,
        shapingLength: Double,
        totalIncs: Int,
        incsPerRow: Int
    ): MutableList<String> {
        /* See README for formula explanation */

        val overRows = shapingLength * rowGauge
        var nbIncsRow = incsPerRow
        if (incsPerRow == 0) {
            nbIncsRow += 1
        }
        // TODO: Formula needs more work
        var shapingCalculated = totalIncs / nbIncsRow
        if (shapingCalculated == 0) {
            shapingCalculated += 1
        }
        val basicShapingRate = roundDownToTwoDecimals(overRows / shapingCalculated)
        val shapingRateItrA = floor(basicShapingRate)
        val shapingRateItrB = shapingRateItrA + 1
        val remainder = roundDownToTwoDecimals(overRows % shapingCalculated)
        val rateFactorA = roundDownToTwoDecimals(shapingCalculated - remainder)
        val rateFactorB = remainder

        shapingValues.addAll(
            arrayListOf(
                rateFactorA.toString(),
                rateFactorB.toString(),
                shapingRateItrA.toString(),
                shapingRateItrB.toString()
            )
        )

        return shapingValues
    }

    private fun roundDownToTwoDecimals(amount: Double): Double {
        // Need BigDec to format with 2 decimals
        val bigDecimal = amount.toBigDecimal().setScale(2, RoundingMode.FLOOR)
        return bigDecimal.toDouble()
    }
}