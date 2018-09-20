package com.wanglu.lib.juejin

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

    private var viewWidth = 174
    private var viewHeight = 174

    private var outerCircleColor = Color.parseColor("#5BA2E9")
    private var innerCircleColor = Color.parseColor("#48CFC2")
    
    private var outerCircleRadius = 0f
    private var innerCircleRadius = 0f
    private var outerCircleMaxRadius = viewWidth / 2f - 30
    private var innerCircleMaxRadius = viewWidth / 2f - 35


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        outerCirclePaint.color = outerCircleColor
        outerCirclePaint.isAntiAlias = true
        outerCirclePaint.style = Paint.Style.STROKE
        outerCirclePaint.strokeWidth = 5f

        innerCirclePaint.color = innerCircleColor
        innerCirclePaint.isAntiAlias = true
        innerCirclePaint.style = Paint.Style.FILL
        
        
        val outerRadiusAnim = ValueAnimator.ofFloat(outerCircleRadius, outerCircleMaxRadius)
        outerRadiusAnim.duration = 400
        outerRadiusAnim.addUpdateListener{
            outerCircleRadius = it.animatedValue as Float
            invalidate()
        }
        
        val innerRadiusAnim = ValueAnimator.ofFloat(outerCircleRadius, innerCircleMaxRadius)
        innerRadiusAnim.duration = 400
        innerRadiusAnim.addUpdateListener{
            outerCircleRadius = it.animatedValue as Float
            invalidate()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(viewWidth, viewHeight)
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

}