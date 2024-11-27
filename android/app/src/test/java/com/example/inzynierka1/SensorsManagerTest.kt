package com.example.inzynierka1
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Test

class SensorsManagerTest {

    init {

        mockkStatic(Sensor::class)
        every { Sensor.TYPE_ACCELEROMETER } returns 1


    }

    @Test
    fun testAccelerometerEvent(){
    }
}