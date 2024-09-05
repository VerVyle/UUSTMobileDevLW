package com.vervyle.lab2.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vervyle.lab2.MapViewModel

@Composable
fun MapApp(
    viewModel: MapViewModel,
    modifier: Modifier = Modifier,
) {
    val shouldKeepAlive by viewModel.shouldKeepAlive.collectAsState()

    YandexMap(
        shouldKeepAlive,
        modifier
    )
}