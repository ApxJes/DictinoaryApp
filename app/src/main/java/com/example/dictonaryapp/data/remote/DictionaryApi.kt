package com.example.dictonaryapp.data.remote

import com.example.dictonaryapp.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("/api/v2/entries/en/<word>")
    suspend fun getWordInfo(
        @Path ("word") word: String
    ): List<WordInfoDto>
}