package com.bf.climbinglogbook.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [Ascent::class],
    version = 5
)

@TypeConverters(Converters::class)
abstract class LogbookDatabase : RoomDatabase() {

    abstract fun getAscentDao(): AscentDAO

}