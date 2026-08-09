package com.ezhan1me.app

import com.ezhan1me.libs.ActivityManager

object HCrashHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        e.printStackTrace()
        ActivityManager.restart(killProcess = true)
    }
}