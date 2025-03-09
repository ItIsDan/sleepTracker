package com.example.sleeptracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sleeptracker.ui.theme.SleepTrackerTheme
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            SleepTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding: PaddingValues ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "loginScreen") {
                            composable("loginScreen") { LoginScreen(navController) }
                            composable("sleepTracking") { SleepTrackingScreen(navController) }
                            composable("sleepAnalysis") { SleepAnalysisScreen(navController) }
                            composable("scheduleSettings") { ScheduleSettingsScreen(navController) }
                            composable("notificationSettings") { NotificationSettingsScreen(navController) }
                            composable("soundSettings") { SoundSettingsScreen(navController) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(title: String, navController: NavHostController) {
    var isOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF907ACA))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text ("☰",
            fontSize = 24.sp,
            color = Color(0xFFEACBFF),
            modifier = Modifier.clickable { isOpen = !isOpen }
        )
        Text (title,
            fontSize = 24.sp,
            color = Color(0xFFEACBFF),
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(modifier = Modifier.width(24.dp))
    }

    DrawerMenu(isOpen = isOpen, onClose = { isOpen = false }, navController)
}

@Composable
fun DrawerMenu(isOpen: Boolean, onClose: () -> Unit, navController: NavHostController) {
    val navigateItems = listOf(
        "sleepTracking" to "Sleep Tracking",
        "sleepAnalysis" to "Sleep Analysis",
        "scheduleSettings" to "Schedule Settings",
        "notificationSettings" to "Notification Settings",
        "soundSettings" to "Sound Settings"
    )

    if (isOpen) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)),
            contentAlignment = Alignment.TopStart
        ) {
            Column (
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFEACBFF))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                navigateItems.forEach { (route, text) ->
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEACBFF))
                            .clickable {
                                navController.navigate(route)
                                onClose()
                            }
                    ) {
                        Text (
                            text = text,
                            fontSize = 20.sp,
                            color = Color(0xFF65558F)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box (
                    modifier = Modifier
                        .background(Color(0xFFEACBFF))
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("loginScreen")
                        },
                ) {
                    Text (
                        text = "Log out",
                        fontSize = 20.sp,
                        color = Color(0xFF65558F)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color(0xFF907ACA))
            .fillMaxSize()
            .padding(top = 160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            style = TextStyle(
                fontSize = 40.sp,
                color = Color(0xFFEADBFF)
            )
        )
        Spacer(modifier = Modifier.height(140.dp))
        Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text (
                    text = "Username",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF65558F)
                    )
                )},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(300.dp, 55.dp)
                    .background(Color(0xFFEACBFF)),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF65558F)
                )
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text (
                    text = "Password",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF65558F)
                    )
                )},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(300.dp, 55.dp)
                    .background(Color(0xFFEACBFF)),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF65558F)
                )
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
            Button(
                onClick = { navController.navigate("sleepTracking") },
                modifier = Modifier.size(300.dp, 50.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFFEACBFF)
                    )
                )
            }
            Button(
                onClick = {},
                modifier = Modifier.size(300.dp, 50.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text(
                    text = "Register",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFFEACBFF)
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {},
                modifier = Modifier.size(300.dp, 50.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFFEACBFF)
                    )
                )
            }
        }
    }
}

