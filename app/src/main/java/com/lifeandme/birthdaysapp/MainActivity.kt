package com.lifeandme.birthdaysapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.lifeandme.birthdaysapp.ui.theme.BirthdaysAppTheme
import com.lifeandme.core.util.IPermissionManager
import com.lifeandme.feature_birthdays.birthday.presentation.birthday_screen.BirthdayScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var permissionManager: IPermissionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdaysAppTheme {
                BirthdayScreen()
            }
        }
        requestAllRequiredPermissions()
    }

    private val requestNotificationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            checkAndRequestExactAlarmAccess()
        }
    }

    private val startSettingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (!permissionManager.canScheduleExactAlarms(this)) {
            Toast.makeText(this, "هنوز مجوزی صادر نشده است!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestAllRequiredPermissions() {
        if (permissionManager.isNotificationPermissionGranted(this)) {
            checkAndRequestExactAlarmAccess()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                checkAndRequestExactAlarmAccess()
            }
        }
    }

    private fun checkAndRequestExactAlarmAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!permissionManager.canScheduleExactAlarms(this)) {
                val intent = permissionManager.createExactAlarmIntent()
                startSettingsLauncher.launch(intent)
            }
        }
    }
}
