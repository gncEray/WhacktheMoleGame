package com.chucky.catchthemolegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private var score = 0
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable{ } //for Countdown
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageArray.add(findViewById(R.id.imageView))
        imageArray.add(findViewById(R.id.imageView2))
        imageArray.add(findViewById(R.id.imageView3))
        imageArray.add(findViewById(R.id.imageView4))
        imageArray.add(findViewById(R.id.imageView5))
        imageArray.add(findViewById(R.id.imageView6))
        imageArray.add(findViewById(R.id.imageView7))
        imageArray.add(findViewById(R.id.imageView8))
        imageArray.add(findViewById(R.id.imageView9))

        hideImages()

        object : CountDownTimer(20000, 1000) {

            override fun onFinish() {
                val tw : TextView = findViewById(R.id.timeText)
                tw.text = "Time : 0"

                handler.removeCallbacks(runnable)

                for(image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("GAME OVER")
                alert.setMessage("PLAY AGAIN?")

                alert.setPositiveButton("YES") {dialog, which ->
                    val intent = intent //intent diye atınca yeniden aynı aktiviteyi başlatıyor
                    finish() //finish the activity
                    startActivity(intent) //intente atadığın aktiviteyi başlat(yeniden)
                }

                alert.setNegativeButton("NO") {dialog, which ->
                    Toast.makeText(this@MainActivity, "GAME OVER", Toast.LENGTH_LONG).show() //only toast message is enough
                }

                alert.show()

            }

            override fun onTick(millisUntilFinished: Long) {
                val tw : TextView = findViewById(R.id.timeText)
                tw.text = "Time: ${millisUntilFinished/1000}"
            }

        }.start()

    }


    private fun hideImages() {

        //making all images invisible, and then one image is visible with Runnable
        runnable = Runnable {
            //while runnable
            //make all images invisible
            for(image in imageArray) {
                image.visibility = View.INVISIBLE
            }

            //generating a random number to use as index number to make visible one image
            val randomIndex = (0..9).random()
            imageArray[randomIndex].visibility = View.VISIBLE

            handler.postDelayed(runnable, 750) //ilki runnable ismini(değişken old.) ikincisi her kaç saniyede bir işlemin yapılacağını gösteriyor
        } //fun run ends
        //Runnable ends

        handler.post(runnable) //Idk why do we use that

    } //hideImages ends

    //bu komut tüm resimlerde tanımlı layout kısmında o yüzden tıklanıldığında işleme giriyor
    fun increaseScore() {
        score = score + 1
        val scoreText: TextView = findViewById(R.id.scoreText)
        scoreText.text = "Score: ${score}"
    }

}

