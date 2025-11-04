package com.lifeandme.birthdaysapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.lifeandme.core.util.AlarmScheduler
import com.lifeandme.domain.repository.BirthdayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var birthdayRepository: BirthdayRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Device rebooted — restoring alarms")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val birthdays = birthdayRepository.getAllBirthdays()
                    birthdays.collect { list ->
                        list.forEach { birthday ->
                            AlarmScheduler.scheduleBirthday(
                                context,
                                birthday.id,
                                birthday.name,
                                birthday.year,
                                birthday.month,
                                birthday.day,
                                birthday.hour,
                                birthday.minute
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e("BootReceiver", "Error restoring alarms", e)
                }
            }
        }
    }
}