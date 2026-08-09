package com.ezhan1me.app.ui.navigation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezhan1me.app.R
import com.ezhan1me.app.getHanimeShareText
import com.ezhan1me.app.ui.screen.search.AdvancedSearchSheet
import com.ezhan1me.app.ui.screen.search.SearchScreen
import com.ezhan1me.app.ui.viewmodel.SearchViewModel
import com.ezhan1me.libs.utils.copyTextToClipboard
import com.ezhan1me.libs.utils.showShortToast
import kotlinx.serialization.json.Json

@Composable
fun SearchRouteScreen(
    route: SearchRoute,
    onBack: () -> Unit,
    onNavigateToVideo: (String) -> Unit,
) {
    val viewModel: SearchViewModel = viewModel()
    var showAdvancedSearchSheet by remember { mutableStateOf(false) }

    LaunchedEffect(route.advancedSearchJson) {
        route.advancedSearchJson?.let { json ->
            runCatching { Json.decodeFromString<Map<String, String>>(json) }
                .onSuccess { params ->
                    params.forEach { (key, value) ->
                        when (key.uppercase()) {
                            "QUERY" -> viewModel.query = value
                            "GENRE" -> viewModel.genre = value
                            "SORT" -> viewModel.sort = value
                            "YEAR" -> viewModel.year = value.toIntOrNull()
                            "MONTH" -> viewModel.month = value.toIntOrNull()
                            "DURATION" -> viewModel.duration = value
                        }
                    }
                }
        }
    }

    if (showAdvancedSearchSheet) {
        AdvancedSearchSheet(
            viewModel = viewModel,
            onDismiss = { showAdvancedSearchSheet = false },
        )
    }

    SearchScreen(
        viewModel = viewModel,
        initialQuery = route.query,
        onBack = onBack,
        onOpenVideo = onNavigateToVideo,
        onLongPressCopy = { videoCode, title ->
            copyTextToClipboard(getHanimeShareText(title, videoCode))
            showShortToast(R.string.copy_to_clipboard)
        },
        onOpenAdvancedSearch = { showAdvancedSearchSheet = true },
    )
}
