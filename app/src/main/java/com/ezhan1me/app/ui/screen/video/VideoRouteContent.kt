package com.ezhan1me.app.ui.screen.video

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.preference.PreferenceManager
import com.ezhan1me.app.R
import com.ezhan1me.app.logic.model.HanimeInfo
import com.ezhan1me.app.logic.state.VideoLoadingState
import com.ezhan1me.app.ui.bridge.VideoPageHost
import com.ezhan1me.app.ui.viewmodel.CommentViewModel
import com.ezhan1me.app.ui.viewmodel.VideoViewModel
import com.ezhan1me.libs.utils.application

@Composable
fun VideoRouteContent(
    videoCode: String,
    videoState: VideoLoadingState<*>,
    videoViewModel: VideoViewModel,
    commentViewModel: CommentViewModel,
    fromDownload: Boolean,
    pendingDownloadPrompt: DownloadPromptState?,
    onPendingDownloadPromptChange: (DownloadPromptState?) -> Unit,
    onRetry: () -> Unit,
    onOpenVideo: (HanimeInfo) -> Unit,
    onOpenArtist: (com.ezhan1me.app.logic.model.HanimeVideo.Artist) -> Unit,
    onNavigateToSearch: (String) -> Unit,
    onToggleSubscribe: (com.ezhan1me.app.logic.model.HanimeVideo.Artist) -> Unit,
    onToggleFavorite: (com.ezhan1me.app.logic.model.HanimeVideo) -> Unit,
    onRateVideo: (com.ezhan1me.app.logic.model.HanimeVideo, Boolean) -> Unit,
    onManageMyList: (com.ezhan1me.app.logic.model.HanimeVideo.MyList?, List<Boolean>) -> Unit,
    onQuickCheckIn: (com.ezhan1me.app.logic.entity.CheckInRecordEntity) -> Unit,
    onPrepareDownload: (String, com.ezhan1me.app.logic.model.HanimeVideo?) -> Unit,
    onConfirmDownloadPrompt: (com.ezhan1me.app.logic.model.HanimeVideo?) -> Unit,
    onRequestOpenOfficialDownloadPage: () -> Unit,
    onRequestOpenDownloadPermissionSettings: () -> Unit,
    onOpenWebPage: () -> Unit,
    onOpenOriginalComic: (String) -> Unit,
    onOpenShare: (String, String) -> Unit,
    onCopyText: (String) -> Unit,
    onIntroductionLinkClick: (String) -> Unit,
    stringLongPressShare: String,
    pageHost: VideoPageHost,
) {
    val hostUiState by videoViewModel.videoHostUiStateFlow.collectAsStateWithLifecycle()
    val disableComments = remember {
        PreferenceManager.getDefaultSharedPreferences(application)
            .getBoolean("disable_comments", false)
    }
    val tabs = remember(disableComments, hostUiState.commentBadgeCount, fromDownload) {
        buildList {
            add(VideoTabItem(R.string.introduction))
            if (!fromDownload && !disableComments) {
                add(VideoTabItem(R.string.comment, badgeCount = hostUiState.commentBadgeCount))
            }
        }
    }

    VideoScreen(
        state = videoState,
        onRetry = onRetry,
    ) {
        VideoTabsContent(
            tabs = tabs,
            selectedTabIndex = hostUiState.selectedTabIndex,
            onSelectedTabChange = { videoViewModel.setSelectedTabIndex(videoCode, it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = with(LocalDensity.current) { hostUiState.appBarBottomInsetPx.toDp() }),
        ) { page ->
            if (page == 0) {
                RenderVideoIntroductionContent(
                    videoCode = videoCode,
                    viewModel = videoViewModel,
                    pendingDownloadPrompt = pendingDownloadPrompt,
                    onPendingDownloadPromptChange = onPendingDownloadPromptChange,
                    onOpenVideo = onOpenVideo,
                    onOpenArtist = onOpenArtist,
                    onNavigateToSearch = onNavigateToSearch,
                    onToggleSubscribe = onToggleSubscribe,
                    onToggleFavorite = onToggleFavorite,
                    onRateVideo = onRateVideo,
                    onManageMyList = onManageMyList,
                    onQuickCheckIn = onQuickCheckIn,
                    onPrepareDownload = onPrepareDownload,
                    onConfirmDownloadPrompt = onConfirmDownloadPrompt,
                    onRequestOpenOfficialDownloadPage = onRequestOpenOfficialDownloadPage,
                    onRequestOpenDownloadPermissionSettings = onRequestOpenDownloadPermissionSettings,
                    onOpenWebPage = onOpenWebPage,
                    onOpenOriginalComic = onOpenOriginalComic,
                    onOpenShare = onOpenShare,
                    onCopyText = onCopyText,
                    onIntroductionLinkClick = onIntroductionLinkClick,
                    stringLongPressShare = stringLongPressShare,
                )
            } else {
                RenderVideoCommentContent(
                    viewModel = commentViewModel,
                    reportMessages = remember { kotlinx.coroutines.flow.MutableSharedFlow() },
                    getMessageText = { message ->
                        if (message.args.isNotEmpty()) {
                            application.getString(message.resId, *message.args.toTypedArray())
                        } else {
                            application.getString(message.resId)
                        }
                    },
                    pageHost = pageHost,
                )
            }
        }
    }
}
