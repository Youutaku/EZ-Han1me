package com.ezhan1me.app.logic.model.github

/**
 * @project EZ Han1me
 * @author Yenaly Liew
 * @time 2024/03/21 021 08:56
 */
data class Latest(
    val version: String,
    val changelog: String,
    val downloadLink: String,
    /**
     * Node ID for the download link
     *
     * 其实就是个变相的 md5 验证
     */
    val nodeId: String,
)
