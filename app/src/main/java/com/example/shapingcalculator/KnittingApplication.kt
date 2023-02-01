package com.example.shapingcalculator

import android.app.Application
import com.example.shapingcalculator.data.ShapedItemRoomDatabase

class KnittingApplication: Application() {
    val database: ShapedItemRoomDatabase by lazy { ShapedItemRoomDatabase.getDatabase(this) }
}