@Composable
fun SleepTrackingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF907ACA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar (
            title = "Sleep tracking",
            navController = navController
        )
        Column (
            modifier = Modifier
            .background(Color(0xFF907ACA))
            .fillMaxSize()
            .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Good Night",
                style = TextStyle(
                    fontSize = 32.sp,
                    color = Color(0xFFEADBFF)
                ),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(120.dp))
            Card (
                modifier = Modifier
                    .size(
                        width = 180.dp,
                        height = 80.dp
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEACBFF)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "22:48",
                        color = Color(0xFF65558F),
                        fontSize = 57.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Sleep duration",
                color = Color(0xFFEACBFF),
                fontSize = 28.sp,
            )
            Card(
                modifier = Modifier
                    .size(
                        width = 135.dp,
                        height = 50.dp
                    )
                    .padding(top = 5.dp, start = 0.dp,  end = 0.dp, bottom = 0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEACBFF)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "00:00",
                        color = Color(0xFF65558F),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Button (
                onClick = { navController.navigate("sleepAnalysis") },
                modifier = Modifier
                    .size(330.dp, 40.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text(
                    text = "Start tracking",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color(0xFFEACBFF)
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SleepAnalysisScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF907ACA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar (
            title = "Sleep analysis",
            navController = navController
        )
        Column (
            modifier = Modifier
                .background(Color(0xFF907ACA))
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.size(220.dp, 140.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEACBFF))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Tuesday",
                        fontSize = 24.sp,
                        color = Color(0xFF65558F)
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = { navController.navigate("sleepTracking") },
                        modifier = Modifier.height(40.dp).width(180.dp),
                        shape = RoundedCornerShape(100.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
                    ) {
                        Text(
                            text = "Start tracking",
                            fontSize = 20.sp,
                            color = Color(0xFFEACBFF)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    text = "Statistics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEACBFF)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = Color(0xFFEACBFF)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(
                    modifier = Modifier.size(130.dp, 130.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEACBFF))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Quality",
                            fontSize = 22.sp,
                            color = Color(0xFF65558F)
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        Text(
                            text = "82 %",
                            fontSize = 30.sp,
                            color = Color(0xFF65558F))
                    }
                }
                Card(
                    modifier = Modifier.size(130.dp, 130.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEACBFF))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Duration",
                            fontSize = 22.sp,
                            color = Color(0xFF65558F)
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        Text(
                            text = "7h 35m",
                            fontSize = 28.sp,
                            color = Color(0xFF65558F)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleSettingsScreen(navController: NavHostController) {
    val startTimePickerState = rememberTimePickerState(initialHour = 1, initialMinute = 0, true)
    val endTimePickerState = rememberTimePickerState(initialHour = 1, initialMinute = 0, true)

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF907ACA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar (
            title = "Schedule settings",
            navController = navController
        )
        Column (
            modifier = Modifier
                .background(Color(0xFF907ACA))
                .fillMaxSize()
                .padding(start = 40.dp, top = 120.dp, bottom = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "Choose your sleep schedule",
                        fontSize = 22.sp,
                color = Color(0xFFEACBFF)
            )
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF65558F)
                ),
                modifier = Modifier.size(
                    width = 280.dp,
                    height = 300.dp
                )
            ) {
                Column (
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter start time",
                        color = Color(0xFFEACBFF),
                        fontSize = 12.sp
                    )
                    TimeInput(
                        state = endTimePickerState,
                        colors = TimePickerDefaults.colors(
                            timeSelectorSelectedContainerColor = Color(0xFF554778),
                            timeSelectorSelectedContentColor = Color(0xFFEACBFF),
                            timeSelectorUnselectedContainerColor = Color(0xFF554778),
                            timeSelectorUnselectedContentColor = Color(0xFFEACBFF),
                            periodSelectorUnselectedContentColor = Color(0xFFEACBFF)
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Enter wake up time",
                        color = Color(0xFFEACBFF),
                        fontSize = 12.sp,
                    )
                    TimeInput(
                        state = startTimePickerState,
                        colors = TimePickerDefaults.colors(
                            timeSelectorSelectedContainerColor = Color(0xFF554778),
                            timeSelectorSelectedContentColor = Color(0xFFEACBFF),
                            timeSelectorUnselectedContainerColor = Color(0xFF554778),
                            timeSelectorUnselectedContentColor = Color(0xFFEACBFF),
                            periodSelectorUnselectedContentColor = Color(0xFFEACBFF)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 18.sp,
                    color = Color(0xFFEACBFF)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(navController: NavHostController) {
    var enableNotifications by remember { mutableStateOf(true) }
    var warnIfIgnored by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(initialHour = 1, initialMinute = 0, true)

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF907ACA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar (
            title = "Notification settings",
            navController = navController
        )
        Column (
            modifier = Modifier
                .background(Color(0xFF907ACA))
                .fillMaxSize()
                .padding(start = 40.dp, top = 120.dp, bottom = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Switch(
                    checked = enableNotifications,
                    onCheckedChange = { enableNotifications = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFEACBFF))
                )
                Text(
                    text = "Enable notifications",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEACBFF)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Switch(
                    checked = warnIfIgnored,
                    onCheckedChange = { warnIfIgnored = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFEACBFF))
                )
                Text(
                    text = "Warn if schedule was ignored",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEACBFF)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF65558F)
                ),
                modifier = Modifier.size(
                    width = 280.dp,
                    height = 160.dp
                )
            ) {
                Column (
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Warn in advance before sleep schedule",
                        color = Color(0xFFEACBFF),
                        fontSize = 12.sp,
                    )
                    TimeInput(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            timeSelectorSelectedContainerColor = Color(0xFF554778),
                            timeSelectorSelectedContentColor = Color(0xFFEACBFF),
                            timeSelectorUnselectedContainerColor = Color(0xFF554778),
                            timeSelectorUnselectedContentColor = Color(0xFFEACBFF),
                            periodSelectorUnselectedContentColor = Color(0xFFEACBFF)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 18.sp,
                    color = Color(0xFFEACBFF)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundSettingsScreen(navController: NavHostController) {
    val timePickerState = rememberTimePickerState(initialHour = 1, initialMinute = 0, true)
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val sounds = listOf("Sound 1", "Sound 2", "Sound 3", "Sound 4", "Sound 5", "Sound 6")

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF907ACA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar (
            title = "Sound settings",
            navController = navController
        )
        Column (
            modifier = Modifier
                .background(Color(0xFF907ACA))
                .fillMaxSize()
                .padding(start = 40.dp, top = 60.dp, bottom = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text (
                text = "Sound choice",
                fontSize = 22.sp,
                color = Color(0xFFEACBFF)
            )
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(sounds) { sound ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFEACBFF)
                            ),
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier.size(
                                width = 360.dp,
                                height = 50.dp
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { println("Clicked on: $sound") },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = sound,
                                    fontSize = 18.sp,
                                    color = Color(0xFF65558F)
                                )
                            }
                        }
                    }

                }
            }
            Text (
                text = "Sound volume",
                fontSize = 22.sp,
                color = Color(0xFFEACBFF)
            )
            Slider (
                value = sliderPosition,
                onValueChange = { sliderPosition = it }
            )
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF65558F)
                ),
                modifier = Modifier.size(
                    width = 280.dp,
                    height = 160.dp
                )
            ) {
                Column (
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Shutdown timer",
                        color = Color(0xFFEACBFF),
                        fontSize = 12.sp,
                    )
                    TimeInput(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            timeSelectorSelectedContainerColor = Color(0xFF554778),
                            timeSelectorSelectedContentColor = Color(0xFFEACBFF),
                            timeSelectorUnselectedContainerColor = Color(0xFF554778),
                            timeSelectorUnselectedContentColor = Color(0xFFEACBFF),
                            periodSelectorUnselectedContentColor = Color(0xFFEACBFF)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF65558F))
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 18.sp,
                    color = Color(0xFFEACBFF)
                )
            }
        }
    }
}