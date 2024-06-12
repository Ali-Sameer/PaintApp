package com.example.paintapp


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.paintapp.MainActivity.Companion.paintBrush
import com.example.paintapp.MainActivity.Companion.path

class PaintView  : View{


   private var params : ViewGroup.LayoutParams? = null

    companion object{
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
    }

    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
private fun init(){
    paintBrush.isAntiAlias = true
    paintBrush.color = currentBrush
    paintBrush.style = Paint.Style.STROKE
    paintBrush.strokeJoin = Paint.Join.ROUND
    paintBrush.strokeWidth = 11f

    params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)



}

    @android.annotation.SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
       val sa = event.x
        val sz = event.y

        when (event.action){
            MotionEvent.ACTION_DOWN ->{
                path.moveTo(sa,sz)
                return true
            }
            MotionEvent.ACTION_MOVE ->{
                path.lineTo(sa,sz)
                pathList.add(path)
                colorList.add(currentBrush)
            }
            else ->return false
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas) {
       for(i in pathList.indices){
           paintBrush.color = colorList[i]
           canvas.drawPath(pathList[i], paintBrush)
           invalidate()
       }
    }
}