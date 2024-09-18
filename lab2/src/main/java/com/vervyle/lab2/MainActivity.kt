package com.vervyle.lab2

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.vervyle.lab2.ui.MapApp
import com.vervyle.lab2.ui.theme.UUSTMobileDevLWTheme
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "lab2"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        MapKitFactory.setApiKey(BuildConfig.yandex_api_key)
        MapKitFactory.initialize(this)
        enableEdgeToEdge()
        setContent {
            UUSTMobileDevLWTheme {
                Scaffold { innerPadding ->
                    val location by viewModel.location.collectAsState()
                    val shouldKeepAlive by viewModel.shouldKeepAlive.collectAsState()
                    MapApp(
                        shouldKeepAlive = shouldKeepAlive,
                        location = location,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        viewModel.shouldKeepAlive.value = true
    }

    override fun onStop() {
        viewModel.shouldKeepAlive.value = false
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}