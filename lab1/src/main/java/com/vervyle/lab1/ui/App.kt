package com.vervyle.lab1.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.vervyle.lab1.AppViewModel

@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = hiltViewModel()
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val angle by viewModel.angle.collectAsState()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .onGloballyPositioned { newCoordinates ->
                size = newCoordinates.size
            }
    ) {
        rotate(angle) {
            drawLine(
                Color.DarkGray,
                Offset(20f, size.height.toFloat() / 2),
                Offset(size.width.toFloat() - 20f, size.height.toFloat() / 2),
                12f
            )
        }
    }
}