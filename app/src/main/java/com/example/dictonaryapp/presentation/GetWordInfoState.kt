package com.example.dictonaryapp.presentation

import com.example.dictonaryapp.domain.model.WordInfo

data class GetWordInfoState(
    val wordIntoItem: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
