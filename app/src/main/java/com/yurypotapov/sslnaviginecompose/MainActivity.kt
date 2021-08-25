package com.yurypotapov.sslnaviginecompose

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.navigine.naviginesdk.Location
import com.navigine.naviginesdk.NavigationThread
import com.navigine.naviginesdk.NavigineSDK
import com.yurypotapov.sslnaviginecompose.ui.theme.SSLNavigineComposeTheme
import kotlinx.coroutines.coroutineScope
import java.lang.Boolean
import java.util.*
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    private lateinit var mLocation: Location;

    private var lastKnowLocation: String = "Empty Location";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initializing Navigation library (USER_HASH is your personal security key)
        if (!NavigineSDK.initialize(getApplicationContext(), "FD45-C20F-3C16-F8CA", null))
            Toast.makeText(
                this, "Unable to initialize Navigation library!",
                Toast.LENGTH_LONG
            ).show();

        setContent {
            SSLNavigineComposeTheme {
                var latitude by remember { mutableStateOf("") }
                var longitude by remember { mutableStateOf("") }
                var altitude by remember { mutableStateOf("") }
                var gpsSolution by remember {mutableStateOf("") }
                var speed by remember {mutableStateOf("") }
                var accuracy by remember {mutableStateOf("") }
                var bearing by remember {mutableStateOf("") }

                Surface(color = MaterialTheme.colors.background) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = "Latitude:", fontWeight = FontWeight.Bold)
                        Text(text = latitude)
                        Text(text = "Longitude:", fontWeight = FontWeight.Bold)
                        Text(text = longitude)
                        Text(text = "Altitude:", fontWeight = FontWeight.Bold)
                        Text(text = altitude)
                        Text(text = "Device ID:", fontWeight = FontWeight.Bold)
                        Text(text = NavigineSDK.getDeviceId())
                        Text(text = "Device Model:", fontWeight = FontWeight.Bold)
                        Text(text = NavigineSDK.getDeviceModel())
                        Text(text = "Device Wifi Address:", fontWeight = FontWeight.Bold)
                        Text(text = NavigineSDK.getWifiAddress())
                        Text(text = "Device Wifi Name:", fontWeight = FontWeight.Bold)
                        Text(text = NavigineSDK.getWifiNetworkBSSID() + "/" + NavigineSDK.getWifiNetworkSSID())
                        Text(text = "Device Bluetooth Address:", fontWeight = FontWeight.Bold)
                        Text(text = NavigineSDK.getBluetoothAddress())
                        Text(text = "Device Battery Level:", fontWeight = FontWeight.Bold)
                        Text(text = NavigineSDK.getBatteryLevel()?.toString())
                        Text(text = "Device Coordinates Provider:", fontWeight = FontWeight.Bold)
                        Text(text = gpsSolution)
                        Text(text = "Device Speed:", fontWeight = FontWeight.Bold)
                        Text(text = speed)
                        Text(text = "Device Accuracy:", fontWeight = FontWeight.Bold)
                        Text(text = accuracy)
                        Text(text = "Device Bearing:", fontWeight = FontWeight.Bold)
                        Text(text = bearing)

                        Button(onClick = {
                            val location = updateState();
                            latitude = location?.latitude?.toString()
                            longitude = location?.longitude?.toString()
                            altitude = location?.altitude?.toString()
                            gpsSolution = location?.provider
                            speed = location?.speed.toString();
                            accuracy = location?.accuracy.toString();
                            bearing = location?.bearing.toString()

                        }) {
                            Text(text = "Update Device Location", modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }

    private fun updateState(): android.location.Location {

        NavigineSDK.loadLocation(91226, 30)

        val navigation = NavigineSDK.getNavigation()
        if (navigation != null)
            navigation.mode = NavigationThread.MODE_SCAN
        return NavigineSDK.getLastKnownLocation();
    }


    private fun updateNavigationResults() {
        val navigation = NavigineSDK.getNavigation()
        if (navigation !== null) {
            val info = navigation.deviceInfo // Get device position
            if (!info.isValid) {
                // Navigation results are not available
                // Try to find out the problem using navigation error code
                when (info.errorCode) {
                    4 -> Log.d(
                        "DEBUG NAVIGINE", "You are out of navigation zone! " +
                                "Check that your bluetooth is enabled!"
                    )
                    7 -> Log.d("DEBUG NAVIGINE", "Not enough reference points on the location!")
                    8, 30 -> Log.d("DEBUG NAVIGINE", "Not enough beacons on the location!")
                    else -> Log.d(
                        "DEBUG NAVIGINE", "Something is wrong with the location! " +
                                "Error code " + info.errorCode
                    )
                }
            } else Log.d(
                "DEBUG NAVIGINE",
                java.lang.String.format(
                    Locale.ENGLISH,
                    "Device %s: [%d/%d, %.2f, %.2f]",
                    info.id,
                    info.location,
                    info.subLocation,
                    info.x,
                    info.y
                )
            )
        } else {
            Log.d("DEBUG NAVIGINE", "Doesn't work SDK Navigation");
        }
    }
}