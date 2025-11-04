package com.lifeandme.core.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import java.util.*

object AlarmScheduler {

    const val ACTION_BIRTHDAY_ALARM = "com.lifeandme.action.BIRTHDAY_ALARM"

    fun scheduleBirthday(
        context: Context,
        id: Long,
        name: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (!alarmManager.canScheduleExactAlarms()) {
                Log.e(
                    "AlarmScheduler",
                    "خطا: مجوز 'زمان‌بندی آلارم دقیق' فعال نیست. آلارم تنظیم نمی‌شود."
                )


                return
            }
        }

        val intent = Intent(ACTION_BIRTHDAY_ALARM).apply {
            `package` = context.packageName
            putExtra("id", id)
            putExtra("name", name)
            putExtra("year", year)
            putExtra("month", month)
            putExtra("day", day)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val now = Calendar.getInstance()
        val targetCalendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, now.get(Calendar.YEAR))
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (targetCalendar.timeInMillis <= now.timeInMillis) {
            targetCalendar.add(Calendar.YEAR, 1)
            Log.d("AlarmScheduler", "تاریخ گذشته است، به سال بعد منتقل شد: ${targetCalendar.time}")
        } else {
            Log.d("AlarmScheduler", "تاریخ در امسال (آینده) تنظیم شد: ${targetCalendar.time}")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val infoIntent = getInfoPendingIntent(context)
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(targetCalendar.timeInMillis, infoIntent),
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, targetCalendar.timeInMillis, pendingIntent
            )
        }
    }

    private fun getInfoPendingIntent(context: Context): PendingIntent {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            ?: Intent().apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }

        return PendingIntent.getActivity(
            context,
            999,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun cancelAlarm(context: Context, id: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            Intent(ACTION_BIRTHDAY_ALARM).apply { `package` = context.packageName },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}