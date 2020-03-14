package com.example.shakeit

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.sensor_reading.*
import java.text.SimpleDateFormat
import java.util.*

class SensorReading : AppCompatActivity(), SensorEventListener {
    // declares a private member of this class, sensorManager (type is SensorManager)
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope : Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_reading)

        playGame()

        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // get accelerometer sensor and register listener
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
            this.accelerometer = it
        }
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // get gyroscope sensor and register
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.let {
            this.gyroscope = it
        }
        gyroscope?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // set tap onclick listener
        txtTap.setOnClickListener {
            Toast.makeText(this, "Tapped", Toast.LENGTH_SHORT).show()
            playGame()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_GYROSCOPE -> {
                // set the display text of the text view
                txtGyrReadX.text = "Gyroscope X: " + event.values[0].toString() + "rad/s^2"
                txtGyrReadY.text = "Gyroscope Y: " + event.values[1].toString() + "rad/s^2"
                txtGyrReadZ.text = "Gyroscope Z: " + event.values[2].toString() + "rad/s^2"
            }

            Sensor.TYPE_ACCELEROMETER -> {
                // set the display text of the text view
                txtAccReadX.text = "Accelerometer X: " + event.values[0].toString() + "m/s^2"
                txtAccReadY.text = "Accelerometer Y: " + event.values[1].toString() + "m/s^2"
                txtAccReadZ.text = "Accelerometer Z: " + event.values[2].toString() + "m/s^2"
            }
        }
    }

    private fun playGame() {
        val currentTime: String = SimpleDateFormat("ss", Locale.getDefault()).format(Date())
        Log.d("Timer:", currentTime)
    }

}