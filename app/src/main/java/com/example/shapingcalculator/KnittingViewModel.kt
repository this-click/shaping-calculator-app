package com.example.shapingcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shapingcalculator.data.ShapedItem
import com.example.shapingcalculator.data.ShapedItemDao
import kotlinx.coroutines.launch

class KnittingViewModel(private val itemDao: ShapedItemDao) : ViewModel() {
//    private fun replaceItem(item: ShapedItem) {
//        viewModelScope.launch {
//            itemDao.insertOrReplace(item)
//        }
//    }
private fun insertItem(item: ShapedItem) {
    viewModelScope.launch {
        itemDao.insertThis(item)
    }
}

    private fun getNewItemEntry(
        newRowGauge: String,
        newLength: String,
        newIncreasesTotal: String,
        newIncreasesRow: String
    ): ShapedItem {
        return ShapedItem(
            rowGauge = newRowGauge,
            shapingLength = newLength,
            increasesTotal = newIncreasesTotal,
            increasesRow = newIncreasesRow
        )
    }

    fun addNewItem(rowGauge: String, shapingLength: String, increasesTotal: String, increasesRow: String) {
        val newItem = getNewItemEntry(rowGauge, shapingLength, increasesTotal, increasesRow)
        insertItem(newItem)
    }

    fun isEntryValid(
        rowGauge: String,
        length: String,
        increasesTotal: String,
        increasesRow: String
    ): Boolean {
        if (rowGauge.isBlank() || length.isBlank() || increasesTotal.isBlank() || increasesRow.isBlank()) {
            return false
        }
        return true
    }
}

class KnittingViewModelFactory(private val shapedItemDao: ShapedItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KnittingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return KnittingViewModel(shapedItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}