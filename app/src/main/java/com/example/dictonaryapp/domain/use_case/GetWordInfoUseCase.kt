package com.example.dictonaryapp.domain.use_case

import com.example.dictonaryapp.core.util.SimpleResource
import com.example.dictonaryapp.domain.model.WordInfo
import com.example.dictonaryapp.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordInfoUseCase @Inject constructor(
    private val repository: WordInfoRepository
) {

    operator fun invoke(word: String): Flow<SimpleResource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow { }
        }

        return repository.getWordInfo(word)
    }
}