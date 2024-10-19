package com.example.alarmmanagertest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import java.util.Calendar


class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmItem) {
        val delay: Long = item.time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Create an intent that will trigger the BroadcastReceiver
            val intentFor31 = Intent(context, DelegateBroadcastReceiver::class.java)
            // FLAG_IMMUTABLE & FLAG_MUTABLE for SDK 31 (A10 & A11)
            val pendingIntentFor31 =
                PendingIntent.getBroadcast(context, 2, intentFor31, PendingIntent.FLAG_IMMUTABLE)

            // Set the time to trigger the alarm (e.g., 8:00 AM every day)
            //val tomorrow: Calendar = Calendar.getInstance()
            //tomorrow.setTimeInMillis(System.currentTimeMillis())
            //tomorrow.set(Calendar.MINUTE, 2) // Set minutes to 0
            //Log.i("Project_Alarm","Now Time here "+calendar.time);
            //Log.i("Project_Alarm", "Desired Time here to trigger" + tomorrow.time);
            // attention !! todo
            // When the SCHEDULE_EXACT_ALARM permission is revoked for your app, your app stops, and all future exact alarms are canceled. This also means that the value returned by canScheduleExactAlarms() stays valid for the entire lifecycle of your app.
            // When the SCHEDULE_EXACT_ALARMS permission is granted to your app, the system sends it the ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED broadcast.
            if (alarmManager.canScheduleExactAlarms()) {
                // Your app has permission to schedule exact alarms
                Log.d("Project_Alarm", "Exact alarm permission granted")
                // Set a repeating alarm
                //alarmManager.setExactAndAllowWhileIdle & setExactAndAllowWhileIdle & setInexactRepeating
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,  // Wake up the device if necessary
                    System.currentTimeMillis() + delay,  // Set the alarm time
                    pendingIntentFor31 // The PendingIntent to trigger
                )
                Toast.makeText(context, "clicked at $delay", Toast.LENGTH_SHORT).show()
            } else {
                // Permission is not granted. Request or guide the user to enable it.
                Log.d("Project_Alarm", "Exact alarm permission not granted")
                val intent1 = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent1.setData(Uri.parse("package:" + context.packageName))
                context.startActivity(intent1)
            }
        } else {
            val intent = Intent(context, AlarmService::class.java)
            val pendingIntent =
                PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,  // Wake up the device if necessary
                System.currentTimeMillis() + delay,  // Set the alarm time
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,  // Repeat every day
                pendingIntent // The PendingIntent to trigger
            )
        }
    }

    /*override fun schedule() {
        // Create an intent that will trigger the BroadcastReceiver
        val intentFor31 = Intent(context, DelegateBroadcastReceiver::class.java)
        // FLAG_IMMUTABLE & FLAG_MUTABLE for SDK 31 (A10 & A11)
        val pendingIntentFor31 =
            PendingIntent.getBroadcast(context, 2, intentFor31, PendingIntent.FLAG_IMMUTABLE)

        val delay: Long = 300_000
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                // Your app has permission to schedule exact alarms
                Log.d("Project_Alarm", "Exact alarm permission granted")
                // Set a repeating alarm
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,  // Wake up the device if necessary
                    System.currentTimeMillis() + delay,  // Set the alarm time
                    pendingIntentFor31 // The PendingIntent to trigger
                )
                Toast.makeText(context, "clicked at $delay", Toast.LENGTH_SHORT).show()
            } else {
                // Permission is not granted. Request or guide the user to enable it.
                Log.d("Project_Alarm", "Exact alarm permission not granted")
                val intent1 = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent1.setData(Uri.parse("package:" + context.packageName))
                context.startActivity(intent1)
            }
        }
    }*/

    override fun cancel() {
        val intentFor31 = Intent(context, DelegateBroadcastReceiver::class.java)
        val pendingIntentFor31 =
            PendingIntent.getBroadcast(context, 2, intentFor31, PendingIntent.FLAG_IMMUTABLE)
        val intent = Intent(context, AlarmService::class.java)
        val pendingIntent = PendingIntent.getService(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.cancel(pendingIntentFor31)
        } else {
            alarmManager.cancel(pendingIntent)
        }
    }
}
