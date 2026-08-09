package com.ezhan1me.app.logic.exception

import com.ezhan1me.app.R

/**
 * 检测到爬虫被封鎖
 *
 * @project EZ Han1me
 * @author Yenaly Liew
 * @time 2023/08/07 007 12:45
 */
open class CloudFlareBlockedException(reason: String) : RuntimeException(reason) {
    companion object {
        val localizedMessages = intArrayOf(
            R.string.website_blocked_msg,
            R.string.website_blocked_msg_2,
            R.string.website_blocked_msg_3,
        )
    }
}