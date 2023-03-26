package com.example.w5musicplayer.ui

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.w5musicplayer.ui.broadcast.RestartServiceReciever

fun setUpPlayIntent(baseContext: Context):PendingIntent{

    val playIntent = Intent(baseContext, RestartServiceReciever::class.java)
        .setAction("Play")

    return  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        PendingIntent.getBroadcast(
            baseContext, 0, playIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    } else {
        PendingIntent.getBroadcast(baseContext, 0, playIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

}

fun setUpPauseIntent(baseContext: Context):PendingIntent{

    val pauseIntent = Intent(baseContext, RestartServiceReciever::class.java)
        .setAction("Pause")

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


        PendingIntent.getBroadcast(
            baseContext, 0, pauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    } else {
        PendingIntent.getBroadcast(baseContext, 0, pauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

}

fun setUpServiceFinishIntent(baseContext: Context):PendingIntent{

    val finishIntent = Intent(baseContext, RestartServiceReciever::class.java)
        .setAction("Stop")
    //.putExtra("ExtraData","stopdata")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        PendingIntent.getBroadcast(
            baseContext, 0, finishIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    } else {
        PendingIntent.getBroadcast(baseContext, 0, finishIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }
}