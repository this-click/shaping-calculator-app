package com.example.shapingcalculator.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShapedItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertThis(item: ShapedItem)

//    @Query("REPLACE INTO shaped_item (row_gauge, shaping_length, increases_total, increases_row) " +
//            "VALUES (:row_gauge, :shaping_length, :increases_total, :increases_row) ")
//    fun insertOrReplace(item: ShapedItem)
//
//    @Update
//    suspend fun update(item: ShapedItem)
//
//    @Delete
//    suspend fun delete(item: ShapedItem)
//
//    @Query("SELECT * FROM item where id = :id")
//    fun findIt(id: Int): Flow<ShapedItem>
//
//    @Query("SELECT * from item ORDER BY name ASC")
//    fun getAll(): Flow<List<ShapedItem>>

}