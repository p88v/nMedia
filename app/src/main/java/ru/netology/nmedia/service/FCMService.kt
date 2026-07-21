package ru.netology.nmedia.service

import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        println(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        println(message)
    }
}