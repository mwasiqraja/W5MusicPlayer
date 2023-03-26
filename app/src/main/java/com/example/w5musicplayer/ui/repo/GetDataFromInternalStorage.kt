package com.example.w5musicplayer.ui.repo

import android.app.Application
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.w5musicplayer.ui.AudioModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class GetDataFromInternalStorage(var application: Application) {

    private val mp3List = MutableLiveData<ArrayList<AudioModel>>()
    val mp3ListObservable: LiveData<ArrayList<AudioModel>> = mp3List


    suspend fun getMp3Files() = withContext(IO) {

        val audioList = ArrayList<AudioModel>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DISPLAY_NAME,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DURATION
        )

        val cursor = application.contentResolver.query(uri, projection, null, null, null)

        if (cursor != null) {

            while (cursor.moveToNext()) {

                val audioObj = AudioModel()

                audioObj.songName = cursor.getString(0)
                audioObj.dataPath = cursor.getString(1)
//                audioObj.songDuration = cursor.getString(2).toLong()

                if (cursor.getString(1).endsWith(".mp3"))
                    audioList.add(audioObj)

                mp3List.postValue(audioList)
            }

            cursor.close()
        }

    }


}