package com.ezhan1me.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.ezhan1me.app.logic.DatabaseRepo
import com.ezhan1me.app.logic.entity.HKeyframeEntity
import com.ezhan1me.libs.base.YenalyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @project EZ Han1me
 * @author Yenaly Liew
 * @time 2022/07/01 001 13:40
 */
class SettingsViewModel(application: Application) : YenalyViewModel(application) {

    fun loadAllHKeyframes(keyword: String? = null) =
        DatabaseRepo.HKeyframe.loadAll(keyword).flowOn(Dispatchers.IO)

    fun deleteHKeyframes(entity: HKeyframeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepo.HKeyframe.delete(entity)
        }
    }

    fun updateHKeyframes(entity: HKeyframeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepo.HKeyframe.update(entity)
        }
    }

    fun removeHKeyframe(videoCode: String, keyframe: HKeyframeEntity.Keyframe) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepo.HKeyframe.removeKeyframe(videoCode, keyframe)
        }
    }


    fun modifyHKeyframe(
        videoCode: String,
        oldKeyframe: HKeyframeEntity.Keyframe, keyframe: HKeyframeEntity.Keyframe,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepo.HKeyframe.modifyKeyframe(videoCode, oldKeyframe, keyframe)
        }
    }

    fun insertHKeyframes(entity: HKeyframeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            DatabaseRepo.HKeyframe.insert(entity)
        }
    }

    fun loadAllSharedHKeyframes() =
        DatabaseRepo.HKeyframe.loadAllShared().flowOn(Dispatchers.IO)
}
