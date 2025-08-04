package com.tenminutenotifier

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

class NotificationScheduler(private val context: Context) {
    
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    fun scheduleNotifications() {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            action = "com.tenminutenotifier.SHOW_NOTIFICATION"
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Calculate next 10-minute mark (XX:00:00, XX:10:00, XX:20:00, etc.)
        val nextTriggerTime = getNextTenMinuteMark()
        
        // Use setAlarmClock for highest priority (handles Doze mode)
        val alarmClockInfo = AlarmManager.AlarmClockInfo(
            nextTriggerTime,
            null // No intent for when user taps the alarm
        )
        
        try {
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
        } catch (e: SecurityException) {
            // Fallback for devices that don't allow exact alarms
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                nextTriggerTime,
                10 * 60 * 1000L, // 10 minutes
                pendingIntent
            )
        }
    }
    
    fun cancelNotifications() {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            action = "com.tenminutenotifier.SHOW_NOTIFICATION"
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        alarmManager.cancel(pendingIntent)
    }
    
    private fun getNextTenMinuteMark(): Long {
        val calendar = Calendar.getInstance()
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentSecond = calendar.get(Calendar.SECOND)
        
        // Calculate minutes to add to reach next 10-minute mark
        val minutesToAdd = 10 - (currentMinute % 10)
        
        // If we're exactly at a 10-minute mark with 0 seconds, schedule for next one
        if (minutesToAdd == 10 && currentSecond == 0) {
            calendar.add(Calendar.MINUTE, 10)
        } else {
            calendar.add(Calendar.MINUTE, minutesToAdd)
        }
        
        // Set to exact time (0 seconds, 0 milliseconds)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        
        return calendar.timeInMillis
    }
    
    companion object {
        private const val NOTIFICATION_REQUEST_CODE = 1001
    }
}