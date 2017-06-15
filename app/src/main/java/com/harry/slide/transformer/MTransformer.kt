package com.harry.slide.transformer

import android.support.v4.view.ViewPager
import android.view.View
import com.harry.R

/**
 * 类/接口描述
 * @author Harry
 * @date 2017/5/18.
 */
class MTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val wallpaper = view.findViewById(R.id.iv)
        if (position < -1) { // [-Infinity,-1)
            wallpaper.translationX = 0.toFloat()
            view.translationX = 0.toFloat()
        } else if (position <= 1) { // [-1,1]
            wallpaper.translationX = pageWidth * getFactor(position)
            view.translationX = 8 * position
        } else { // (1,+Infinity]
            wallpaper.translationX = 0.toFloat()
            view.translationX = 0.toFloat()
        }
    }

    private fun getFactor(position: Float): Float {
        return -position / 2
    }
}