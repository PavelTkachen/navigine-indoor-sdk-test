package com.yurypotapov.sslnaviginecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.navigine.idl.java.LocationManager
import com.navigine.sdk.Navigine
import com.yurypotapov.sslnaviginecompose.ui.theme.SSLNavigineComposeTheme
import com.navigine.idl.java.NavigineSdk
import com.yurypotapov.sslnaviginecompose.listener.LocationListener
import com.yurypotapov.sslnaviginecompose.listener.PositionListener

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Navigine library
        Navigine.initialize(applicationContext);
        NavigineSdk.setUserHash("FD45-C20F-3C16-F8CA");

        val mNavigineSdk = NavigineSdk.getInstance()
        val mLocationManager: LocationManager = mNavigineSdk.locationManager
        mLocationManager.addLocationListener(LocationListener());
        mLocationManager.locationId = 91226;
        val mNavigationManager = mNavigineSdk.getNavigationManager(mLocationManager);
        mNavigationManager.addPositionListener(PositionListener());
//        mNavigationManager.startLogRecording();


        setContent {
            SSLNavigineComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainContent();
                }
            }
        }
    }

    @Composable
    public fun MainContent() {
        val result = remember { mutableStateOf("") };
        val selectedItem = remember { mutableStateOf("upload") }
        val fabShape = RoundedCornerShape(50)
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = getString(R.string.app_name))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            result.value = "Drawer icon clicked"
                        }
                    ) {
                        Icon(Icons.Filled.LocationOn, contentDescription = "")
                    }
                },
                backgroundColor = Color(Color.White.value),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
            content = {
                Box(modifier = Modifier.padding(16.dp))
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { result.value = "FAB clicked" },
                    shape = fabShape,
                    backgroundColor = Color(Color.White.value)
                ) {
                    Icon(Icons.Filled.Refresh, "Update Manually")
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.End,

            bottomBar = {
                BottomAppBar(
                    backgroundColor = Color(Color.White.value),
                    cutoutShape = fabShape,
                    content = {
                        BottomNavigation(
                            backgroundColor = Color(Color.White.value),
                            elevation = 0.dp
                        ) {
                            BottomNavigationItem(
                                icon = {
                                    Icon(Icons.Rounded.Home, "")
                                },
                                label = { Text(text = "Dashboard") },
                                selected = selectedItem.value == "Dashboard",
                                onClick = {
                                    result.value = "Dashboard icon clicked"
                                    selectedItem.value = "Dashboard"
                                },
                                alwaysShowLabel = false,
                            )

                            BottomNavigationItem(
                                icon = {
                                    Icon(Icons.Filled.Settings, "")
                                },
                                label = { Text(text = "Settings") },
                                selected = selectedItem.value == "Settings",
                                onClick = {
                                    result.value = "Settings icon clicked"
                                    selectedItem.value = "Setting"
                                },
                                alwaysShowLabel = false
                            )
                        }
                    }
                )
            }
        )
    }
}