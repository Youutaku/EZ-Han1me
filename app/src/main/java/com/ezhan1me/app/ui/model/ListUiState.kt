package com.ezhan1me.app.ui.model

data class ListUiState<T>(
    val loading: Boolean = false,
    val items: List<T> = emptyList(),
    val errorMessage: String? = null,
)
