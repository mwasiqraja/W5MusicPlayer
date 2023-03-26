package com.example.w5musicplayer.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.w5musicplayer.ui.MusicService
import com.example.w5musicplayer.ui.utils.ObjectConstant


class RestartServiceReciever() : BroadcastReceiver() {





    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("TAG71", "onReceive: ${intent?.action}")
        if(intent?.action=="Pause") {
            ObjectConstant.viewModel.pauseMedia()
        }
        else if(intent?.action=="Play"){
            ObjectConstant.viewModel.startMedia()
        }
        else if(intent?.action=="Stop"){
            Log.d("TAG71", "about to stop service: ")
            context?.stopService(Intent(context, MusicService::class.java))
        }



    }

}
