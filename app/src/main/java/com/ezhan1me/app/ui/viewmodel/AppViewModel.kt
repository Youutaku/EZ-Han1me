package com.ezhan1me.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.ezhan1me.app.FirebaseConstants
import com.ezhan1me.app.Preferences
import com.ezhan1me.app.logic.NetworkRepo
import com.ezhan1me.app.logic.model.github.Latest
import com.ezhan1me.app.logic.state.WebsiteState
import com.ezhan1me.app.worker.HUpdateWorker
import com.ezhan1me.app.worker.HanimeDownloadManagerV2
import com.ezhan1me.app.worker.HanimeDownloadWorker
import com.ezhan1me.libs.base.YenalyViewModel
import com.ezhan1me.libs.utils.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @project EZ Han1me
 * @author Yenaly Liew
 * @time 2024/03/29 029 18:00
 */
object AppViewModel : YenalyViewModel(application), IHCsrfToken {

    /**
     * csrfToken 全局唯一，只需要在首页拉起或点击视频页时更新一下就可以了
     */
    override var csrfToken: String? = null

    private val _versionFlow = MutableStateFlow<WebsiteState<Latest?>>(WebsiteState.Loading)
    val versionFlow = _versionFlow.asStateFlow()

    private val _pendingUpdateDialog = MutableSharedFlow<Latest>(extraBufferCapacity = 1)
    val pendingUpdateDialog = _pendingUpdateDialog.asSharedFlow()

    val runningWorkInfoCountFlow = MutableStateFlow(0)

    init {
        // 取消，防止每次启动都有残留的更新任务
        WorkManager.getInstance(application).pruneWork()

        viewModelScope.launch(Dispatchers.IO) {
            // HanimeDownloadManager.init()
            HanimeDownloadManagerV2.init()
        }

        viewModelScope.launch(Dispatchers.Main) {
            Preferences.loginStateFlow.collect { isLogin ->
                Log.d("LoginState", "isLogin: $isLogin")
                Firebase.crashlytics.setCustomKeys {
                    key(FirebaseConstants.LOGIN_STATE, isLogin)
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            HUpdateWorker.collectOutput(application)
        }

        viewModelScope.launch(Dispatchers.IO) {
            HanimeDownloadWorker.getRunningWorkInfoCount(application).collect { count ->
                Log.d(HanimeDownloadWorker.TAG, "getRunningWorkInfoCount: $count")
                runningWorkInfoCountFlow.value = count
                Firebase.crashlytics.setCustomKeys {
                    key(FirebaseConstants.RUNNING_DOWNLOAD_WORK_COUNT, count)
                }
            }
        }
    }

    fun showUpdateDialogIfAvailable() {
        val state = _versionFlow.value
        if (state is WebsiteState.Success) {
            state.info?.let { latest ->
                viewModelScope.launch { _pendingUpdateDialog.emit(latest) }
            }
        }
    }

    fun getLatestVersion(forceCheck: Boolean = true, delayMillis: Long = 0, forceShow: Boolean = false) {
        viewModelScope.launch {
            delay(delayMillis)
            getLatestVersionSuspend(forceCheck, forceShow)
        }
    }

    private suspend fun getLatestVersionSuspend(forceCheck: Boolean = true, forceShow: Boolean = false) {
        NetworkRepo.getLatestVersion(forceCheck).collect {
            _versionFlow.value = it
            if (it is WebsiteState.Success && (forceShow || Preferences.isUpdateDialogVisible)) {
                it.info?.let { info -> _pendingUpdateDialog.emit(info) }
            }
        }
    }
}