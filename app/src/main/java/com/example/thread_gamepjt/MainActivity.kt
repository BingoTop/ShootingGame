package com.example.thread_gamepjt

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private var isMute = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        play.setOnClickListener {
            val intent = Intent(this@MainActivity,GameActivity::class.java)
            startActivity(intent)
        }
        val prefs = getSharedPreferences("game", MODE_PRIVATE)
        isMute = prefs.getBoolean("isMute", false)
        val volumeCtrl = findViewById<ImageView>(R.id.volumeCtrl)
        startService(Intent(this,MyService::class.java))
        if (isMute){
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24)
        } else {
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24)
        }
        highScoreTxt.text = "HighScore: " + prefs.getInt("highscore", 0)
        volumeCtrl.setOnClickListener {
            isMute = !isMute
            if (isMute){
                volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24)

            } else {
                volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24)
            }
            val editor = prefs.edit()
            editor.putBoolean("isMute", isMute)
            editor.apply()
        }

    }
    override fun onPause() {
        super.onPause()
        stopService(Intent(this,MyService::class.java))
    }
    override fun onResume() {
        super.onResume()
    }
}