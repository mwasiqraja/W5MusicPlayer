package com.example.w5musicplayer.ui

import android.app.*
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.w5musicplayer.R
import com.example.w5musicplayer.ui.broadcast.RestartServiceReciever
import com.example.w5musicplayer.ui.utils.ObjectConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class MusicService : Service() {

    var mediaPlayer: MediaPlayer? = null
    lateinit var restartServiceReciever: RestartServiceReciever
    lateinit var localBroadcastManager: LocalBroadcastManager
    var CHANNEL_ID = "CHANNEL3"
    var PAUSE = "pause"
    var PLAY = "play"
    var STOP = "stop"


    private val songViewModel: SharedViewModel by inject()



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        restartServiceReciever = RestartServiceReciever()

        localBroadcastManager = LocalBroadcastManager.getInstance(this)

        localBroadcastManager.registerReceiver(restartServiceReciever,
            IntentFilter("restartservice"))

        createNotificationChannel()
        //startForegroundOwn()


        //startMyOwnForeground()

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG5", "onStartCommand: ${intent?.getStringExtra("ExtraData")}")

        intent?.getStringExtra("ExtraData").also {
            it?.let {
                songViewModel.playMedia(it)
            }
        }

        CoroutineScope(IO).launch {
            await()
        }



        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Static Title")
            .setContentText("Foreground Service")
            //.setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            //.addAction(R.drawable.ic_baseline_pause_24, "Upload",)
            .addAction(R.drawable.ic_baseline_pause_24, "pause", setUpPauseIntent(baseContext))
            .addAction(R.drawable.ic_baseline_play_arrow_24, "play", setUpPlayIntent(baseContext))
            .addAction(R.drawable.ic_baseline_stop_24, "stop", setUpServiceFinishIntent(baseContext))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(null)
            .setAutoCancel(true)
            .build()


        startForeground(1, notification)


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        /*val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.putExtra("FULLPATH", actualPath)
        broadcastIntent.setClass(this, RestartServiceReciever::class.java)
        localBroadcastManager.sendBroadcast(broadcastIntent)
         */
        ObjectConstant.mediaPlayer?.release()
        ObjectConstant.mediaPlayer=null
        Log.d("TAG5", "onDestroy: BreakUp -> Wasiq")
    }


    private suspend fun await(){

        for(i in 1..10){
            ObjectConstant.per=i
            Log.d("TAG7", "await: $i")
            delay(1000)
        }
        withContext(Main){
            Toast.makeText(baseContext,"Uploading Files",Toast.LENGTH_SHORT).show()

        }
        await()

    }
    private fun createNotificationChannel() {

        /**
         *  for version 26 and above
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)

        }
    }

}