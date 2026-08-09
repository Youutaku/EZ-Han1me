package com.ezhan1me.app.ui.navigation.main

import androidx.compose.runtime.Composable
import com.ezhan1me.app.ui.activity.MainActivity
import com.ezhan1me.app.ui.screen.video.VideoRouteHostScreen

@Composable
fun VideoRouteScreen(
    activity: MainActivity,
    route: VideoRoute,
) {
    VideoRouteHostScreen(activity = activity, route = route)
}
