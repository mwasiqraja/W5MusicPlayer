package com.example.w5musicplayer.ui.utils

import android.media.MediaPlayer
import android.util.Log
import com.example.w5musicplayer.ui.SharedViewModel
import org.koin.android.ext.android.inject

object ObjectConstant {

    lateinit var viewModel: SharedViewModel
    var per=0

    fun getViewModelInstance( myviewModel: SharedViewModel){
        viewModel=myviewModel
    }
    fun callFromService(){
        Log.d("TAG6", "callFromService: ")
    }
    var mediaPlayer:MediaPlayer?=null

    fun getInstance():MediaPlayer{

        if(mediaPlayer==null)
        {
            mediaPlayer= MediaPlayer()
        }

        return mediaPlayer!!
    }

}