package com.example.dictonaryapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictonaryapp.core.util.SimpleResource
import com.example.dictonaryapp.domain.use_case.GetWordInfoUseCase
import com.example.dictonaryapp.presentation.GetWordInfoState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetWordInfoViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase
): ViewModel() {

    private val _state: MutableStateFlow<GetWordInfoState> = MutableStateFlow(GetWordInfoState())
    val state: StateFlow<GetWordInfoState> = _state

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _eventFlow: MutableSharedFlow<UiEvent> = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    private var job: Job? = null

    fun searchQuery(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500L)
            getWordInfoUseCase(query)
                .onEach { result ->
                    when(result) {
                        is SimpleResource.Success -> {
                            _state.value = _state.value.copy(
                                wordInfoItem = result.data ?: emptyList(),
                                isLoading = false
                            )

                            Log.d("ViewModel", "State updated: $_state")
                        }

                        is SimpleResource.Error -> {
                            _state.value = _state.value.copy(
                                wordInfoItem = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(UiEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            ))
                        }

                        is SimpleResource.Loading -> {
                            _state.value = _state.value.copy(
                                wordInfoItem = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
    }
}