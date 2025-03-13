package com.example.dictonaryapp.data.repository

import android.util.Log
import com.example.dictonaryapp.core.util.SimpleResource
import com.example.dictonaryapp.data.local.entity.WordInfoDao
import com.example.dictonaryapp.data.remote.DictionaryApi
import com.example.dictonaryapp.domain.model.WordInfo
import com.example.dictonaryapp.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
): WordInfoRepository {
    override fun getWordInfo(word: String): Flow<SimpleResource<List<WordInfo>>> = flow {
        emit(SimpleResource.Loading())

        val wordInfo = dao.getWordInfo(word).map { it.toWordInfo() }
        emit(SimpleResource.Loading(wordInfo))

        try {
            val remoteWordInfo = api.getWordInfo(word)
            dao.deleteWordInfo(remoteWordInfo.map { it.word })
            dao.insertWordInfo(remoteWordInfo.map { it.toWordInfoEntity() })

            Log.d("WordInfoRepo", "Received API data: $remoteWordInfo")

        } catch (e: HttpException) {
            emit(
                SimpleResource.Error(
                    message = "Oop! Something went wrong",
                    data = wordInfo
                )
            )
            return@flow

        } catch (e: IOException) {
            emit(
                SimpleResource.Error(
                    message = "Couldn't reach sever! Please check your internet connection",
                    data = wordInfo
                )
            )
            return@flow
        }

        val newWordInfo = dao.getWordInfo(word).map { it.toWordInfo() }
        emit(SimpleResource.Success(newWordInfo))
        
        Log.d("WordInfoRepo", "Stored in DB: $newWordInfo")

    }
}