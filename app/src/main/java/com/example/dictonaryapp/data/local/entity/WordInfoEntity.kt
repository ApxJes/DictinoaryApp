package com.example.dictonaryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictonaryapp.domain.model.Meaning
import com.example.dictonaryapp.domain.model.WordInfo

@Entity(tableName = "dictionary_table")
data class WordInfoEntity(
    val meanings: List<Meaning>,
    val origin: String?,
    val phonetic: String?,
    val word: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }
}
