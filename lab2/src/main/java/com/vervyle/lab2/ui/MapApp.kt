package com.vervyle.lab2.ui

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vervyle.lab2.MapViewModel

@Composable
fun MapApp(
    shouldKeepAlive: Boolean,
    location: Location,
    modifier: Modifier = Modifier,
) {

    YandexMap(
        shouldKeepAlive,
        location,
        modifier
    )
}