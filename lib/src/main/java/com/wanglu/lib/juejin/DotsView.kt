package com.wanglu.lib.juejin

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DotsView : View {

    private val bigDotPaint = Paint()
    private val smallDotPaint = Paint()


    private var viewWidth = 0
    private var viewHeight = 0
    private var centerX = 0
    private var centerY = 0
    private var bigDotColor = Color.parseColor("#48CFC2")
    private var smallDotColor = Color.parseColor("#5BA2E9")

    private var bigDotRadius = MAX_BIG_DOT_RADIUS   // 大点的半径
    private var smallDotRadius = MAX_SMALL_DOT_RADIUS // 小点的半径


    companion object {
        const val DOT_COUNT = 7
        const val DOT_ANGLE = 360 / DOT_COUNT
        const val MAX_BIG_DOT_RADIUS = 5f
        const val MAX_SMALL_DOT_RADIUS = 3f
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        bigDotPaint.color = Color.TRANSPARENT
        bigDotPaint.isAntiAlias = true
        bigDotPaint.style = Paint.Style.FILL

        smallDotPaint.color = Color.TRANSPARENT
        smallDotPaint.isAntiAlias = true
        smallDotPaint.style = Paint.Style.FILL
    }


    /**
     * 设置颜色
     */
    fun setColor(color1: Int, color2: Int){
        bigDotColor = color1
        smallDotColor = color2
        invalidate()
    }


    /**
     * 根据imageView的大小设置size
     */
    fun setSize(width: Int, height: Int) {
        this.viewWidth = width
        this.viewHeight = height
        invalidate()
    }

    fun show(){
        bigDotPaint.color = bigDotColor
        smallDotPaint.color = smallDotColor
        invalidate()
    }


    private var animSet = AnimatorSet()

    /**
     * 消失
     */
    fun dismiss(){
        val bigDotAnim = ValueAnimator.ofFloat(bigDotRadius, 0f)
        val smallDotAnim = ValueAnimator.ofFloat(smallDotRadius, 0f)
        bigDotAnim.duration = 300
        smallDotAnim.duration = 300
        bigDotAnim.addUpdateListener {
            bigDotRadius = it.animatedValue as Float
            invalidate()
        }

        smallDotAnim.addUpdateListener {
            smallDotRadius = it.animatedValue as Float
            invalidate()
        }

        animSet.playTogether(bigDotAnim, smallDotAnim)
        animSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {
                bigDotPaint.color = Color.TRANSPARENT
                smallDotPaint.color = Color.TRANSPARENT

                bigDotRadius = MAX_BIG_DOT_RADIUS
                smallDotRadius = MAX_SMALL_DOT_RADIUS
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                // 动画结束后还原
                bigDotPaint.color = Color.TRANSPARENT
                smallDotPaint.color = Color.TRANSPARENT

                bigDotRadius = MAX_BIG_DOT_RADIUS
                smallDotRadius = MAX_SMALL_DOT_RADIUS
                invalidate()
            }

        })
        animSet.start()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 测量的时候如果宽高都不为0再设置
        if (viewWidth != 0 && viewHeight != 0) {
            setMeasuredDimension(viewWidth, viewHeight)
            centerX = viewWidth / 2
            centerY = viewHeight / 2
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 画大点
        for (i in 0 until DOT_COUNT) {
            // 计算出大点的 X,Y 值  为该View中心点坐标 加上 整个View的一半   因 cos 和 sin 返回的是『-1 ~ 1』的值
            val cX = centerX + (centerX - 10) * Math.cos((i * DOT_ANGLE) * Math.PI / 180)
            val cY = centerY + (centerY - 10) * Math.sin((i * DOT_ANGLE) * Math.PI / 180)
            canvas!!.drawCircle(cX.toFloat(), cY.toFloat(), bigDotRadius, bigDotPaint)
        }

        // 画小点
        for (i in 0 until DOT_COUNT) {
            val cX = (centerX + (centerX - 10) * Math.cos((i * DOT_ANGLE - 10) * Math.PI / 180)).toFloat()
            val cY = (centerY + (centerY - 10) * Math.sin((i * DOT_ANGLE - 10) * Math.PI / 180)).toFloat()
            canvas!!.drawCircle(cX, cY, smallDotRadius, smallDotPaint)
        }

    }

    fun cancelAnim(){
        animSet.cancel()
    }

}