package com.anonyper.skinlibrary

import android.view.View

/**
 * ChangeSkinApplication
 * Created by anonyper on 2019/8/6.
 */
class SkinView() {
    var view: View? = null//View
    var attrsList: ArrayList<SkinAttrs>? = null//View对应的属性
    fun changeSkin() {//换肤
        if (attrsList != null && view != null) {
            SkinManager.instance.changeSkin(this)
        }
    }
}