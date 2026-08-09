package com.ezhan1me.app.ui.navigation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezhan1me.app.R
import com.ezhan1me.app.getHanimeSearchShareText
import com.ezhan1me.app.getHanimeShareText
import com.ezhan1me.app.ui.screen.home.SubscriptionScreen
import com.ezhan1me.app.ui.viewmodel.MySubscriptionsViewModel
import com.ezhan1me.libs.utils.copyTextToClipboard
import com.ezhan1me.libs.utils.showShortToast

@Composable
fun SubscriptionRouteScreen(
    onBack: () -> Unit,
    onNavigateToSearch: (String?) -> Unit,
    onNavigateToVideo: (String) -> Unit,
) {
    val viewModel: MySubscriptionsViewModel = viewModel()
    SubscriptionScreen(
        navigateBack = onBack,
        viewModel = viewModel,
        onClickArtist = { onNavigateToSearch(it) },
        onLongClickArtist = { artistName ->
            copyTextToClipboard(getHanimeSearchShareText(artistName))
            showShortToast(R.string.copy_to_clipboard)
        },
        onClickVideosItem = onNavigateToVideo,
        onLongClickVideosItem = { videoCode, title ->
            copyTextToClipboard(getHanimeShareText(title, videoCode))
            showShortToast(R.string.copy_to_clipboard)
        },
    )
}
