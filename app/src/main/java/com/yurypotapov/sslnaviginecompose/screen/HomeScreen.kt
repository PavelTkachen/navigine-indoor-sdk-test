package com.yurypotapov.sslnaviginecompose.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.navigine.idl.java.LocationManager
import com.navigine.idl.java.NavigineSdk
import com.navigine.sdk.Navigine
import com.navigine.view.LocationView
import com.yurypotapov.sslnaviginecompose.listener.LocationListener
import com.yurypotapov.sslnaviginecompose.listener.PositionListener
import kotlinx.coroutines.launch
import com.yurypotapov.sslnaviginecompose.R;
import com.yurypotapov.sslnaviginecompose.service.NavigineService


class HomeScreen(private val context: Context) {

    private var locationView: LocationView;
    private var navigineService: NavigineService =
        NavigineService(context, "FD45-C20F-3C16-F8CA", 91226);

    companion object {
        const val SEARCH_OBJECTS_DRAWER_STATE = "search_objects_drawer_state";
        const val SEARCH_ROUTE_DRAWER_STATE = "search_route_drawer_state";
    }

    init {
        this.navigineService.init();
        this.locationView = this.navigineService.getLocationView();
    }

    @Composable
    public fun GetDrawerHeader(drawerState: String) {
        val title = when (drawerState) {
            SEARCH_OBJECTS_DRAWER_STATE -> context.getString(R.string.search_objects_drawer_title)
            SEARCH_ROUTE_DRAWER_STATE -> context.getString(R.string.search_route_drawer_title)
            else -> "UNDEFINED TITLE"
        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 4.em
        )
    }

    @ExperimentalMaterialApi
    @Composable
    public fun HomeScreenPage(navHostController: NavHostController) {
        val result = remember { mutableStateOf("") };
        val selectedItem = remember { mutableStateOf("upload") }
        val fabShape = RoundedCornerShape(50)
        var drawerStateValue by remember { mutableStateOf(SEARCH_OBJECTS_DRAWER_STATE) }
        val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope();







        //initial traditional android view
        this.locationView = LocationView(context);

        ModalBottomSheetLayout(
            sheetContent = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(15.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        GetDrawerHeader(drawerStateValue);
                    }
                }
                when (drawerStateValue) {
                    SEARCH_OBJECTS_DRAWER_STATE -> {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                            items(graphTags) {
//                                Card(
//                                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
//                                    onClick = { null }
//                                ) {
//                                    Column(modifier = Modifier.padding(16.dp)) {
//                                        Text(text = it)
//                                    }
//                                }
//                            }
                        }
                    }
                    SEARCH_ROUTE_DRAWER_STATE -> {

                    }
                    else -> {
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(text = "Undefined Title", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            },
            sheetState = bottomState,
        ) {
            Scaffold(topBar = {
                TopAppBar(
                    title = {
                        Text(text = context.getString(R.string.app_name))
                    },
                    navigationIcon = {
                        IconButton(onClick = { null }) {
                            Icon(Icons.Rounded.Home, contentDescription = "Home")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                drawerStateValue = SEARCH_OBJECTS_DRAWER_STATE
                                scope.launch {
                                    bottomState.show();
                                }
                            }
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_find_objects),
                                contentDescription = "Find Objects"
                            )
                        }
                        IconButton(
                            onClick = {
                                drawerStateValue = SEARCH_ROUTE_DRAWER_STATE
                                scope.launch {
                                    bottomState.show();
                                }
                            }
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_find_route),
                                contentDescription = "Find Route"
                            )
                        }
                    },
                    backgroundColor = Color(Color.White.value),
                    elevation = AppBarDefaults.TopAppBarElevation
                )
            },
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            AndroidView(factory = {
                                locationView;
                            });
                        }

                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                        },
                        shape = fabShape,
                        backgroundColor = Color(Color.White.value)
                    ) {
                        Icon(Icons.Rounded.LocationOn, "Focus")
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
                                        Icon(
                                            Icons.Rounded.Home,
                                            context.getString(R.string.home_menu)
                                        )
                                    },
                                    label = { Text(text = context.getString(R.string.home_menu)) },
                                    selected = selectedItem.value == context.getString(R.string.home_menu),
                                    onClick = {
                                        context.getString(R.string.home_menu)
                                    },
                                    alwaysShowLabel = true,
                                )
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painterResource(R.drawable.ic_debug),
                                            context.getString(R.string.debug_menu)
                                        )
                                    },
                                    label = { Text(text = "Debug") },
                                    selected = selectedItem.value == context.getString(R.string.debug_menu),
                                    onClick = {
                                        result.value = context.getString(R.string.debug_menu)
                                        selectedItem.value = context.getString(R.string.debug_menu)
                                    },
                                    alwaysShowLabel = true
                                )
                            }
                        }
                    )
                }
            )
        }
    }

    @ExperimentalMaterialApi
    @Composable
    public fun LazyColumnRow(title: String, onClickValue: (String) -> Unit) {
        ListItem(text = { Text(text = title)})
    }
}