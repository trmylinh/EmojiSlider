package com.bernaferrari.emojislider

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.graphics.drawable.Drawable.Callback
import android.support.v4.content.ContextCompat

import com.facebook.rebound.SimpleSpringListener
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringSystem

class DrawableProfilePicture(context: Context) : Drawable(), Callback {
    internal val f32860a: TextDrawable
    internal val drawableAverageHandle: DrawableAverageCircle

    private val mSpringSystem = SpringSystem.create()
    private val mSpringListener = object : SimpleSpringListener() {
        override fun onSpringUpdate(spring: Spring?) {
            invalidateSelf()
        }
    }

    private val profileSpring = mSpringSystem.createSpring()
        .origamiConfig(40.0, 7.0)
        .setCurrentValue(0.0)
        .addListener(mSpringListener)

    internal var sizeHandle: Float = 0f

    init {
        this.f32860a = TextDrawable(context, getWidthPixels(context))
        this.f32860a.callback = this
        this.drawableAverageHandle = DrawableAverageCircle(context)
        this.drawableAverageHandle.callback = this
        this.drawableAverageHandle.outerColor =
                ContextCompat.getColor(context, R.color.colorPrimary)
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
    override fun scheduleDrawable(drawable: Drawable, runnable: Runnable, j: Long) {}
    override fun unscheduleDrawable(drawable: Drawable, runnable: Runnable) {}

    fun getWidthPixels(context: Context): Int = context.resources.displayMetrics.widthPixels

    private fun drawCircle(canvas: Canvas) {

        val drawable = drawableAverageHandle

//        drawable = when (f20875a[c5186e.ordinal]) {
//            1 -> this.drawableAverageHandle
//            2 -> this.drawableAverageHandle
//            3 -> this.drawableAverageHandle
//            else -> this.drawableAverageHandle
//        }//                StringBuilder stringBuilder = new StringBuilder("Unsupported handle type: ");
        //                stringBuilder.append(this.f32865f);
        //                throw new IllegalStateException(stringBuilder.toString());
        val intrinsicWidth = (this.sizeHandle - drawable.intrinsicWidth.toFloat()) / 2.0f
        val intrinsicHeight = (this.sizeHandle - drawable.intrinsicHeight.toFloat()) / 2.0f
        val scale = profileSpring.currentValue.toFloat()

        canvas.save()
        canvas.translate(intrinsicWidth, intrinsicHeight)
        canvas.scale(scale, scale, bounds.exactCenterX(), bounds.exactCenterY())
        drawable.draw(canvas)
        canvas.restore()
    }

    fun show() {
        this.profileSpring.endValue = 1.0
        invalidateSelf()
    }

    fun hide() {
        this.profileSpring.endValue = 0.0
        invalidateSelf()
    }

    override fun draw(canvas: Canvas) {
        drawCircle(canvas)
    }

    override fun getIntrinsicHeight(): Int {
        return this.sizeHandle.toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return this.sizeHandle.toInt()
    }

    override fun invalidateDrawable(drawable: Drawable) {
        invalidateSelf()
    }

    override fun setAlpha(i: Int) {
        this.f32860a.alpha = i
        this.drawableAverageHandle.alpha = i
    }

    override fun setBounds(i: Int, i2: Int, i3: Int, i4: Int) {
        super.setBounds(i, i2, i3, i4)
        this.f32860a.setBounds(i, i2, i3, i4)
        this.drawableAverageHandle.setBounds(i, i2, i3, i4)
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        this.f32860a.colorFilter = colorFilter
        this.drawableAverageHandle.colorFilter = colorFilter
    }
}