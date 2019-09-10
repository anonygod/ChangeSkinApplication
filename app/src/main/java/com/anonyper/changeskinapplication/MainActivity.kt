package com.anonyper.changeskinapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.anonyper.skinlibrary.SkinActivity

class MainActivity : SkinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.text_view).setOnClickListener {
            val intent: Intent = Intent()
            intent.setClass(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }


}