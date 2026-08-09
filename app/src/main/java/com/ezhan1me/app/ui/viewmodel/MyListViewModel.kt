package com.ezhan1me.app.ui.viewmodel

import android.app.Application
import com.ezhan1me.app.ui.viewmodel.mylist.FavSubViewModel
import com.ezhan1me.app.ui.viewmodel.mylist.PlaylistSubViewModel
import com.ezhan1me.app.ui.viewmodel.mylist.WatchLaterSubViewModel
import com.ezhan1me.libs.base.YenalyViewModel

/**
 * @project EZ Han1me
 * @author Yenaly Liew
 * @time 2022/07/04 004 22:46
 */
class MyListViewModel(application: Application) : YenalyViewModel(application) {

    val playlist by sub<PlaylistSubViewModel>()
    val watchLater by sub<WatchLaterSubViewModel>()
    val fav by sub<FavSubViewModel>()
}
