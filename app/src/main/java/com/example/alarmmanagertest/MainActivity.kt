package com.example.alarmmanagertest

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alarmmanagertest.ui.theme.AlarmManagerTestTheme
import java.time.LocalDateTime
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    private lateinit var powerReceiver: PowerModeReceiver

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create an instance of the receiver
        powerReceiver = PowerModeReceiver()

        // Create an intent filter to listen for power connection and disconnection
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }

        // Register the receiver with the activity
        registerReceiver(powerReceiver, filter)
        enableEdgeToEdge()
        val scheduler = AndroidAlarmScheduler(this)
        setContent {
            AlarmManagerTestTheme {
                var secondsText by remember { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplayButtons(
                        modifier = Modifier.padding(innerPadding),
                        scheduler = scheduler
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayButtons(modifier: Modifier = Modifier, scheduler: AndroidAlarmScheduler) {
    val alarmItem = AlarmItem(LocalDateTime.now().plusSeconds(1100), "")
    Column {
        Button(onClick = {
            scheduler.schedule(alarmItem)
        }) {
            Text(
                text = "schedule",
                modifier = modifier
            )
        }
        Button(onClick = {
            scheduler.cancel(alarmItem)
        }) {
            Text(
                text = "cancel",
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlarmManagerTestTheme {
        //Greeting("Android")
    }
}