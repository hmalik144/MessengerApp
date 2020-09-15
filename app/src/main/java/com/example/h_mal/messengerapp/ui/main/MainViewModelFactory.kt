package com.example.h_mal.messengerapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.messengerapp.data.repository.RepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * ViewModel provider factory to instantiate MainViewModel.
 */
class MainViewModelFactory(
    private val repositoryImpl: RepositoryImpl
) : ViewModelProvider.Factory {

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return (
                    MainViewModel(repositoryImpl)
                    ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}