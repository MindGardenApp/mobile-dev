package com.unity.mindgarden.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    companion object {
        private const val PREF_NAME = "user_session"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_IS_ONBOARDING_COMPLETED = "isOnboardingCompleted"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }

    // ✅ Tambahan untuk status onboarding
    fun setOnboardingCompleted(isCompleted: Boolean) {
        editor.putBoolean(KEY_IS_ONBOARDING_COMPLETED, isCompleted)
        editor.apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return sharedPref.getBoolean(KEY_IS_ONBOARDING_COMPLETED, false)
    }
}
