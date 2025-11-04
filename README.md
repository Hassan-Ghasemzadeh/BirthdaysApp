🎂 تولد+ (Tavalod+)

اپلیکیشن یادآور تولد با تقویم شمسی 🇮🇷 — ساخته‌شده با Kotlin و Jetpack Compose


---

📱 معرفی پروژه

یادروز یه اپلیکیشن مدرن و مینیمال برای ثبت و یادآوری تولد دوستان و عزیزانه.
تمام تاریخ‌ها بر اساس تقویم شمسی ذخیره می‌شن و در روز تولد، اپ به‌صورت خودکار نوتیفیکیشن تبریک نمایش می‌ده.

اپ با معماری Clean Architecture + MVVM طراحی شده و از جدیدترین کتابخانه‌های Jetpack استفاده می‌کنه.


---

✨ ویژگی‌ها

📅 ثبت تولدها با تقویم شمسی

🔔 ارسال نوتیفیکیشن خودکار در روز تولد

♻️ بازتنظیم آلارم‌ها بعد از ریست گوشی (BootReceiver)

🎨 رابط کاربری مدرن با Jetpack Compose

🧩 معماری تمیز با ماژول‌بندی (multi-module)

💾 ذخیره‌سازی آفلاین با Room Database

⚡ مدیریت وابستگی‌ها با Dagger Hilt

🧠 رعایت اصول SOLID و Best Practices



---

🛠 تکنولوژی‌ها و کتابخانه‌ها

بخش	ابزار

زبان	Kotlin
UI	Jetpack Compose
DI	Dagger Hilt
Database	Room
Async	Kotlin Coroutines + Flow
Architecture	Clean + MVVM
Calendar	PersianDate  
Notification	AlarmManager + NotificationManager



---

🖼 پیش‌نمایش

صفحه اصلی	افزودن تولد	نوتیفیکیشن

		



---

⚙️ نصب و اجرا

git clone https://github.com/<YOUR_USERNAME>/BirthdaysApp.git
cd BirthdaysApp
./gradlew assembleDebug

یا مستقیماً در Android Studio:

> File → Open → پوشه پروژه → Run ▶️




---

📦 وابستگی‌ها (Gradle)

implementation("androidx.compose.ui:ui:1.7.0")
implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
implementation("com.google.dagger:hilt-android:2.47")
kapt("com.google.dagger:hilt-compiler:2.47") 


---

💡 نکات توسعه

همه‌ی کلاس‌های تزریقی باید با @HiltViewModel, @AndroidEntryPoint, یا EntryPointAccessors علامت‌گذاری بشن.

آلارم‌ها بعد از هر بوت دوباره تنظیم می‌شن (BootReceiver).

فقط تاریخ‌های شمسی معتبر پذیرفته می‌شن.



---

🪪 لایسنس

این پروژه تحت مجوز MIT License منتشر شده است.

© 2025 Tavalod+ — All rights reserved.


---
