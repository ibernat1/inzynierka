import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import com.example.inzynierka1.SensorsManager
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class SensorsManagerTest {

    private lateinit var context: Context
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var sensorsManager: SensorsManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
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

    @Test
    fun `test onSensorChanged updates message and lists correctly`() {
        val mockSensor = mockk<Sensor>()
        every { mockSensor.type } returns Sensor.TYPE_ACCELEROMETER

        val event = createMockSensorEvent(
            mockSensor,
            floatArrayOf(1.0f, 2.0f, 3.0f),
            accuracy = 3,
            timestamp = 5_000_000_000L
        )
        sensorsManager.onSensorChanged(event)


        // Sprawdzenie, czy message zostało zaktualizowane
        assertEquals("1.0,2.0,3.0", sensorsManager.message)

        // Sprawdzenie, czy lista reading została zaktualizowana
        assertEquals(listOf(1.0f, 2.0f, 3.0f), sensorsManager.reading)

        // Sprawdzenie, czy values zawiera odpowiednią wartość
        assertTrue(sensorsManager.values.contains("1.0,2.0,3.0"))

        // Sprawdzenie, czy valuesFloat zawiera odpowiednią listę
        assertTrue(sensorsManager.valuesFloat.contains(listOf(1.0f, 2.0f, 3.0f)))
    }


    @Test
    fun `test getValuesList returns correct values`() {
        // Przygotowanie: dodanie wartości do listy values
        sensorsManager.values.add("value1")
        sensorsManager.values.add("value2")
        sensorsManager.values.add("value3")

        // Wywołanie metody
        val result = sensorsManager.getValuesList()

        // Sprawdzenie, czy wynik to lista, która zawiera te same wartości
        assertEquals(listOf("value1", "value2", "value3"), result)
    }

    @Test
    fun `test getValuesList logs correct values`() {
        // Przygotowanie: dodanie wartości do listy values
        sensorsManager.values.add("value1")
        sensorsManager.values.add("value2")

        // Wywołanie metody
        sensorsManager.getValuesList()

        // Weryfikacja, czy Log.d został wywołany z odpowiednim komunikatem
        verify { Log.d("Sensors", "[value1, value2]") }
    }

    @Test
    fun `test nullValues clears the values list`() {
        // Przygotowanie: dodanie wartości do listy values
        sensorsManager.values.add("value1")
        sensorsManager.values.add("value2")

        // Sprawdzenie przed wywołaniem nullValues, czy lista zawiera elementy
        assertTrue(sensorsManager.values.isNotEmpty())

        // Wywołanie metody
        sensorsManager.nullValues()

        // Sprawdzenie po wywołaniu nullValues, czy lista jest pusta
        assertTrue(sensorsManager.values.isEmpty())
    }

    @Test
    fun `test getValue returns correct message`() {
        // Przygotowanie: ustawienie wartości zmiennej message
        val expectedMessage = "Test message"
        sensorsManager.message = expectedMessage

        // Wywołanie metody getValue
        val result = sensorsManager.getValue()

        // Sprawdzenie, czy wynik jest zgodny z oczekiwaną wartością
        assertEquals(expectedMessage, result)
    }

    fun createMockSensorEvent(
        sensor: Sensor,
        values: FloatArray,
        accuracy: Int,
        timestamp: Long
    ): SensorEvent {
        val constructor = SensorEvent::class.java.getDeclaredConstructor(Int::class.java)
        constructor.isAccessible = true
        val sensorEvent = constructor.newInstance(values.size)

        SensorEvent::class.java.getDeclaredField("sensor").apply {
            isAccessible = true
            set(sensorEvent, sensor)
        }
        SensorEvent::class.java.getDeclaredField("values").apply {
            isAccessible = true
            set(sensorEvent, values)
        }
        SensorEvent::class.java.getDeclaredField("accuracy").apply {
            isAccessible = true
            set(sensorEvent, accuracy)
        }
        SensorEvent::class.java.getDeclaredField("timestamp").apply {
            isAccessible = true
            set(sensorEvent, timestamp)
        }

        return sensorEvent
    }

}
