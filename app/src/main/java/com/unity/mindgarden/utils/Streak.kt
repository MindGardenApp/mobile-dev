package com.unity.mindgarden.utils

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Calendar
import java.util.Date
import android.util.Log

fun calculateStreak(
    db: FirebaseFirestore,
    userId: String,
    label: String
) {
    val todayCalendar = Calendar.getInstance()

    db.collection("users")
        .document(userId)
        .get()
        .addOnSuccessListener { document ->
            val streakData = document.get("streak") as? Map<*, *>
            val lastStreakDate = streakData?.get("last")

            if (lastStreakDate != null) {
                val lastStreakCalendar = dateToCalendar((lastStreakDate as Timestamp).toDate())

                if (
                    lastStreakCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                    lastStreakCalendar.get(Calendar.WEEK_OF_YEAR) == todayCalendar.get(Calendar.WEEK_OF_YEAR)
                ) {
                    updateStreak(
                        db,
                        userId,
                        todayCalendar,
                        lastStreakCalendar,
                        label
                    )
                } else {
                    resetStreak(
                        db,
                        userId,
                        todayCalendar,
                        lastStreakCalendar,
                        label
                    )
                }
            } else {
                resetStreak(
                    db,
                    userId,
                    todayCalendar,
                    label = label
                )
            }
        }
}

fun resetStreak(
    db: FirebaseFirestore,
    userId: String,
    todayCalendar: Calendar,
    lastStreakCalendar: Calendar? = null,
    label: String
) {
    db.collection("users")
        .document(userId)
        .set(
            hashMapOf(
                "streak" to mutableMapOf(
                    "count" to FieldValue.increment(1),
                    "last" to Timestamp(todayCalendar.time),
                    "weekly" to mutableMapOf(
                        "sunday" to (todayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY),
                        "monday" to (todayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY),
                        "tuesday" to (todayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY),
                        "wednesday" to (todayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY),
                        "thursday" to (todayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY),
                        "friday" to (todayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY),
                        "saturday" to (todayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    )
                ),
                "stats" to mutableMapOf(
                    "joy" to if (label == "joy") 1 else 0,
                    "sadness" to if (label == "sadness") 1 else 0,
                    "anger" to if (label == "anger") 1 else 0,
                    "fear" to if (label == "fear") 1 else 0,
                    "love" to if (label == "love") 1 else 0,
                    "surprise" to if (label == "surprise") 1 else 0
                )
            ),
            SetOptions.merge()
        )

    if (
        lastStreakCalendar == null ||
        todayCalendar.get(Calendar.DAY_OF_YEAR) - 1 != lastStreakCalendar.get(Calendar.DAY_OF_YEAR)
    ) {
            if (todayCalendar.get(Calendar.DAY_OF_YEAR) == lastStreakCalendar?.get(Calendar.DAY_OF_YEAR)) {
                db.collection("users")
                    .document(userId)
                    .update(
                        "streak.count", FieldValue.increment(-1)
                    )
            } else {
                db.collection("users")
                    .document(userId)
                    .update(
                        "streak.count", 1
                    )
            }
    }
}

fun updateStreak(
    db: FirebaseFirestore,
    userId: String,
    todayCalendar: Calendar,
    lastStreakCalendar: Calendar,
    label: String
) {
    val dayKey = when (todayCalendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "sunday"
        Calendar.MONDAY -> "monday"
        Calendar.TUESDAY -> "tuesday"
        Calendar.WEDNESDAY -> "wednesday"
        Calendar.THURSDAY -> "thursday"
        Calendar.FRIDAY -> "friday"
        Calendar.SATURDAY -> "saturday"
        else -> return
    }

    db.collection("users")
        .document(userId)
        .update(
            "streak.count", FieldValue.increment(1),
            "streak.last", Timestamp(todayCalendar.time),
            "streak.weekly.$dayKey", true,
            "stats.$label", FieldValue.increment(1) // Assuming joy is the label for this example
        )

    if (todayCalendar.get(Calendar.DAY_OF_YEAR) - 1 != lastStreakCalendar.get(Calendar.DAY_OF_YEAR)) {
        if (todayCalendar.get(Calendar.DAY_OF_YEAR) == lastStreakCalendar.get(Calendar.DAY_OF_YEAR)) {
            db.collection("users")
                .document(userId)
                .update(
                    "streak.count", FieldValue.increment(-1)
                )
        } else {
            db.collection("users")
                .document(userId)
                .update(
                    "streak.count", 1
                )
        }
    }
}

fun dateToCalendar(date: Date): Calendar {
    return Calendar.getInstance().apply {
        time = date
    }
}