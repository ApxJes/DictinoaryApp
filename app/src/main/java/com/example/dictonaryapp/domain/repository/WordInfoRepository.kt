package com.example.dictonaryapp.domain.repository

import com.example.dictonaryapp.core.util.SimpleResource
import com.example.dictonaryapp.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<SimpleResource<List<WordInfo>>>
}