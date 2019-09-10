package com.anonyper.skinlibrary

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.anonyper.utillibrary.Loger
import java.util.ArrayList

/**
 * ChangeSkinApplication
 * Created by anonyper on 2019/8/7.
 */
class SkinFactory : LayoutInflater.Factory2 {
    var skinViewList: ArrayList<SkinView>? = null
    override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View? {
        Loger.i("！！！！！！！！！！！！！！！！！！！！！！！！！！！")
        var view: View? = createView(name!!, context!!, attrs!!)
        if (skinViewList == null) {
            skinViewList = ArrayList()
        }
        var skinView: SkinView? = null


        if (attrs != null && attrs.attributeCount > 0) {
            Loger.i("要处理的view:$name")
            var index = 0
            while (index < attrs.attributeCount) {
                val name = attrs.getAttributeName(index)
                Loger.i("要处理的view 属性:$name")
                if ("textColor".equals(name) || "src".equals(name) || "background".equals(name)) {
                    if (skinView == null) {
                        skinView = SkinView()
                        skinView.attrsList = ArrayList<SkinAttrs>()
                        skinView.view = view
                    }
                    val attrValue = attrs.getAttributeValue(index)
                    if (attrValue.startsWith("@")) {
                        try {
                            val id = Integer.parseInt(attrValue.substring(1))
                            val entryName = context.getResources().getResourceEntryName(id)
                            val resName = context.getResources().getResourceName(id)
                            val typeName = context.getResources().getResourceTypeName(id)
                            Loger.i("resName = " + resName)
                            Loger.i("entryName = " + entryName)
                            Loger.i("typeName = " + typeName)
                            Loger.i("name = " + name)
                            //构造SkinAttr实例,attrname,id,entryName,typeName
                            //属性的名称（background）、属性的id值（int类型），属性的id值（@+id，string类型），属性的值类型（color）
                            val skinAttr = SkinAttrs(name, id, typeName, entryName)
                            skinView.attrsList?.add(skinAttr)
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        } catch (e: Resources.NotFoundException) {
                            e.printStackTrace()
                        }

                    }

                }
                index++
            }
        }
        if (skinView != null) {
            skinView.changeSkin()
            skinViewList!!.add(skinView)
        }
        return view

    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        Loger.i("！！！！！！！！！！！！！！！！！！！！！！！！！！！")
        return onCreateView(null, name, context, attrs)
    }


    fun changeSkin() {
        if (skinViewList != null) {
            for (itemView in skinViewList!!) {
                itemView.changeSkin()
            }
        }
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        var view: View? = null
        try {
            if (-1 == name.indexOf('.')) {
                if ("View" == name) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs)
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs)
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs)
                }
            } else {
                view = LayoutInflater.from(context).createView(name, null, attrs)
            }
        } catch (e: Exception) {
            view = null
        }

        return view
    }
}