package com.vervyle.lab2.ui

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.vervyle.lab2.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CircleMapObject
import com.yandex.mapkit.mapview.MapView

@Composable
fun YandexMap(
    shouldKeepAlive: Boolean,
    location: Location,
    modifier: Modifier = Modifier
) {
    var lastCircle: CircleMapObject? by remember {
        mutableStateOf(
            null
        )
    }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val view: View = LayoutInflater
                .from(context)
                .inflate(R.layout.map_view, null, false)
            val mapView = view.findViewById<MapView>(R.id.mapview)
            mapView.mapWindow.map.move(
                CameraPosition(
                    Point(location.latitude, location.longitude),
                    11f,
                    0f,
                    0f
                ),
                Animation(Animation.Type.SMOOTH, 1f)
            ) { }
            mapView
        },
        update = { view: MapView ->
            if (shouldKeepAlive) view.onStart() else view.onStop()
            view.mapWindow.map.move(
                CameraPosition(
                    Point(location.latitude, location.longitude),
                    17f,
                    0f,
                    0f
                ),
                Animation(Animation.Type.SMOOTH, 4f)
            ) { }
            lastCircle?.let {
                view.mapWindow.map.mapObjects.remove(it)
            }
            lastCircle = view.mapWindow.map.mapObjects.addCircle(
                Circle(
                    Point(location.latitude, location.longitude),
                    4f
                )
            )
        }
    )
}