package com.lifeandme.birthdaysapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.lifeandme.birthdaysapp.R
import com.lifeandme.core.util.AlarmScheduler
import com.lifeandme.core.util.NotificationHelper
import com.lifeandme.domain.repository.BirthdayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Receiver for birthday alarms.
 * It shows the notification and reschedules the alarm for the next year.
 */
class BirthdayAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var birthdayRepository: BirthdayRepository

    override fun onReceive(context: Context, intent: Intent) {
         if (intent.action != AlarmScheduler.ACTION_BIRTHDAY_ALARM) return

        // Tells the system that the receiver is still working asynchronously.
        val pendingResult: PendingResult = goAsync()

        val id = intent.getLongExtra("id", 0)
        val name = intent.getStringExtra("name") ?: "دوست عزیز"

        // Show the notification
        NotificationHelper.createChannel(context, "birthdays_channel", "یادآوری تولد")
        val notif = NotificationHelper.buildNotification(
            context,
            "birthdays_channel",
            context.getString(R.string.birthday_notification_title, name),
            context.getString(R.string.birthday_notification_content, name)
        )
        val nm =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        nm.notify(id.toInt(), notif)

        // Reschedule the alarm for the next year/after boot
        CoroutineScope(Dispatchers.IO).launch {
            try {
                 birthdayRepository.getAllBirthdays().firstOrNull()?.let { list ->
                    list.firstOrNull { it.id == id }?.let { b ->
                        AlarmScheduler.scheduleBirthday(
                            context,
                            b.id,
                            b.name,
                            b.year,
                            b.month,
                            b.day,
                            b.hour,
                            b.minute
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("BirthdayAlarmReceiver", "Error rescheduling alarm", e)
            } finally {
                 pendingResult.finish()
            }
        }
    }
}