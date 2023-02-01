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