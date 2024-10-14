package com.example.alarmmanagertest

interface AlarmScheduler {
    fun schedule(item : AlarmItem)
    fun cancel(item : AlarmItem)
}