package com.wanglu.wlikeview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wanglu.lib.juejin.WJueJinLikeAnim
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var isLiked = false
        val likeAnim = WJueJinLikeAnim.Builder(iv, R.mipmap.fd_zan_press).create()
        iv.setOnClickListener {
            if(isLiked){
                iv.setImageResource(R.mipmap.fd_zan)
                isLiked = false
            }else{
                iv.setImageResource(R.mipmap.fd_zan_press)
                isLiked = true
                likeAnim.show()
            }
        }

    }
}
