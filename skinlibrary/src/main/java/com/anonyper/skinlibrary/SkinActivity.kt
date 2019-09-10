package com.anonyper.skinlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.anonyper.basecomponent.BaseActivity
import com.anonyper.utillibrary.Loger
import java.util.ArrayList
import android.content.res.Resources.NotFoundException
import android.os.Bundle
import android.view.LayoutInflater


open class SkinActivity : BaseActivity() {
    var mSkinInflaterFactory: SkinFactory? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Loger.i("开始mSkinInflaterFactory")
        super.onCreate(savedInstanceState)
        try {
            //设置LayoutInflater的mFactorySet为true，表示还未设置mFactory,否则会抛出异常。
            val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
            field.isAccessible = true
            field.setBoolean(layoutInflater, false)
            //设置LayoutInflater的MFactory
            mSkinInflaterFactory = SkinFactory()
            layoutInflater.factory2 = mSkinInflaterFactory
            Loger.i("设定了mSkinInflaterFactory")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    var skinViewList: ArrayList<SkinView>? = null

//    override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View? {
//
//        var view: View? = super.onCreateView(parent, name, context, attrs)
//        if (skinViewList == null) {
//            skinViewList = ArrayList()
//        }
//        var skinView: SkinView? = null
//
//
//        if (attrs != null && attrs.attributeCount > 0) {
//            Loger.i("要处理的view:$name")
//            var index = 0
//            while (index < attrs.attributeCount) {
//                val name = attrs.getAttributeName(index)
//                Loger.i("要处理的view 属性:$name")
//                if ("textColor".equals(name) || "src".equals(name) || "background".equals(name)) {
//                    if (skinView == null) {
//                        skinView = SkinView()
//                        skinView.attrsList = ArrayList<SkinAttrs>()
//                        skinView.view = view
//                    }
//                    val attrValue = attrs.getAttributeValue(index)
//                    if (attrValue.startsWith("@")) {
//                        try {
//                            val id = Integer.parseInt(attrValue.substring(1))
//                            val entryName = getResources().getResourceEntryName(id)
//                            val typeName = getResources().getResourceTypeName(id)
//                            //构造SkinAttr实例,attrname,id,entryName,typeName
//                            //属性的名称（background）、属性的id值（int类型），属性的id值（@+id，string类型），属性的值类型（color）
//                            val skinAttr = SkinAttrs(name, id, typeName, entryName)
//                            skinView.attrsList?.add(skinAttr)
//                        } catch (e: NumberFormatException) {
//                            e.printStackTrace()
//                        } catch (e: NotFoundException) {
//                            e.printStackTrace()
//                        }
//
//                    }
//
//                }
//                index++
//            }
//        }
//        if (skinView != null) {
//            skinView.changeSkin()
//            skinViewList!!.add(skinView)
//        }
//        return view
//    }

    override fun onResume() {
        super.onResume()
        mSkinInflaterFactory!!.changeSkin()
    }


}
