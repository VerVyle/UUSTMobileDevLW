package com.vervyle.lab2

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vervyle.lab2.model.LocationClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationClient: LocationClient
) : ViewModel() {

    var shouldKeepAlive = MutableStateFlow(true)

    val location = locationClient
        .getLocationUpdates(10000L)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            Location(null)
        )
}