package com.lifeandme.core.util

interface IPermissionManager {
    /**
     * Checks if the POST_NOTIFICATIONS permission is granted (API 33+).
     */
    fun isNotificationPermissionGranted(context: android.content.Context): Boolean

    /**
     * Checks if the exact alarm permission is granted (API 31+).
     */
    fun canScheduleExactAlarms(context: android.content.Context): Boolean

    /**
     * Creates an Intent to navigate the user to the exact alarm settings page.
     */
    fun createExactAlarmIntent(): android.content.Intent
}