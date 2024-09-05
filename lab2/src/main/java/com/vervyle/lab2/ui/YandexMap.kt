package com.vervyle.lab2.ui

import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.vervyle.lab2.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
fun YandexMap(
    shouldKeepAlive: Boolean,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val view: View = LayoutInflater
                .from(context)
                .inflate(R.layout.activity_main, null, false)
            val mapView = view.findViewById<MapView>(R.id.mapview)
            mapView.mapWindow.map.move(
                CameraPosition(
                    Point(54.725208, 55.941028),
                    11f,
                    0f,
                    0f
                ),
                Animation(Animation.Type.SMOOTH, 4f)
            ) { }
            mapView
        },
        update = { view: MapView ->
            if (shouldKeepAlive) view.onStart() else view.onStop()
        }
    )
}