package com.wanglu.lib

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View

class WJiKeLikeAnim : View {

    // 选中的
    private var selectedRes: Int

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    @SuppressLint("Recycle")
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val arr = context!!.obtainStyledAttributes(attrs, R.styleable.WJiKeLikeAnim, defStyleAttr, 0)
        selectedRes = arr.getResourceId(R.styleable.WJiKeLikeAnim_selectedRes, R.drawable.ic_messages_like_selected)
    }


}