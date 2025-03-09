package com.example.sleeptracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sleeptracker.ui.theme.SleepTrackerTheme

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
                shape = RoundedCornerShape(10.dp),
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
            title = "Sleep Tracking",
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
                    .size(300.dp, 50.dp),
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
                            color = Color(0xFFEACBFF))
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
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

@Composable
fun ScheduleSettingsScreen(navController: NavHostController) {
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
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

@Composable
fun NotificationSettingsScreen(navController: NavHostController) {
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
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

@Composable
fun SoundSettingsScreen(navController: NavHostController) {
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
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()

    SleepTrackerTheme {
        SleepAnalysisScreen(navController)
    }
}