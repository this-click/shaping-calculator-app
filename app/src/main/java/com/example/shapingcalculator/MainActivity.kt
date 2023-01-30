package com.example.shapingcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
            if (isEntryValid()) {
                viewModel.addNewItem(
                    binding.gaugeEditText.toString(),
                    binding.lengthEditText.text.toString(),
                    binding.incsPerRowEditText.text.toString(),
                    binding.incsEditText.text.toString()
                )
            }
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.gaugeEditText.toString(),
            binding.lengthEditText.text.toString(),
            binding.incsPerRowEditText.text.toString(),
            binding.incsEditText.text.toString()
        )
    }
}
