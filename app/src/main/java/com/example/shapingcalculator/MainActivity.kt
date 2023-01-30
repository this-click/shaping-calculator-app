package com.example.shapingcalculator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shapingcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: KnittingViewModel by viewModels {
        KnittingViewModelFactory(
            (application as KnittingApplication).database
                .shapedItemDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            val view: View? = this.currentFocus
            closeSoftKeyboard(view)

            if (isEntryValid()) {
                viewModel.addNewItem(
                    binding.gaugeEditText.text.toString(),
                    binding.lengthEditText.text.toString(),
                    binding.incsPerRowEditText.text.toString(),
                    binding.incsEditText.text.toString()
                )
            }
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.gaugeEditText.text.toString(),
            binding.lengthEditText.text.toString(),
            binding.incsPerRowEditText.text.toString(),
            binding.incsEditText.text.toString()
        )
    }

    private fun closeSoftKeyboard(view:View?) {
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
