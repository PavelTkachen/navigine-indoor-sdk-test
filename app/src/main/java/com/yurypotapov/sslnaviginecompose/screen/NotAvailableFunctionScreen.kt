package com.yurypotapov.sslnaviginecompose.screen

import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.yurypotapov.sslnaviginecompose.R;

class NotAvailableFunctionScreen(private val context: Context) {

    @ExperimentalPermissionsApi
    @Composable
    public fun NotAvailableFunctionScreen(navHostController: NavHostController) {
        val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION);
        
        PermissionRequired(
            permissionState = locationPermissionState,
            permissionNotGrantedContent = {
                Scaffold() {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier.padding(15.dp)) {
                            Column {
                                Row {
                                    Text(text = context.getString(R.string.permission_request), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                }
                                Spacer(modifier = Modifier.padding(0.dp, 15.dp))
                                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                    OutlinedButton(onClick = {
                                        locationPermissionState.launchPermissionRequest()
                                    }) {
                                        Text(text = context.getString(R.string.permission_request_button))
                                    }
                                }
                            }
                        }
                    }
                }
            },
            permissionNotAvailableContent = {
                Scaffold() {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier.padding(15.dp)) {
                            Column {
                                Row {
                                    Text(text = context.getString(R.string.permission_complet_failed), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
            }) {
            Scaffold() {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(modifier = Modifier.padding(15.dp)) {
                        Column {
                            Row {
                                Text(text = context.getString(R.string.permission_complete), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            }
                            Spacer(modifier = Modifier.padding(0.dp, 15.dp))
                            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                OutlinedButton(onClick = {
                                   navHostController.navigate("home")
                                }) {
                                    Text(text = context.getString(R.string.permission_complete_button))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}