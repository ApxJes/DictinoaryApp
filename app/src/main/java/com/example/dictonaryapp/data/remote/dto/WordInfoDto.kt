package com.example.dictonaryapp.data.remote.dto

import com.example.dictonaryapp.data.local.entity.WordInfoEntity

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String?,
    val phonetic: String?,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            originIs = origin,
            phonetic = phonetic,
            word = word
        )
    }
}