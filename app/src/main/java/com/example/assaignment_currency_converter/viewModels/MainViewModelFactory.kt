package com.example.assaignment_currency_converter.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository

class MainViewModelFactory(private val repository: OpenExchangeRepository, private val database: CurrencyDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository,database) as T
    }
}