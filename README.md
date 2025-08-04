# 10-Minute Notifier Android App

Android app that sends inspirational quote notifications every 10 minutes at precise intervals (XX:00, XX:10, XX:20, etc.).

## 🚀 Quick Setup

### 1. Open in Android Studio
1. Launch Android Studio
2. File → Open → Select `C:\dev\TenMinuteNotifier`
3. Wait for Gradle sync to complete

### 2. Update Quote Service URL
**CRITICAL**: Before building, edit `QuoteManager.kt` line 27:
```kotlin
private val quotesUrl = "https://[YOUR-USERNAME].github.io/my-quotes-service/quotes.json"
```
Replace `[YOUR-USERNAME]` with your actual GitHub username.

### 3. Build APK
1. Build → Build Bundle(s) / APK(s) → Build APK(s)
2. Wait for build completion
3. APK location: `app/build/outputs/apk/debug/app-debug.apk`

### 4. Install on Device
- Transfer APK to phone and install
- Grant notification permissions
- Disable battery optimization for reliable alerts
- Enable 10-minute notifications in app

## 📱 Features

- **Precise timing**: Notifications at exact 10-minute marks
- **Quote integration**: Random inspirational quotes in each notification
- **Battery optimization**: Bypasses Doze mode for reliable delivery
- **Offline support**: Quotes cached locally, works without internet
- **Auto-restart**: Notifications resume after device reboot

## 🛠️ Development Notes

- **Package**: `com.tenminutenotifier`
- **Min SDK**: API 21 (Android 5.0)
- **Target SDK**: API 33 (Android 13)
- **Language**: Kotlin

## 📋 Setup Checklist

- [ ] Open project in Android Studio
- [ ] Update GitHub username in QuoteManager.kt
- [ ] Set up GitHub Pages quote service (see quote-service-guide.md)
- [ ] Build and install APK
- [ ] Grant permissions and disable battery optimization
- [ ] Test notifications at 10-minute intervals

## 🔧 Troubleshooting

**Notifications not appearing?**
- Check battery optimization settings
- Verify notification permissions
- Ensure exact alarm permission granted

**Quotes not loading?**
- Verify GitHub Pages URL is accessible
- Check internet connection for initial download
- Test quote refresh button in app

---

**Ready to build! Open in Android Studio and follow the setup steps.**