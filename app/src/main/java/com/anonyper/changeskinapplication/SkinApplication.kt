package com.anonyper.changeskinapplication

import android.app.Application
import com.anonyper.skinlibrary.SkinManager

/**
 * ChangeSkinApplication
 * Created by anonyper on 2019/8/7.
 */
class SkinApplication : Application() {
    var skinApkPath = "/sdcard/skin.apk"
    override fun onCreate() {
        super.onCreate()
        SkinManager.instance.init(this, skinApkPath)
    }
}