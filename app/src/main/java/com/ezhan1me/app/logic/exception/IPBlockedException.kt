package com.ezhan1me.app.logic.exception

/**
 * IP被封鎖
 *
 * @project EZ Han1me
 * @author Yenaly Liew
 * @time 2023/08/07 007 12:40
 */
class IPBlockedException(reason: String) : CloudFlareBlockedException(reason)