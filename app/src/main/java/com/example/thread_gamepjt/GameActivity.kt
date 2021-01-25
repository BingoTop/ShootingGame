package com.example.thread_gamepjt

import android.annotation.SuppressLint
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.setMargins
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    var gameView: GameView? = null
    var game:FrameLayout? = null
    var gameButtons:RelativeLayout? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        gameView = GameView(this, point.x, point.y)
        game = FrameLayout(this)
        gameButtons = RelativeLayout(this)
        val bulletButton = ImageButton(this)
        bulletButton.id = 123
        bulletButton.setOnClickListener {
            gameView!!.newBullet()
        }
        val ballImg = resources.getDrawable(R.drawable.energy_ball_icon)
        bulletButton.setImageDrawable(ballImg)
        val btn = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT)
        btn.width = 150
        btn.height = 150
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT)
        gameButtons?.layoutParams = params
        gameButtons?.addView(bulletButton)
        btn.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE)
        btn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE)
        btn.setMargins(15,15,15,15)
        bulletButton.layoutParams = btn
        game?.addView(gameView)
        game?.addView(gameButtons)
        setContentView(game)
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