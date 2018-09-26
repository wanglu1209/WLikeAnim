package com.wanglu.lib.qq

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
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
    private var paintAlpha = 255


    private val paint1 = Paint()
    private val paint2 = Paint()
    private val paint3 = Paint()
    private val circlePaint = Paint()

    private var strokeWidth = 8f

    private var circleRadius = 0f
    private var circleAlpha = 255

    private var rectF = RectF()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        paint1.color = Color.parseColor("#80D22D")
        paint2.color = Color.parseColor("#FCBC84")
        paint3.color = Color.parseColor("#8DDCF8")
        circlePaint.color = Color.parseColor("#87DDFB")

        paint1.isAntiAlias = true
        paint2.isAntiAlias = true
        paint3.isAntiAlias = true
        circlePaint.isAntiAlias = true

        paint1.alpha = paintAlpha
        paint2.alpha = paintAlpha
        paint3.alpha = paintAlpha
        circlePaint.alpha = circleAlpha

        paint1.strokeWidth = strokeWidth
        paint2.strokeWidth = strokeWidth
        paint3.strokeWidth = strokeWidth

        paint1.style = Paint.Style.STROKE
        paint2.style = Paint.Style.STROKE
        paint3.style = Paint.Style.STROKE
        circlePaint.style = Paint.Style.FILL

        paint1.strokeCap = Paint.Cap.ROUND
        paint2.strokeCap = Paint.Cap.ROUND
        paint3.strokeCap = Paint.Cap.ROUND


        post {

            // 三条曲线转动的偏移量
            val offsetAngleAnim = ValueAnimator.ofFloat(0f, 360f)
            offsetAngleAnim.addUpdateListener {
                offsetAngle = it.animatedValue as Float
                invalidate()
            }
            // 三条曲线的长度
            val sweepAngleAnim = ValueAnimator.ofFloat(0f, maxSweepAngle, 0f)
            sweepAngleAnim.addUpdateListener {
                sweepAngle = it.animatedValue as Float
                invalidate()
            }
            // 透明度
            val alphaAnim = ValueAnimator.ofInt(100, 255, 0)
            alphaAnim.addUpdateListener {
                paintAlpha = it.animatedValue as Int
                paint1.alpha = paintAlpha
                paint2.alpha = paintAlpha
                paint3.alpha = paintAlpha
                circlePaint.alpha = paintAlpha
                invalidate()
            }
            // 中间圆的半径
            val radiusAnim = ValueAnimator.ofFloat(viewWidth * 0.15f, viewWidth / 2f)
            radiusAnim.addUpdateListener {
                circleRadius = it.animatedValue as Float
                invalidate()
            }
            val animSet = AnimatorSet()
            animSet.playTogether(offsetAngleAnim, sweepAngleAnim, alphaAnim, radiusAnim)
            animSet.duration = 1000

            animSet.addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    animSet.start()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })
            animSet.start()

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 测量的时候如果宽高都不为0再设置
//        if (viewWidth != 0 && viewHeight != 0) {
//            setMeasuredDimension(viewWidth, viewHeight)
//        }

        setMeasuredDimension(viewWidth, viewHeight)
        rectF.left = viewWidth * 0.1f
        rectF.top = viewHeight * 0.1f
        rectF.right = viewWidth * 0.9f
        rectF.bottom = viewHeight * 0.9f
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawArc(rectF, startAngle + offsetAngle % 360, sweepAngle, false, paint1)
        canvas.drawArc(rectF, (startAngle + maxSweepAngle * 2) + offsetAngle % 360, sweepAngle / 4 * 3, false, paint2)
        canvas.drawArc(rectF, (startAngle + maxSweepAngle * 4) + offsetAngle % 360, sweepAngle / 6 * 5, false, paint3)
        canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), circleRadius, circlePaint)
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