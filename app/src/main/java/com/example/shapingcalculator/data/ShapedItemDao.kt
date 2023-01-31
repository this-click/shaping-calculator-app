package com.example.shapingcalculator.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShapedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShapedItem)

//    @Update
//    suspend fun update(item: ShapedItem)
//
//    @Delete
//    suspend fun delete(item: ShapedItem)
//
    @Query("SELECT * FROM shaped_item")
    fun getItemById(): Flow<ShapedItem>
//
//    @Query("SELECT * from item ORDER BY name ASC")
//    fun getAll(): Flow<List<ShapedItem>>

}