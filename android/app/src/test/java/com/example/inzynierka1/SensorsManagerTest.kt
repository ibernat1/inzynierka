import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import com.example.inzynierka1.SensorsManager
import io.mockk.*
import org.junit.Before
import org.junit.Test

class SensorsManagerTest {

    private lateinit var context: Context
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var sensorsManager: SensorsManager

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        context = mockk()
        sensorManager = mockk()
        accelerometer = mockk()
        sensorsManager = spyk(SensorsManager(context))

        every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager

        every { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) } returns accelerometer

        every {
            sensorManager.registerListener(
                sensorsManager,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME
            )
        } returns true
    }

    @Test
    fun `test setUpSensors registers accelerometer`() {
        sensorsManager.setUpSensors()

        verify { context.getSystemService(Context.SENSOR_SERVICE) }
        verify {
            sensorManager.registerListener(
                sensorsManager,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }
}
