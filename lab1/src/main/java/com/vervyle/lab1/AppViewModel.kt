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
    private val eventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            val rotationMatrix = FloatArray(16)
            SensorManager.getRotationMatrixFromVector(
                rotationMatrix,
                event?.values ?: throw RuntimeException("???")
            )
            val remappedRotationMatrix = FloatArray(16)
            SensorManager.remapCoordinateSystem(
                rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                remappedRotationMatrix
            )
            val orientations = FloatArray(3)
            SensorManager.getOrientation(remappedRotationMatrix, orientations)
            orientations.map { Math.toDegrees(it.toDouble()) }
            angle.value = orientations[2]
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