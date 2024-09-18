package com.vervyle.lab2.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.vervyle.lab2.model.LocationClient
import com.vervyle.lab2.model.util.DefaultLocationClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationClientProvider {

    @Provides
    fun providesLocationClient(
        @ApplicationContext context: Context
    ): LocationClient {
        return DefaultLocationClient(
            context,
            LocationServices.getFusedLocationProviderClient(context)
        )
    }
}