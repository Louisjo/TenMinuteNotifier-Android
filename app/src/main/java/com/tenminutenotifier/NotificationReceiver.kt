package com.tenminutenotifier

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotificationReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "com.tenminutenotifier.SHOW_NOTIFICATION" -> {
                showNotificationWithQuote(context)
                // Schedule next notification
                NotificationScheduler(context).scheduleNotifications()
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                // Reschedule notifications after device restart
                val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                if (prefs.getBoolean("notifications_enabled", false)) {
                    NotificationScheduler(context).scheduleNotifications()
                }
            }
        }
    }
    
    private fun showNotificationWithQuote(context: Context) {
        createNotificationChannel(context)
        
        // Use coroutine to get quote asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            val quoteManager = QuoteManager(context)
            val quote = quoteManager.getRandomQuote()
            
            val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val currentTime = timeFormatter.format(Date())
            
            val title = "10-Minute Reminder"
            val text = if (quote != null) {
                "\"${quote.text}\" — ${quote.author}"
            } else {
                "Time for your 10-minute check-in!"
            }
            val bigText = "$text\n\nTime: $currentTime"
            
            // Create intent to open app when notification is tapped
            val tapIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val tapPendingIntent = PendingIntent.getActivity(
                context, 0, tapIntent, PendingIntent.FLAG_IMMUTABLE
            )
            
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(tapPendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build()
            
            try {
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
            } catch (e: SecurityException) {
                // Handle case where notification permission was revoked
            }
        }
    }
    
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "10-Minute Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications every 10 minutes with inspirational quotes"
                enableVibration(true)
                enableLights(true)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    companion object {
        private const val CHANNEL_ID = "ten_minute_notifications"
        private const val NOTIFICATION_ID = 1
    }
}