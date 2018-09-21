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

class CircleView : View {

    private val outerCirclePaint = Paint()
    private val innerCirclePaint = Paint()

    private var viewWidth = 0
    private var viewHeight = 0

    private var outerCircleColor = Color.parseColor("#5BA2E9")
    private var innerCircleColor = Color.parseColor("#48CFC2")

    private var outerCircleRadius = 0f
    private var innerCircleRadius = 0f
    private var outerStrokeWidth = 10f
    private var outerCircleMaxRadius = 0f
    private var innerCircleMaxRadius = 0f


    private var animSet = AnimatorSet()
    private lateinit var dv: DotsView

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        outerCirclePaint.color = outerCircleColor
        outerCirclePaint.isAntiAlias = true
        outerCirclePaint.style = Paint.Style.STROKE
        outerCirclePaint.strokeWidth = outerStrokeWidth

        innerCirclePaint.color = innerCircleColor
        innerCirclePaint.isAntiAlias = true
        innerCirclePaint.style = Paint.Style.FILL

    }


    fun startAnim() {
        animSet.start()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (viewWidth != 0 && viewHeight != 0) {
            setMeasuredDimension(viewWidth, viewHeight)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), outerCircleRadius, outerCirclePaint)
        canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), innerCircleRadius, innerCirclePaint)
    }


    /**
     * 根据imageView的大小设置size
     */
    fun setSize(width: Int, height: Int) {
        this.viewWidth = width
        this.viewHeight = height
        outerStrokeWidth = (viewWidth / 2 * 0.1).toFloat()
        outerCircleMaxRadius = viewWidth / 2f - 30
        innerCircleMaxRadius = outerCircleMaxRadius - outerStrokeWidth / 2


        val outerRadiusAnim = ValueAnimator.ofFloat(outerCircleRadius, outerCircleMaxRadius)
        outerRadiusAnim.duration = 200
        outerRadiusAnim.addUpdateListener {
            outerCircleRadius = it.animatedValue as Float
            invalidate()
        }

        val innerRadiusAnim = ValueAnimator.ofFloat(innerCircleRadius, innerCircleMaxRadius)
        innerRadiusAnim.duration = 200
        innerRadiusAnim.addUpdateListener {
            innerCircleRadius = it.animatedValue as Float
            invalidate()
        }

        val outerStrokeWidthAnim = ValueAnimator.ofFloat(outerStrokeWidth, 0f)
        outerStrokeWidthAnim.duration = 100
        outerStrokeWidthAnim.addUpdateListener {
            outerStrokeWidth = it.animatedValue as Float
            outerCirclePaint.strokeWidth = outerStrokeWidth
            invalidate()
        }

        innerRadiusAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                innerCirclePaint.color = Color.TRANSPARENT
                invalidate()
            }

        })

        outerRadiusAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                innerCirclePaint.color = Color.TRANSPARENT
                invalidate()
            }

        })



        animSet.play(outerRadiusAnim).with(innerRadiusAnim).before(outerStrokeWidthAnim)
        animSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {
                outerCircleRadius = 0f
                innerCircleRadius = 0f
                outerStrokeWidth = (viewWidth / 2 * 0.1).toFloat()
                outerCirclePaint.strokeWidth = outerStrokeWidth
                innerCirclePaint.color = innerCircleColor
                dv.cancelAnim()
            }

            override fun onAnimationStart(animation: Animator?) {
                dv.show()
            }

            override fun onAnimationEnd(animation: Animator?) {
                outerCircleRadius = 0f
                innerCircleRadius = 0f
                outerStrokeWidth = (viewWidth / 2 * 0.1).toFloat()
                outerCirclePaint.strokeWidth = outerStrokeWidth
                innerCirclePaint.color = innerCircleColor
                dv.dismiss()
            }

        })
        invalidate()
    }

    /**
     * 设置颜色
     */
    fun setColor(color1: Int, color2: Int) {
        innerCircleColor = color1
        outerCircleColor = color2
        invalidate()
    }

    fun cancelAnim() {
        animSet.cancel()
    }

    fun isAnimRunning(): Boolean {
        return animSet.isRunning
    }

    fun setDv(dv: DotsView){
        this.dv = dv
    }
}