package com.ezhan1me.app.ui.navigation.main

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.ezhan1me.app.R
import com.ezhan1me.app.ui.activity.MainActivity
import com.ezhan1me.app.ui.screen.home.DailyCheckInScreen
import com.ezhan1me.app.ui.widget.CheckInWidgetProvider

@Composable
fun DailyCheckInRouteScreen(
    activity: MainActivity,
    onBack: () -> Unit,
    onNavigateToVideo: (String) -> Unit,
) {
    DailyCheckInScreen(
        activity = activity,
        onBack = onBack,
        onAddWidget = {
            val mgr = AppWidgetManager.getInstance(activity)
            Toast.makeText(
                activity,
                R.string.widget_pin_not_supported_manual_add,
                Toast.LENGTH_SHORT
            ).show()
            if (mgr.isRequestPinAppWidgetSupported) {
                mgr.requestPinAppWidget(
                    ComponentName(activity, CheckInWidgetProvider::class.java),
                    null, null,
                )
            } else {
                Toast.makeText(activity, R.string.widget_not_supported, Toast.LENGTH_SHORT).show()
            }
        },
        onNavigateToVideo = onNavigateToVideo,
    )
}
