// checking compatibility
package com.example.shakeit

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.absoluteValue
import kotlin.random.Random
import android.hardware.SensorManager as SensorManager

class MainActivity : AppCompatActivity(), SensorEventListener {

    // declares a private member of this class, sensorManager (type is SensorManager)
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var shakeNum: Int = 0
    private val interval: Long = 2000000000     // unit: nanoseconds
    private var lastTime: Long = 0
    private var randomNum: Int = 0

    // constructor of the activity class, sensors initialized
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get sensor manager
        Log.d("initialization:", "onCreate() called")
        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // setup the text view and clicking behavior
//        shakeTxt.text = "Shake it!"
//        shakeTxt.setOnClickListener {
//            Log.d("shake_button:", "shake button clicked")
//
//            // generate a new random number
//            randomNum = Random.nextInt(1, 6)
//            shakeTxt.text = randomNum.toString()
////            Log.d("shake_button:", "shake button clicked2")
//        }

        // get accelerometer sensor and register listener
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            this.accelerometer = it
        }


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    // implement sensor event listener
    override fun onSensorChanged(event: SensorEvent?) {

        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                if (event.values[0].absoluteValue > 2 && (event.timestamp - lastTime) > interval) {
                    lastTime = event.timestamp
                    shakeNum += 1
                    shakeNumTxt.text = shakeNum.toString() + "/10"

                    val rn = Random.nextInt(1, 5)
                    displayInstruction(rn)

                    if (shakeNum == 10) {
                        shakeNum = 0
                        shakeNumTxt.text = "0/10"
                    }
                }
            }
        }
    }


    // sensor listener registration
    override fun onResume() {
        super.onResume()
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // instruction display function
    private fun displayInstruction(rd: Int) {
        when (rd) {
            1 -> { shakeTxt.text = "Shake it!" }

            2 -> { shakeTxt.text = "Spin it" }

            3 -> { shakeTxt.text = "Flip it"}

            4 -> { shakeTxt.text = "Tap it"}
        }
    }

}
