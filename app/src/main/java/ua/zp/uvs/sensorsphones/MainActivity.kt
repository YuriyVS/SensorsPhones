package ua.zp.uvs.sensorsphones

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorPrivacyManager.Sensors
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ua.zp.uvs.sensorsphones.databinding.ActivityMainBinding

class MainActivity : Activity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var pressure: Sensor? = null
    private var mLight: Sensor? = null
    private var aMbientTemperature: Sensor? = null
    private var mTemperature: Sensor? = null
    private var mSensor: Sensor? = null
    lateinit var textInfo: TextView
    lateinit var textListSensors: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        textInfo = binding.textInfo
        textListSensors = binding.textListSensors
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        aMbientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        mTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE)
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        var temp = ""
        deviceSensors.forEach {
            temp += "\n" +
                    "Sensor: " + it.name + "\n"
        }
        textListSensors.text = temp
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val megure  = event?.values?.get(0)
        textInfo.text = megure.toString()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        mSensor?.also { it ->
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}