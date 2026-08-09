package com.ezhan1me.app.ui.navigation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezhan1me.app.R
import com.ezhan1me.app.getHanimeShareText
import com.ezhan1me.app.ui.screen.home.myplaylist.PlaylistScreen
import com.ezhan1me.app.ui.viewmodel.MyPlayListViewModelV2
import com.ezhan1me.libs.utils.copyTextToClipboard
import com.ezhan1me.libs.utils.showShortToast

@Composable
fun MyPlaylistRouteScreen(
    onBack: () -> Unit,
    onNavigateToVideo: (String) -> Unit,
) {
    val viewModel: MyPlayListViewModelV2 = viewModel()
    PlaylistScreen(
        viewModel = viewModel,
        navigateBack = onBack,
        onClickItem = onNavigateToVideo,
        onLongClickItem = { videoCode, title ->
            copyTextToClipboard(getHanimeShareText(title, videoCode))
            showShortToast(R.string.copy_to_clipboard)
        },
    )
}
