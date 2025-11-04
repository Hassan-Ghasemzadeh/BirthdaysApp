🎂 Tavalod+

Persian Birthday Reminder App 🇮🇷 — Built with Kotlin & Jetpack Compose


---

Overview

Tavalod+ is a modern and minimal Android application designed to remind you of birthdays based on the Jalali (Persian) calendar.
All dates are stored in the Solar Hijri system, and the app automatically sends birthday notifications on the right day.

The project follows Clean Architecture + MVVM, built entirely with Kotlin and Jetpack Compose.


---

Features

- Add and manage birthdays using the Persian (Jalali) calendar

- Automatic birthday notifications

- Alarm reset after device reboot (BootReceiver)

- Modern UI built with Jetpack Compose

- Multi-module Clean Architecture

- Offline storage with Room Database

- Dependency injection with Dagger Hilt

- Follows SOLID principles and industry best practices



---


Architecture

Clean Architecture + MVVM

UI (Compose)
   ↓
ViewModel (Hilt)
   ↓
UseCase (Domain)
   ↓
Repository Interface
   ↓
Repository Implementation (Data)
   ↓
Room Database


---

Tech Stack

Layer	Technology

Language	Kotlin
UI	Jetpack Compose
Dependency Injection	Dagger Hilt
Database	Room
Async	Coroutines + Flow
Architecture	Clean Architecture + MVVM
Calendar	PersianDate 
Notifications	AlarmManager + NotificationManager



---

How to Run

git clone https://github.com/<YOUR_USERNAME>/BirthdaysApp.git
cd BirthdaysApp
./gradlew assembleDebug

Or open it directly in Android Studio:

> File → Open → select the project folder → Run ▶️




---

Dependencies (Gradle)

implementation("androidx.compose.ui:ui:1.7.0")  
implementation("androidx.room:room-runtime:2.6.1")  
kapt("androidx.room:room-compiler:2.6.1")  
implementation("com.google.dagger:hilt-android:2.47")  
kapt("com.google.dagger:hilt-compiler:2.47")   


---

Development Notes

All injectable classes are annotated with @HiltViewModel, @AndroidEntryPoint, or use EntryPointAccessors.

Alarms are re-scheduled automatically after device reboot (BootReceiver).

Only valid Persian (Jalali) dates are accepted.



---

Developer

Hassan Ghasemzadeh
Android Developer | Kotlin | Clean Architecture
📧 hghasemzadeh38@gmail.com


---

License

This project is licensed under the MIT License. 
© 2025 Tavalod+ — All rights reserved.
