package com.example.dictonaryapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.dictonaryapp.domain.use_case.GetWordInfoUseCase
import com.example.dictonaryapp.presentation.GetWordInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class GetWordInfoViewModel(
    private val getWordInfoUseCase: GetWordInfoUseCase
): ViewModel() {

    private val _state: MutableStateFlow<GetWordInfoState> = MutableStateFlow(GetWordInfoState())
    val state: StateFlow<GetWordInfoState> = _state

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _eventFlow: MutableSharedFlow<UiEvent> = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow


    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
    }
}