package com.example.locationpermision

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue with accessing the location.
                getCurrentLocation(textView)
            } else {
                // Permission is denied. Show a message to the user.
                Toast.makeText(
                    this, "The permission is denied, " +
                            "so can not access the location.", Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textView = findViewById(R.id.tvPosition)
        val button: Button = findViewById(R.id.btnCurrentPosition)
        button.setOnClickListener {
            getCurrentLocation(textView)
        }
    }

    private fun getCurrentLocation(textView: TextView) {
        // Get the current location from the location manager.
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Check if the app already has the permission. ACCESS_COARSE_LOCATION | ACCESS_FINE_LOCATION
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            // Get the current position from the location.
            location?.let {
                val currentPosition = Helper.getCurrentLocation(it)
                textView.text = buildString {
                    append("latitude: ")
                    append(currentPosition.latitude)
                    append("\n")
                    append("longitude: ")
                    append(currentPosition.longitude)
                }
            }
        } else {
            // Request the permission. ACCESS_COARSE_LOCATION | ACCESS_FINE_LOCATION
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
