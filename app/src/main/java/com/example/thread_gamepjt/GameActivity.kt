package com.example.thread_gamepjt

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class GameActivity : AppCompatActivity() {
    var gameView: GameView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        println(point.y)
        gameView = GameView(this, point.x, point.y)
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView!!.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView!!.resume()
    }
}