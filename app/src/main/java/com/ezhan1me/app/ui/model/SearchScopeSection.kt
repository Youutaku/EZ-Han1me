package com.ezhan1me.app.ui.model

import com.ezhan1me.app.logic.model.SearchOption

data class SearchScopeSection(
    val titleRes: Int,
    val options: List<SearchOption>,
    val spanCount: Int = 3,
)