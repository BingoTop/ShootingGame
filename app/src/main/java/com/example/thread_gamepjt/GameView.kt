package com.example.thread_gamepjt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.concurrent.timerTask


class GameView(context:Context,var screenX:Int,var screenY:Int):SurfaceView(context), Runnable {
    var isGameOver = false
    var TAG = "게임뷰 - "
    var thread1:Thread? = null
    var isPlaying:Boolean=false
    var background1:Background
    var background2:Background
    var paint:Paint
    var flight:Flight?= null
    val bullets:MutableList<Bullet>
    val birds:Array<Bird?>
    var random:Random
    var score:Int

    init{
        background1 = Background(screenX,screenY,resources)
        background2 = Background(screenX,screenY,resources)
        Log.d(TAG,"백그라운드 객체 생성 x: ${screenX}, y: ${screenY}")
        Log.d(TAG,"flight 객체 생성 y: ${screenY}, ${resources}")
        background2.x = screenX
        paint = Paint()
        screenRatioX = 1920f / screenX
        screenRatioY = 1080f / screenY
        flight = Flight(this,screenY,resources)
        bullets = ArrayList()
        paint.setTextSize(128F)
        paint.setColor(Color.BLACK)
        birds = arrayOfNulls(4)
        score = 0
        for(i in 0..3){
            val bird = Bird(resources)
            birds[i] = bird
        }
        random = Random()

    }

    companion object{
        var screenRatioX : Float = 0.0f
        var screenRatioY : Float = 0.0f
    }


    override fun run() {
        while(isPlaying){
            update()
            draw()
            sleep()
        }
    }

    fun update(){
        background1.x =(background1.x -3 * screenRatioX).toInt()
        background2.x =(background2.x -3 * screenRatioX).toInt()
        if (background1.x + background1.background.width < 0) {
            background1.x = screenX
        }
        if (background2.x + background2.background.width < 0) {
            background2.x = screenX
        }

        if(flight!!.isGoingUp){
            flight!!.y -= 30 * screenRatioY.toInt()
        }else{
            flight!!.y += 30 * screenRatioY.toInt()
        }
        if(flight!!.y < 0){
            flight!!.y = 0
        }
        if(flight!!.y > screenY - flight!!.height){
            flight!!.y = screenY - flight!!.height
        }
        val trash:ArrayList<Bullet> = ArrayList()
        bullets.forEach { bullet ->
            if(bullet.x > screenX){
                trash.add(bullet)
            }
            bullet.x += 50 * screenRatioX.toInt()
            birds.forEach {
                // 몬스터가 공격에 맞았을 경우
                if(Rect.intersects(it!!.getCollisionShape(),bullet.getCollisionShape())){
                        it.x = - 500
                        bullet.x = screenX + 500 // 스크린 밖으로 그리고 trash 리스트 add
                        it.wasShot = true
                        score++
                    }

            }

        }
        trash.forEach {
            bullets.remove(it)
        }
        birds.forEach {
            it!!.x -= it!!.speed
            Log.d(TAG,"it.x + it.width: ${it!!.x + it.width}")
            if(it.x + it.width < 0){
                Log.d(TAG,"버드 위치가 스크린 밖에 있을 때")
//                if(!it.wasShot){
//                    isGameOver = true
//                    return
//                }
                val bound = 10 * screenRatioX.toInt()
                it.speed = random.nextInt(bound)
                if(it.speed < 10 * screenRatioX){
                   it.speed = 10 * screenRatioX.toInt()
                }
                it.x = screenX
                it.y = random.nextInt(screenY -  it.height)
                it.wasShot = false
            }
//            if(Rect.intersects(it.getCollisionShape(),flight!!.getCollisionShape())){
//                isGameOver = true
//                return
//            }
        }
        Log.d(TAG,"UPDATE 끝")
    }
    fun draw(){
        if(holder.surface.isValid()){
            val canvas: Canvas = holder.lockCanvas()
            canvas.drawBitmap(background1.background,background1.x.toFloat(),background1.y.toFloat(),paint)
            canvas.drawBitmap(background2.background,background2.x.toFloat(),background2.y.toFloat(),paint)
            canvas.drawBitmap(flight!!.getFlight(),flight!!.x.toFloat(),flight!!.y.toFloat(),paint)
            canvas.drawText("${score}",screenX/2f,164f,paint)
            birds.forEach{
                canvas.drawBitmap(it!!.getBird(),it.x.toFloat(),it.y.toFloat(),paint)
            }
//            if(isGameOver){
//                isPlaying = false
//                canvas.drawBitmap(flight!!.dead,flight!!.x.toFloat(),flight!!.y.toFloat(),paint)
//                holder.unlockCanvasAndPost(canvas)
//                return
//            }

            bullets.forEach{
                bullet ->
                canvas.drawBitmap(bullet.bullet,bullet.x.toFloat(),bullet.y.toFloat(),paint)

            }
            holder.unlockCanvasAndPost(canvas)
        }
        Log.d(TAG,"draw 끝")
    }
    fun sleep(){
        try {
//            Log.d(TAG,"스레드 슬립")
            Thread.sleep(25)
        }catch(e:InterruptedException) {
            e.printStackTrace()
        }
    }


    fun resume(){
        Log.d(TAG,"레슘 함수 실행")
        thread1 = Thread(this)
        isPlaying = true
        thread1!!.start()
    }

    fun pause(){
        try {
            Log.d(TAG,"온 포즈 함수 실행")
            isPlaying = false
            thread1!!.join()
        }catch (e:InterruptedException){
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                if(event.getX() < screenX/2){
                    flight?.isGoingUp = true
                }
            }
            MotionEvent.ACTION_UP->{
                flight?.isGoingUp = false
                if(event.getX() > screenX / 2){
                    flight!!.toShoot++
                }
            }
        }
        return true
    }

    fun newBullet() {
        var bullet = Bullet(resources)
        bullet.x = flight!!.x +flight!!.width
        bullet.y = flight!!.y+ (flight!!.height/2)
        bullets.add(bullet)
    }
}