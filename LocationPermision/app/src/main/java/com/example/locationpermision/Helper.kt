package com.example.locationpermision

import android.location.Location

object Helper {
    fun getCurrentLocation(location: Location): Location {
        // Get the current latitude and longitude.
        val latitude = location.latitude
        val longitude = location.longitude

        // Create a new Location object with the current latitude and longitude.
        val currentLocation = Location("")
        currentLocation.latitude = latitude
        currentLocation.longitude = longitude

        return currentLocation
    }
}
