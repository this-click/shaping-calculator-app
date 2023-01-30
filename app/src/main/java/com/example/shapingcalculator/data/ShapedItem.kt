package com.example.shapingcalculator.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shaped_item")
data class ShapedItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @ColumnInfo(name = "row_gauge")
    val rowGauge: String,
    @ColumnInfo(name = "shaping_length")
    val shapingLength: String,
    @ColumnInfo(name = "increases_total")
    val increasesTotal: String,
    @ColumnInfo(name = "increases_row")
    val increasesRow: String
)