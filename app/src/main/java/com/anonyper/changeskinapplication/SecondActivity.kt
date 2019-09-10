package com.anonyper.changeskinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anonyper.skinlibrary.SkinActivity

class SecondActivity : SkinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}
