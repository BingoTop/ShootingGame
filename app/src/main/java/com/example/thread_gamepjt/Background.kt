package com.example.thread_gamepjt

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Background internal constructor(screenX:Int, screenY:Int, res: Resources) {
    internal var background:Bitmap
    var x = 0
    var y = 0
    init{
        background = BitmapFactory.decodeResource(res, R.drawable.background1)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
    }
}