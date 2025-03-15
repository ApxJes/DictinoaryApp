package com.example.dictonaryapp.data.local.entity

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec

@Database (
    entities = [WordInfoEntity::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 3, to = 4, spec = WordInfoDatabase.Migration3To4::class)
    ]
)

@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {

    abstract fun wordInfoDao(): WordInfoDao

    @RenameColumn(tableName = "dictionary_table", fromColumnName = "origin", toColumnName = "originIs")
    class Migration3To4: AutoMigrationSpec
}