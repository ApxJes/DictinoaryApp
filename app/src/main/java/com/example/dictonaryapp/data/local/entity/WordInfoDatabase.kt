package com.example.dictonaryapp.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (
    entities = [WordInfoEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {

    abstract fun wordInfoDao(): WordInfoDao
}