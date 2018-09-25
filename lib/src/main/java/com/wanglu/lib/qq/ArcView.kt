package com.wanglu.lib.qq

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View

class ArcView : View {

    private var viewWidth = 200
    private var viewHeight = 200


    private var startAngle = 270f   // 开始角度
    private var sweepAngle = 0f    // 弧有多长  角度
    private var maxSweepAngle = 60f // 最大角度
    private var offsetAngle = 0f    // 角度偏移


    private val paint1 = Paint()
    private val paint2 = Paint()
    private val paint3 = Paint()

    private var strokeWidth = 8f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        paint1.color = Color.parseColor("#80D22D")
        paint2.color = Color.parseColor("#FCBC84")
        paint3.color = Color.parseColor("#8DDCF8")

        paint1.isAntiAlias = true
        paint2.isAntiAlias = true
        paint3.isAntiAlias = true

        paint1.strokeWidth = strokeWidth
        paint2.strokeWidth = strokeWidth
        paint3.strokeWidth = strokeWidth

        paint1.style = Paint.Style.STROKE
        paint2.style = Paint.Style.STROKE
        paint3.style = Paint.Style.STROKE

        paint1.strokeCap = Paint.Cap.ROUND
        paint2.strokeCap = Paint.Cap.ROUND
        paint3.strokeCap = Paint.Cap.ROUND

        var isNeedAdd = true    // 是否加法

        val handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                offsetAngle += 10f
                

                if(isNeedAdd){
                    sweepAngle += 1f
                    if(sweepAngle >= maxSweepAngle){
                        isNeedAdd = false
                    }
                }else{
                    sweepAngle -= 1f
                    if(sweepAngle <= 0){
                        isNeedAdd = true
                    }
                }
                invalidate()
                sendEmptyMessage(1)
            }
        }

        post {
            handler.sendEmptyMessage(1)
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawArc(5f, 5f, 195f, 195f, startAngle + offsetAngle % 360, sweepAngle, false, paint1)
        canvas.drawArc(5f, 5f, 195f, 195f, (startAngle + maxSweepAngle * 2) + offsetAngle % 360, sweepAngle, false, paint2)
        canvas.drawArc(5f, 5f, 195f, 195f, (startAngle + maxSweepAngle * 4) + offsetAngle % 360, sweepAngle, false, paint3)
    }


    /**
     * 根据imageView的大小设置size
     */
    fun setSize(width: Int, height: Int) {
        this.viewWidth = width
        this.viewHeight = height
        invalidate()
    }

}