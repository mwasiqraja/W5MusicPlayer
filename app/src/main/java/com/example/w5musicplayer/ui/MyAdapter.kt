package com.example.w5musicplayer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.w5musicplayer.R
import com.example.w5musicplayer.ui.`interface`.Listener
import org.koin.android.ext.android.inject


class MyAdapter(var list: ArrayList<AudioModel>,val listener: Listener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.audio_list, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){

        holder.tvSongName.text=list[position].songName

        holder.itemView.setOnClickListener {
            listener.getSongPath(list[position].dataPath)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvSongName = itemView.findViewById<TextView>(R.id.song_Name)

    }
}
