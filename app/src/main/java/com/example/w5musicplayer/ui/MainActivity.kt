package com.example.w5musicplayer.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mwasiqforegroundservice.showToast
import com.example.w5musicplayer.databinding.ActivityMainBinding
import com.example.w5musicplayer.ui.`interface`.Listener
import com.example.w5musicplayer.ui.broadcast.RestartServiceReciever
import com.example.w5musicplayer.ui.utils.ObjectConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), Listener {


    private val songViewModel: SharedViewModel by inject()
    lateinit var restartServiceReciever: RestartServiceReciever
    lateinit var localBroadcastManager: LocalBroadcastManager

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ObjectConstant.getViewModelInstance(songViewModel)
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)


        /*localBroadcastManager=LocalBroadcastManager.getInstance(this)
        restartServiceReciever= RestartServiceReciever()*/

        binding.btnLoadList.setOnClickListener {


            CoroutineScope(Main).launch {

                songViewModel.mp3List?.observe(this@MainActivity) {

                    if (it.isNotEmpty()) {

                        binding.recList.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.recList.adapter = MyAdapter(it, this@MainActivity)

                    }
                }

            }


        }

        binding.btnStop.setOnClickListener {
            //songViewModel.stopMedia()
        }



        binding.btnStart.setOnClickListener {
            //songViewModel.startMedia()
        }


    }

    override fun getSongPath(path: String) {

        Intent(this, MusicService::class.java).also {
            ContextCompat.startForegroundService(this, it.putExtra("ExtraData", path))

        }
        /*
        songViewModel.playMedia(path)*/
        //callReciever()
    }

    /* override fun onStart() {
         super.onStart()

         localBroadcastManager.registerReceiver(restartServiceReciever, IntentFilter("restartservice"))
     }

     override fun onStop() {
         super.onStop()
         localBroadcastManager.unregisterReceiver(restartServiceReciever)
     }*/

    fun callReciever() {

        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.putExtra("ED", "bava g")
        broadcastIntent.setClass(this, RestartServiceReciever::class.java)
        localBroadcastManager.sendBroadcast(broadcastIntent)

    }


}