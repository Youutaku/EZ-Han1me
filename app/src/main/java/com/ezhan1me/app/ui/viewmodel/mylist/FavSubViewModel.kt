package com.ezhan1me.app.ui.viewmodel.mylist

import android.app.Application
import com.ezhan1me.app.Preferences
import com.ezhan1me.app.logic.NetworkRepo
import com.ezhan1me.app.logic.model.HanimeInfo
import com.ezhan1me.app.logic.model.MyListItems
import com.ezhan1me.app.logic.model.MyListType
import com.ezhan1me.app.logic.state.PageLoadingState
import com.ezhan1me.app.logic.state.WebsiteState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class FavSubViewModel(application: Application) : MyListSubViewModel(application) {

    var favVideoPage = 1
    private var csrfToken: String? = null

    val favVideoStateFlow: StateFlow<PageLoadingState<MyListItems<HanimeInfo>>> = itemsStateFlow.asStateFlow()
    val favVideoFlow: StateFlow<List<HanimeInfo>> = itemsFlow.asStateFlow()

    fun getMyFavVideoItems(userId: String, page: Int) {
        loadItems(MyListType.FAV_VIDEO, userId, page) { csrfToken = it.csrfToken }
    }

    private val _deleteMyFavVideoFlow = MutableSharedFlow<WebsiteState<Boolean>>()
    val deleteMyFavVideoFlow = _deleteMyFavVideoFlow.asSharedFlow()

    fun deleteMyFavVideo(videoCode: String, position: Int) {
        deleteItem(
            deleteCall = {
                NetworkRepo.addToMyFavVideo(
                    videoCode = videoCode,
                    likeStatus = true,
                    currentUserId = Preferences.savedUserId,
                    token = csrfToken,
                )
            },
            emitTo = _deleteMyFavVideoFlow,
            position = position,
            mapState = { it },
        )
    }

    override fun clearMyListItems() {
        super.clearMyListItems()
        favVideoPage = 1
    }
}
