package com.vervyle.lab1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : ViewModel() {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private var timeStamp: Long = 0L
    private val eventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event == null) return
            val omegaZ = event.values[2]
            var deltaZ = 0F
            if (timeStamp != 0L) {
                // Вычисляем дельту времени (в секундах)
                val deltaTime: Float = (event.timestamp - timeStamp) * 1.0f / 1000000000.0f

                // Интегрируем угловую скорость для получения угла поворота
                deltaZ += omegaZ * deltaTime
            }

            timeStamp = event.timestamp
            angle.value += Math.toDegrees(deltaZ.toDouble()).toFloat()
            Log.d(TAG, "onSensorChanged: new angle ${angle.value}")
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d(TAG, "onAccuracyChanged: accuracy changed to $accuracy")
        }
    }

    val angle = MutableStateFlow(0f)

    fun onResume() {
        sensorManager.registerListener(
            eventListener,
            sensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    fun onPause() {
        sensorManager.unregisterListener(
            eventListener
        )
    }

    companion object {
        private const val TAG = "DebugTag"
    }
}