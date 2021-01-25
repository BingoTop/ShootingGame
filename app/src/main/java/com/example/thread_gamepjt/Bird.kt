package com.example.thread_gamepjt

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log

class Bird(res:Resources) {
    var TAG = "버드 "
    var wasShot: Boolean = false
    var speed: Int = 20
    var x = 0
    var y= 0
    var width:Int
    var height:Int
    var bird1:Bitmap
    var bird2:Bitmap
    var bird3:Bitmap
    var bird4:Bitmap
    var bird5:Bitmap
    var bird6:Bitmap

    var birdCount = 1

    init{
//        bird1 = BitmapFactory.decodeResource(res,R.drawable.bird1)
//        bird2 = BitmapFactory.decodeResource(res,R.drawable.bird2)
//        bird3 = BitmapFactory.decodeResource(res,R.drawable.bird3)
//        bird4 = BitmapFactory.decodeResource(res,R.drawable.bird4)

        bird1 = BitmapFactory.decodeResource(res,R.drawable.blackdragon1)
        bird2 = BitmapFactory.decodeResource(res,R.drawable.blackdragon2)
        bird3 = BitmapFactory.decodeResource(res,R.drawable.blackdragon3)
        bird4 = BitmapFactory.decodeResource(res,R.drawable.blackdragon4)
        bird5 = BitmapFactory.decodeResource(res,R.drawable.blackdragon5)
        bird6 = BitmapFactory.decodeResource(res,R.drawable.blackdragon6)


        width = bird1.width
        height = bird1.height

        width /= 2
        height /= 2

        width = (width * GameView.screenRatioX).toInt()
        height = (height* GameView.screenRatioY).toInt()
        bird1 = Bitmap.createScaledBitmap(bird1,width,height,false)
        bird2 = Bitmap.createScaledBitmap(bird2,width,height,false)
        bird3 = Bitmap.createScaledBitmap(bird3,width,height,false)
        bird4 = Bitmap.createScaledBitmap(bird4,width,height,false)
        bird5 = Bitmap.createScaledBitmap(bird5,width,height,false)
        bird6 = Bitmap.createScaledBitmap(bird6,width,height,false)

        y = -height

    }

    fun getBird():Bitmap{
        when(birdCount){
            1-> {
                birdCount++
                return bird1
            }
            2->{
                birdCount++
                return bird2
            }
            3-> {
                birdCount++
                return bird3
            }
            4->{
                birdCount++
                return bird4
            }
            5->{
                birdCount++
                return bird5
            }

        }
        birdCount = 1
        return bird6
    }

    fun getCollisionShape(): Rect {
        return Rect(x,y,x+width,y+height)
    }

}