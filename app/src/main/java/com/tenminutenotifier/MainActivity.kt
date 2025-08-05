package com.tenminutenotifier

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var statusText: TextView
    private lateinit var nextNotificationText: TextView
    private lateinit var notificationSwitch: Switch
    private lateinit var refreshButton: Button
    private lateinit var quotePreviewText: TextView
    private lateinit var batteryOptimizeButton: Button
    
    private lateinit var notificationScheduler: NotificationScheduler
    private lateinit var quoteManager: QuoteManager
    
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        updateUI()
        if (isGranted && notificationSwitch.isChecked) {
            notificationScheduler.scheduleNotifications()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        initComponents()
        setupListeners()
        updateUI()
        
        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= 33) {
            requestNotificationPermission()
        }
        
        // Load initial quote
        loadRandomQuote()
    }
    
    private fun initViews() {
        statusText = findViewById(R.id.statusText)
        nextNotificationText = findViewById(R.id.nextNotificationText)
        notificationSwitch = findViewById(R.id.notificationSwitch)
        refreshButton = findViewById(R.id.refreshButton)
        quotePreviewText = findViewById(R.id.quotePreviewText)
        batteryOptimizeButton = findViewById(R.id.batteryOptimizeButton)
    }
    
    private fun initComponents() {
        notificationScheduler = NotificationScheduler(this)
        quoteManager = QuoteManager(this)
    }
    
    private fun setupListeners() {
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            
            if (isChecked && hasNotificationPermission()) {
                notificationScheduler.scheduleNotifications()
            } else if (!isChecked) {
                notificationScheduler.cancelNotifications()
            }
            updateUI()
        }
        
        refreshButton.setOnClickListener {
            lifecycleScope.launch {
                quoteManager.updateQuotes()
                loadRandomQuote()
            }
        }
        
        batteryOptimizeButton.setOnClickListener {
            requestBatteryOptimizationExemption()
        }
    }
    
    private fun updateUI() {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val notificationsEnabled = prefs.getBoolean("notifications_enabled", false)
        
        notificationSwitch.isChecked = notificationsEnabled
        
        when {
            !hasNotificationPermission() -> {
                statusText.text = "❌ Notification permission required"
            }
            notificationsEnabled -> {
                statusText.text = "✅ Notifications active - Every 10 minutes"
                updateNextNotificationTime()
            }
            else -> {
                statusText.text = "⏸️ Notifications paused"
                nextNotificationText.text = ""
            }
        }
    }
    
    private fun updateNextNotificationTime() {
        val calendar = Calendar.getInstance()
        val currentMinute = calendar.get(Calendar.MINUTE)
        val minutesToNext = 10 - (currentMinute % 10)
        
        calendar.add(Calendar.MINUTE, minutesToNext)
        calendar.set(Calendar.SECOND, 0)
        
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        nextNotificationText.text = "Next notification: ${formatter.format(calendar.time)}"
    }
    
    private fun loadRandomQuote() {
        lifecycleScope.launch {
            val quote = quoteManager.getRandomQuote()
            if (quote != null) {
                quotePreviewText.text = "\"${quote.text}\"\n\n— ${quote.author}"
            } else {
                quotePreviewText.text = "No quotes available. Tap refresh to download."
            }
        }
    }
    
    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= 33) {
            ContextCompat.checkSelfPermission(
                this, "android.permission.POST_NOTIFICATIONS"
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
    
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS")
        }
    }
    
    private fun requestBatteryOptimizationExemption() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            startActivity(intent)
        }
    }
}