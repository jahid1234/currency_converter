package com.example.assaignment_currency_converter.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository

class MainViewModelFactory(private val repository: OpenExchangeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}