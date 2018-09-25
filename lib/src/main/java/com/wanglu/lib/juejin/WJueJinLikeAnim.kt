package com.wanglu.lib.juejin

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.PopupWindow
import com.wanglu.lib.R

class WJueJinLikeAnim(context: Context?, private val builder: Builder) : PopupWindow(context) {

    private val imgScaleXAnim: ObjectAnimator
    private val imgScaleYAnim: ObjectAnimator
    private val animSet = AnimatorSet()
    private val iv: ImageView
    private val cv: CircleView
    private val dv: DotsView

    private val SCALE_FACTOR = 3

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.view_jue_jin, null)
        iv = contentView.findViewById(R.id.iv)
        cv = contentView.findViewById(R.id.cv)
        dv = contentView.findViewById(R.id.dv)
        cv.setDv(dv)

//        isTouchable = false
//        setTouchInterceptor { v, event ->
//
//            cv.cancelAnim()
//            dismiss()
//            false
//        }
        imgScaleXAnim = ObjectAnimator.ofFloat(iv, "scaleX", 1.5f, 1f)
        imgScaleYAnim = ObjectAnimator.ofFloat(iv, "scaleY", 1.5f, 1f)
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.view.post {

            // 如果没有设置padding，则把iv的宽高设置为view的宽高
            val params = iv.layoutParams
            if (builder.view.paddingBottom == 0 && builder.view.paddingTop == 0 && builder.view.paddingLeft == 0 && builder.view.paddingRight == 0) {
                params.width = builder.width
                params.height = builder.height
            } else {
                // 如果设置了padding，则获取图片原始的大小
                params.width = builder.view.drawable.bounds.width()
                params.height = builder.view.drawable.bounds.height()
            }

            iv.setImageResource(builder.imgRes)
            iv.layoutParams = params



            width = params.width * SCALE_FACTOR
            height = params.height * SCALE_FACTOR

            cv.setColor(builder.color1, builder.color2)
            dv.setColor(builder.color1, builder.color2)

            cv.setSize(width, height)
            dv.setSize(width, height)

            animSet.duration = 600
            animSet.interpolator = BounceInterpolator() // 设置插值器
            animSet.playTogether(imgScaleXAnim, imgScaleYAnim)
            animSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    dismiss()
                }

            })
        }

    }

    fun show() {
        val viewLocation = IntArray(2)
        builder.view.getLocationInWindow(viewLocation)
        val x = viewLocation[0] + builder.width / 2 - width / 2
        val y = viewLocation[1] + builder.height / 2 - height / 2
        showAtLocation(iv, Gravity.NO_GRAVITY, x, y)
        cv.startAnim()
        animSet.start()
    }

    class Builder(val view: ImageView, val imgRes: Int) {

        internal var color1 = Color.parseColor("#48CFC2")
        internal var color2 = Color.parseColor("#5BA2E9")

        internal var width: Int = 0
        internal var height: Int = 0

        init {
            view.post {
                width = view.width
                height = view.height
            }
        }

        /**
         * 设置颜色
         */
        fun setColor(color1: Int, color2: Int) {
            this.color1 = color1
            this.color2 = color2
        }


        fun create(): WJueJinLikeAnim {
            return WJueJinLikeAnim(view.context, this)
        }
    }
}