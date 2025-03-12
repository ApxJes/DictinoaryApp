package com.example.dictonaryapp.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfo(info: List<WordInfoEntity>)

    @Query("DELETE FROM dictionary_table WHERE word IN(:words)")
    suspend fun deleteWordInfo(words: List<String>)

    @Query("SELECT * FROM dictionary_table WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfo(word: String): List<WordInfoEntity>

}