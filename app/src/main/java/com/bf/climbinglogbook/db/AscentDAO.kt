package com.bf.climbinglogbook.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bf.climbinglogbook.other.Constants.ROUTES_TABLE_NAME

@Dao
interface AscentDAO {
    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAscent(ascent: Ascent)
//
//    @Delete
//    fun deleteAscent(ascent: Ascent)

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY name ASC")
    fun getAllRoutesSortedByNameAsc(): LiveData<List<Ascent>>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY date DESC LIMIT :num")
    fun getLastAscents(num: Int): LiveData<List<Ascent>>

    @Query("SELECT count(id) FROM $ROUTES_TABLE_NAME")
    fun numberOfItemsInDB(): LiveData<Int>

    @Query("SELECT * FROM $ROUTES_TABLE_NAME ORDER BY date DESC ")
    fun getAllAscents(): LiveData<List<Ascent>>
}