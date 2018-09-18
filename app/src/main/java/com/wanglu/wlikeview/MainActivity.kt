package com.wanglu.wlikeview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wanglu.lib.WCommonAnim
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val anim = WCommonAnim(iv)
        iv.setOnClickListener {
            iv.setImageResource(R.mipmap.icon_red_heart)
            anim.start()
        }
    }
}
