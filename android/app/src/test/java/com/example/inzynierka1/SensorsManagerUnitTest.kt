package com.example.inzynierka1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.inzynierka1.viewmodels.MainViewModel
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class SensorsManagerUnitTest {

    private lateinit var context: Context
    private lateinit var sensorManager: SensorManager
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        context = mockk()
        sensorManager = mockk(relaxed = true)
        mainViewModel = mockk(relaxed = true)
        mockkStatic("android.content.Context")
        every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test setUpSensorStuff`() {
        val sensorsManager = SensorsManager(context)
        sensorsManager.setMainViewModel(mainViewModel)
        sensorsManager.setUpSensors()
        val mockListener: SensorEventListener = mockk()

        verify(exactly = 1) {
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(mockListener, any(), any())
        }

    }

    @Test
    fun `test onSensorChanged`() {
        val sensorsManager = SensorsManager(context)
        val sensorEvent = mockk<SensorEvent>()
        every { sensorEvent.sensor } returns mockk {
            every { type } returns Sensor.TYPE_ACCELEROMETER
        }
        every { sensorEvent.values } returns floatArrayOf(1f, 2f, 3f)

        sensorsManager.onSensorChanged(sensorEvent)

        assert(sensorsManager.getValues().size == 1)
        assert(sensorsManager.getValues()[0] == "1.0,2.0,3.0")
    }

    @Test
    fun `test nullValues`() {
        val sensorsManager = SensorsManager(context)
        sensorsManager.nullValues()

        assert(sensorsManager.getValues().isEmpty())
    }

    @Test
    fun `test getValue`() {
        val sensorsManager = SensorsManager(context)
        sensorsManager.onSensorChanged(mockk {
            every { sensor } returns mockk {
                every { type } returns Sensor.TYPE_ACCELEROMETER
            }
            every { values } returns floatArrayOf(1f, 2f, 3f)
        })

        assert(sensorsManager.getValue() == "1.0,2.0,3.0")
    }
}
