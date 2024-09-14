package com.example.lemmecook_frontend.singleton

import android.net.Uri

object UserSession {
    var userId: String? = null
    var fullName: String? = null
    var username: String? = null
    var avatar: Uri? = null

    fun clear() {
        userId = null
        fullName = null
        username = null
        avatar = null
    }
}