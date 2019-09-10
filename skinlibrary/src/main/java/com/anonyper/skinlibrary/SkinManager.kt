package com.anonyper.skinlibrary

import android.content.Context
import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources
import android.content.pm.PackageManager
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.anonyper.utillibrary.Loger
import java.io.File


/**
 * 皮肤管理类的单例模式
 * SkinManager
 * Created by anonyper on 2019/7/25.
 */
class SkinManager private constructor() {
    var context: Context? = null
    var skinResources: Resources? = null
    var currentResources: Resources? = null
    var skinPackageName: String? = null

    companion object {
        val instance: SkinManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SkinManager()
        }
    }

    fun init(context: Context, skinApkPath: String?) {
        this.context = context
        Loger.i(skinApkPath)
        if (TextUtils.isEmpty(skinApkPath)) {
            return
        }
        currentResources = context.resources
        skinResources = createSkinResource(skinApkPath)
        skinPackageName = getSkinPackageName(skinApkPath)
    }

    fun changeSkin(skinView: SkinView) {
        Loger.i("开始换肤")
        if (skinView == null)
            return
        if (skinView.view == null)
            return
        if (skinResources == null)
            return
        if (TextUtils.isEmpty(skinPackageName))
            return
        Loger.i("skinPackageName = " + skinPackageName)
        var view = skinView.view
        if (skinView.attrsList != null) {
            for (item in skinView.attrsList!!) {
                Loger.i("~~~~~~~~~~~~~~~~~~~")
                Loger.i(item.name)
                Loger.i(item.type)
                Loger.i("~~~~~~~~~~~~~~~~~~~")
                if ("textColor".equals(item.name)) {//字体颜色
                    changeTextColor(view, item.resId, item.type, item.value)
                } else if ("background".equals(item.name)) {//背景
                    if ("color".equals(item.type)) {
                        changeBackGroundColor(view, item.resId, item.type, item.value)
                    } else {
                        changeBackGroundDrawable(view, item.resId, item.type, item.value)
                    }
                } else if ("src".equals(item.name)) {//图片
                    changeViewSrcDrawable(view, item.resId, item.type, item.value)
                }
            }
        }
    }

    /**
     * 可能是TextView 也可能是Button
     */

    fun changeTextColor(view: View?, resID: Int?, type: String?, value: String?) {//更改字体颜色
        if (view == null || resID == 0 || TextUtils.isEmpty(type) || TextUtils.isEmpty(value)) {
            return
        }
        var color = getColorById(resID, type, value)
        if (color != 0) {
            if (view is TextView) {
                view.setTextColor(getColorById(resID, type, value))
            }
        }

    }

    /**
     * 更改View的背景颜色
     */
    fun changeBackGroundColor(view: View?, resID: Int?, type: String?, value: String?) {//更改字体颜色
        if (view == null || resID == 0 || TextUtils.isEmpty(type) || TextUtils.isEmpty(value)) {
            return
        }
        var color = getColorById(resID, type, value)
        if (color != 0)
            view.setBackgroundColor(color)

    }

    /**
     * 更改View的背景图片
     */
    fun changeBackGroundDrawable(view: View?, resID: Int?, type: String?, value: String?) {
        if (view == null || resID == 0 || TextUtils.isEmpty(type) || TextUtils.isEmpty(value)) {
            return
        }
        var drawable = getDrawableById(resID, type, value)
        if (drawable != null)
            view.setBackgroundDrawable(drawable)

    }

    /**
     * 更改View的资源图片
     */
    fun changeViewSrcDrawable(view: View?, resID: Int?, type: String?, value: String?) {
        if (view == null || resID == 0 || TextUtils.isEmpty(type) || TextUtils.isEmpty(value)) {
            return
        }
        var drawable = getDrawableById(resID, type, value)
        if (drawable != null && view is ImageView)
            view.setImageDrawable(drawable)


    }


    fun getColorById(resID: Int?, type: String?, value: String?): Int {
        var color: Int
        if (resID == 0) {
            return 0
        }
        var currentColor = currentResources?.getColor(resID!!)!!
        if (skinResources == null)
            return currentColor
        val resName = resID?.let { currentResources!!.getResourceEntryName(it) }
        Loger.i("resName==$resName")
        Loger.i("type==$type")
        Loger.i("value==$value")
        Loger.i("skinPackageName==$skinPackageName")
        val trueResId = skinResources?.getIdentifier(value, type, skinPackageName)
        var trueColor = 0
        Loger.i("trueResId==$trueResId")
        try {
            //根据resId获取对应的资源value
            trueColor = trueResId?.let { skinResources?.getColor(it) }!!
        } catch (e: NotFoundException) {
            e.printStackTrace()
            trueColor = currentColor
        }
        Loger.i("color" + trueColor)
        return trueColor

    }

    /**
     * 根据资源信息得到皮肤包的drawable
     */

    fun getDrawableById(resID: Int?, type: String?, value: String?): Drawable {
        var drawable: Drawable? = null
        if (resID == 0) {
            return drawable!!
        }
        var currentDrawable = currentResources?.getDrawable(resID!!)!!
        if (skinResources == null)
            return currentDrawable

        val trueResId = skinResources?.getIdentifier(value, type, skinPackageName)
        try {
            //根据resId获取对应的资源value
            drawable = trueResId?.let { skinResources?.getDrawable(it) }!!
        } catch (e: NotFoundException) {
            e.printStackTrace()
            drawable = currentDrawable
        }
        return drawable!!

    }

    fun getSkinPackageName(skinApkPath: String?): String? {

        if (skinApkPath == null)
            return skinPackageName
        if (context == null) {
            throw RuntimeException("u need load init method first!!!")
        }

        val mPm = context?.getPackageManager()
        //检索程序外的一个安装包文件
        val mInfo = mPm?.getPackageArchiveInfo(skinApkPath, PackageManager.GET_ACTIVITIES)
        if (mInfo == null) {
            Loger.i("info 为空")
        }
        //获取安装包报名
        skinPackageName = mInfo?.packageName
        Loger.i(skinPackageName)
        return skinPackageName
    }

    /**
     * 根据apk路径获取对应的资源信息
     */
    fun createSkinResource(skinApkPath: String?): Resources? {
        val file: File = File(skinApkPath)
        if (file.exists())
            Loger.i("skinApkPath 存在")
        var assetManager: AssetManager? = null
        try {
            assetManager = AssetManager::class.java.newInstance()
            val method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            method.invoke(assetManager, skinApkPath)
            val resources = this.context?.getResources()
            Loger.i("创建资源信息成功")
            return Resources(assetManager, resources?.getDisplayMetrics(), resources?.getConfiguration())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Loger.i("创建资源信息其实是失败的")
        return null

    }
}
