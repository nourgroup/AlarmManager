package com.example.alarmmanagertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.time.LocalDateTime
import java.util.Calendar


class PowerModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Code to be executed when the alarm triggers
        //val calendar: Calendar = Calendar.getInstance()
        //Log.i("Project_Alarm","execute at "+calendar.time);
        //calendar.setTimeInMillis(System.currentTimeMillis())
        //Toast.makeText(context, "execute at "+calendar.time, Toast.LENGTH_SHORT).show()
        // You can perform any task here, e.g., start a service or perform background work.
        val scheduler = AndroidAlarmScheduler(context)
        val alarmItem = AlarmItem(LocalDateTime.now().plusSeconds(10000), "")

        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                // Code to run when power is connected
                Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show()
                scheduler.schedule(alarmItem)
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                // Code to run when power is disconnected
                Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show()
                scheduler.cancel()
            }
        }
    }
}