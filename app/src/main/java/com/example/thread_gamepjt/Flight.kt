package com.example.thread_gamepjt

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log

class Flight(private var gameView: GameView,screenY:Int,res:Resources?) {
    var toShoot = 0
    var isGoingUp = false
    var TAG = "FLIGHT"
    var x = 0
    var y = 0
    var width = 0
    var height = 0
    var flight1: Bitmap
    var flight2: Bitmap
    var shoot1:Bitmap
    var shoot2:Bitmap
    var shoot3:Bitmap
    var shoot4:Bitmap
    var shoot5:Bitmap
    var shootCount = 0
    var dead:Bitmap

    var wingCounter = 0

    init{
        flight1 = BitmapFactory.decodeResource(res,R.drawable.fly1)
        flight2 = BitmapFactory.decodeResource(res,R.drawable.fly2)

        width = flight1.width
        height = flight1.height
        width /= 4
        height /= 4
        width  *= (GameView.screenRatioX).toInt()
        height *= (GameView.screenRatioY).toInt()
        flight1 = Bitmap.createScaledBitmap(flight1,width,height,false)
        flight2 = Bitmap.createScaledBitmap(flight2,width,height,false)
        shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot1)
        shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot2)
        shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot3)
        shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot4)
        shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot5)

        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false)
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false)
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false)
        shoot4 = Bitmap.createScaledBitmap(shoot4,width,height,false)
        shoot5 = Bitmap.createScaledBitmap(shoot5,width,height,false)

        dead = BitmapFactory.decodeResource(res,R.drawable.dead)
        dead = Bitmap.createScaledBitmap(dead,width,height,false)
        y = screenY /2
        x = (64 * GameView.screenRatioX).toInt()
    }

    fun getFlight():Bitmap{
        if(toShoot != 0){
            if(shootCount == 1){
                shootCount ++
                return shoot1
            }
            if(shootCount == 2){
                shootCount ++
                return shoot2
            }
            if(shootCount == 3){
                shootCount ++
                return shoot3
            }
            if(shootCount == 4){
                shootCount ++
                return shoot4
            }
            shootCount = 1
            toShoot --
            gameView.newBullet()
            return shoot5

        }

        if(wingCounter == 0){
            wingCounter++
            return flight1
        }
        wingCounter --
        return flight2
    }
    fun getCollisionShape(): Rect {
        Log.d(TAG,"비행중 충돌 발생 ")
        return Rect(x,y,x+width,y+height)
    }


}