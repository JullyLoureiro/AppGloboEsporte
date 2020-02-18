package com.example.desafioglobo.utils

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.WindowManager
import android.widget.LinearLayout
import android.view.Gravity
import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import com.example.desafioglobo.R
import java.util.*

class TransparentProgressDialog(context: Context, resourceIdOfImage: Int, titulo: String) :
    Dialog(context, R.style.TransparentProgressDialog) {

    private val iv: ImageView

    init {
        val wlmp = Objects.requireNonNull(getWindow())?.getAttributes()
        if (wlmp != null) {
            wlmp.gravity = Gravity.CENTER_HORIZONTAL
        }
        getWindow()?.setAttributes(wlmp)
        setTitle(titulo)
        setCancelable(false)
        setOnCancelListener(null)
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        iv = ImageView(context)
        iv.setImageResource(resourceIdOfImage)
        layout.addView(iv, params)
        addContentView(layout, params)
    }

    override fun show() {
        super.show()
        val anim = RotateAnimation(
            0.0f,
            360.0f,
            Animation.RELATIVE_TO_SELF,
            .5f,
            Animation.RELATIVE_TO_SELF,
            .5f
        )
        anim.interpolator = LinearInterpolator()
        anim.repeatCount = Animation.INFINITE
        anim.duration = 3000
        iv.setAnimation(anim)
        iv.startAnimation(anim)
    }
}