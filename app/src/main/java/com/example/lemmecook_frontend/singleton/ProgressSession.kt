package com.example.lemmecook_frontend.singleton

import com.example.lemmecook_frontend.models.health.ProgressDataModel
import com.example.lemmecook_frontend.utilities.DateTimeUtility
import com.example.lemmecook_frontend.utilities.ProgressApiUtility

object ProgressSession {
    var progress: ProgressDataModel = ProgressApiUtility.getDefaultProgress(
        DateTimeUtility.getCurrentDateAsString()
    )
}