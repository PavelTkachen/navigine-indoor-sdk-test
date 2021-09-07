package com.yurypotapov.sslnaviginecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.yurypotapov.sslnaviginecompose.ui.theme.SSLNavigineComposeTheme
import com.yurypotapov.sslnaviginecompose.screen.DebugScreen
import com.yurypotapov.sslnaviginecompose.screen.HomeScreen
import com.yurypotapov.sslnaviginecompose.screen.NotAvailableFunctionScreen
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SSLNavigineComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainContent();
                }
            }
        }
    }

    @ExperimentalPermissionsApi
    @ExperimentalMaterialApi
    @Composable
    public fun MainContent() {
        val navController = rememberNavController();
        val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION);

        NavHost(navController, startDestination = "home") {
            composable(route = "home") {
                HomeScreen(applicationContext).HomeScreenPage(navController);
            }
            composable(route = "debug") {
                DebugScreen(applicationContext).DebugScreenPage(navController);
            }
            composable(route = "notavailable") {
                NotAvailableFunctionScreen(applicationContext).NotAvailableFunctionScreen(navController);
            }
        }

        if(!(locationPermissionState.hasPermission)) {
            navController.navigate("notavailable");
        }
    }
}