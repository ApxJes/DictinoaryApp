package com.example.dictonaryapp.data.repository

import com.example.dictonaryapp.core.util.SimpleResource
import com.example.dictonaryapp.data.local.entity.WordInfoDao
import com.example.dictonaryapp.data.remote.DictionaryApi
import com.example.dictonaryapp.domain.model.WordInfo
import com.example.dictonaryapp.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
): WordInfoRepository {
    override fun getWordInfo(word: String): Flow<SimpleResource<List<WordInfo>>> {

    }
}