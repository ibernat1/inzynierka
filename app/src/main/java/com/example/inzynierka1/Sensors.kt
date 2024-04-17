package com.example.inzynierka1

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.getSystemService
import com.example.inzynierka1.viewmodels.MainViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private  const val TAG = "SENSORS"
interface SensorsListener {
    fun onMessageChanged(message: String)
}

class Sensors @Inject constructor(@ApplicationContext private val context: Context): SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private var message: String by mutableStateOf("brak danych")
    private val values = mutableListOf<String>()
//    private var listener: SensorsListener? = null

    private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

//    fun setListener(listener: SensorsListener) {
//        this.listener = listener
//    }

    fun setUpSensorStuff() {
        // Create the sensor manager
        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        Log.d(TAG, "obtained sensorManager")
        // Specify the sensor you want to listen to
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST
//                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        Log.d(TAG, "regsitered the listener")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            message = "X: ${event.values[0]}, Y: ${event.values[1]}, Z: ${event.values[2]}"
//            Log.d(
//                "Sensors",message
//            )
            values.add(message)
            Log.d("Sensors",values.size.toString())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }



    fun getValues(): List<String> {
        Log.d("Sensors", values.toString())
        return values
    }

    fun nullValues(){
        values.clear()
        values.add("Początek")
    }

    fun getValue() : String {
        return message
    }
}