package com.anonyper.skinlibrary

import android.view.View

/**
 * ChangeSkinApplication
 * Created by anonyper on 2019/8/6.
 */
class SkinAttrs(name: String?, resId: Int?, type: String?, value: String?) {
    var name: String? = name //textColor src background
    var resId: Int? = resId //资源ID
    var type: String? = type //字段的类型 color drawable
    var value: String? = value //字段的值
}