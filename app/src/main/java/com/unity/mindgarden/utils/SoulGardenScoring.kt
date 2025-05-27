package com.unity.mindgarden.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.unity.mindgarden.model.PredictionResponse

fun scoreUpdate(
    db: FirebaseFirestore,
    userId: String,
    prediction: PredictionResponse
) {
    val label = prediction.label
    val score = prediction.score

    db.collection("users")
        .document(userId)
        .get()
        .addOnSuccessListener { document ->
            val soulGarden = document.get("soul_garden")!! as Map<*, *>

            var currentScore = soulGarden["currentScore"] as Long
            var state = soulGarden["state"] as Long
            var status = soulGarden["status"] as Long

            currentScore += score.toLong()

            when (state) {
                1L -> {
                    if (currentScore <= 50) {
                        status = 1
                    } else if (currentScore <= 100) {
                        status = 2
                    } else if (currentScore <= 150) {
                        status = 3
                    } else {
                        state = 2
                        status = 3
                        currentScore = 301
                    }
                }

                2L -> {
                    if (currentScore <= 100) {
                        status = 1
                    } else if (currentScore <= 200) {
                        status = 2
                    } else if (currentScore <= 300) {
                        status = 3
                    } else {
                        state = 3
                        status = 3
                        currentScore = 351
                    }
                }

                3L -> {
                    if (currentScore <= 150) {
                        status = 1
                    } else if (currentScore <= 300) {
                        status = 2
                    } else if (currentScore <= 350) {
                        status = 3
                    } else {
                        state = 4
                        status = 3
                        currentScore = 601
                    }
                }

                4L -> {
                    if (currentScore <= 200) {
                        status = 1
                    } else if (currentScore <= 400) {
                        status = 2
                    } else if (currentScore <= 600) {
                        status = 3
                    } else {
                        state = 5
                        status = 3
                        currentScore = 751
                    }
                }

                5L -> {
                    if (currentScore <= 250) {
                        status = 1
                    } else if (currentScore <= 500) {
                        status = 2
                    } else if (currentScore <= 750) {
                        status = 3
                    }
                }
            }

            db.collection("users")
                .document(userId)
                .update(
                    "soul_garden.currentScore",currentScore,
                    "soul_garden.state", state,
                    "soul_garden.status", status
                )
        }
}