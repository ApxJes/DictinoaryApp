package com.example.dictonaryapp.data.local.entity

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dictonaryapp.data.util.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private val Migration_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP TABLE IF EXISTS 'dictionary_table'")

            db.execSQL(
                "CREATE TABLE 'dictionary_table' (" +
                        "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "'meanings' TEXT NOT NULL, " +
                        "'origin' TEXT, " +
                        "'phonetic' TEXT, " +
                        "'word' TEXT NOT NULL)"
            )
        }
    }

    @Singleton
    @Provides
    fun providesRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        WordInfoDatabase::class.java,
        "wordInfo_db"
    ).addMigrations( Migration_2_3).build()

    @Singleton
    @Provides
    fun providesWordInfoDao(db: WordInfoDatabase) = db.wordInfoDao()
}