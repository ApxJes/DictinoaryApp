package com.example.dictonaryapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.dictonaryapp.databinding.ActivityMainBinding
import com.example.dictonaryapp.presentation.viewModel.GetWordInfoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GetWordInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.edtSearchWord.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                query: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.searchQuery(query.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        observeViewModel()
    }

    private fun observeViewModel() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.state.collect { state ->
                binding.loadingProgressBar.visibility = if(state.isLoading) View.VISIBLE else View.GONE

                val resultText = StringBuilder()

                if(state.wordInfoItem.isNotEmpty()) {
                    val wordInfo = state.wordInfoItem[0]

                    resultText.append(wordInfo.word).append("\n")

                    if(wordInfo.meanings.isNotEmpty()) {
                        for(meaning in wordInfo.meanings) {
                            resultText.append("   --  Part of speech: ${meaning.partOfSpeech}\n")

                            if(meaning.definitions.isNotEmpty()) {
                                for(definition in meaning.definitions) {
                                    resultText.append("   --  Definition: ${definition.definition}\n")
                                    if(!definition.example.isNullOrBlank()) {
                                        resultText.append("  --  Example: ${definition.example}\n")
                                    }
                                }
                            }
                        }
                    }
                }

                binding.txvWordInfo.text = resultText.toString()
                Log.d("MainActivity", "State recieved: ${state.wordInfoItem}")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.eventFlow.collect { event ->
                when(event) {
                    is GetWordInfoViewModel.UiEvent.ShowSnackBar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}