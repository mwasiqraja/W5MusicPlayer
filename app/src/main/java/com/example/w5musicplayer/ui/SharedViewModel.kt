package com.example.w5musicplayer.ui

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.w5musicplayer.ui.repo.GetDataFromInternalStorage
import com.example.w5musicplayer.ui.utils.ObjectConstant.getInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File


class SharedViewModel(application: Application, repo: GetDataFromInternalStorage) :
    AndroidViewModel(application) {


    var mp3List: LiveData<ArrayList<AudioModel>>? = repo.mp3ListObservable


    init {
        CoroutineScope(IO).launch {
            repo.getMp3Files()
        }

    }


    fun playMedia(path: String) {


        val file = File(path)
        if (file.exists()) {
            Log.d("TAG7", "File Exist")

            getInstance().let {
                Log.d("TAG1", "playMedia: $it")
                it.reset()
                it.setDataSource(path)
                it.setOnPreparedListener {

                }
                it.prepare()
                it.start()
            }
        } else {
            Log.d("TAG7", "No File Exist")

        }

    }

    fun pauseMedia() {

        Log.d("TAG1", "stopMedia: ${getInstance()}")

        getInstance().apply {

            if (isPlaying) {
                pause()
            }
        }
    }

    fun startMedia() {
        Log.d("TAG1", "startMedia: ${getInstance()}")
        getInstance().start()
    }

    /*fun playMedia(path:String) {


        val file = File(path)
        if (file.exists()) {
            Log.d("TAG7", "File Exist")

            getInstance().let {
                Log.d("TAG1", "playMedia: $it")
                it.reset()
                it.setDataSource(path)
                it.setOnPreparedListener {

                }
                it.prepare()
                it.start()
            }
        }
        else{
            Log.d("TAG7", "No File Exist")

        }

    }

    fun stopMedia() {

        Log.d("TAG1", "stopMedia: ${getInstance()}")

        getInstance().apply {

            if (isPlaying) {
                stop()
            }
        }
    }

    fun startMedia() {
        Log.d("TAG1", "startMedia: ${getInstance()}")
        getInstance().start()
    }*/

    override fun onCleared() {
        super.onCleared()
        /*mediaPlayer?.release()
        mediaPlayer = null*/
    }

}