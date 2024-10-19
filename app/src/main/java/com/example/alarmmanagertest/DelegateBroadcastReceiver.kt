package com.example.alarmmanagertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DelegateBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.startService(Intent(context, AlarmService::class.java))
        if(context != null){
            val androidAlarmScheduler = AndroidAlarmScheduler(context)
            val alarmItem = AlarmItem(60_000, "DelegateBroadcastReceiver")
            androidAlarmScheduler.schedule(alarmItem)
        }
    }
}