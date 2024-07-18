package com.example.battleship.utils

import android.provider.Settings
import android.text.TextUtils
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader

object NavigationUtils {
    private val isMIUI: Boolean by lazy {
        !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"))
    }
    fun isGestureNav(insets: WindowInsetsCompat): Boolean {
        if (isMIUI) {
            val miNavBarMode =
                Settings.Global.getInt(AppUtils.getApp().contentResolver, "force_fsg_nav_bar", 0)
            return miNavBarMode != 0
        }
        val inset = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
        return inset.left > 0
    }

    private fun getSystemProperty(propName: String): String? {
        return try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            BufferedReader(InputStreamReader(p.inputStream), 1024).use {
                it.readLine()
            }
        } catch (_: Exception) {
            return null
        }
    }
}