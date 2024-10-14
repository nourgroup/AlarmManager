package com.example.alarmmanagertest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AlarmService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.i("Project_Alarm","AlarmService:onBind")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("Project_Alarm","AlarmService:onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Project_Alarm","AlarmService:onStartCommand")
        val th = Thread {
            while (true){
                Thread.sleep(2000)
                Log.i("Project_Alarm","AlarmService:onStartCommand:inside")
            }
        }
        th.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Project_Alarm","AlarmService:onDestroy")
    }
}