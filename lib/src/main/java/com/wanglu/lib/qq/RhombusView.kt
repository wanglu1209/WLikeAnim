package com.wanglu.lib.qq

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class RhombusView : View {

    private var viewWidth = 180
    private var viewHeight = 180

    private var rhombusWidth = 0f
    private var rhombusHeight = 0f

    private val rhombusPaint = Paint()
    private val transparentPaint = Paint()
    private val path = Path()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        rhombusPaint.color = Color.parseColor("#9932CC")
        transparentPaint.color = Color.WHITE

        rhombusPaint.isAntiAlias = true
        transparentPaint.isAntiAlias = true

        rhombusPaint.style = Paint.Style.FILL

        transparentPaint.strokeWidth = 5f
        post {
            val rhombusWidthAnim = ValueAnimator.ofFloat(0f, 360f)
            rhombusWidthAnim.addUpdateListener {
                rhombusWidth = it.animatedValue as Float
                invalidate()
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 测量的时候如果宽高都不为0再设置
//        if (viewWidth != 0 && viewHeight != 0) {
//            setMeasuredDimension(viewWidth, viewHeight)
//        }

        setMeasuredDimension(viewWidth, viewHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.moveTo((viewWidth / 2).toFloat(), 0f)
        path.lineTo(viewWidth.toFloat(), (viewHeight / 2).toFloat())
        path.lineTo((viewWidth / 2).toFloat(), viewHeight.toFloat())
        path.lineTo(0f, (viewHeight / 2).toFloat())
        canvas!!.drawPath(path, rhombusPaint)
        for (i in 1..6){
            canvas.drawLine(0f, (viewHeight / 6) * i.toFloat(), viewWidth.toFloat(), (viewHeight / 6) * i.toFloat(), transparentPaint)
        }
    }
}