package com.example.proyecto_notas

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Media
import com.example.proyecto_notas.model.Reminder

class media_adapter(var media: List<Media>): RecyclerView.Adapter<media_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){


        var img_tumbnail : ImageView
        var media_description : TextView
        var btn_delete : ImageView

        init{
            img_tumbnail = v.findViewById(R.id.img_tumbnail)
            media_description = v.findViewById(R.id.media_description)
            btn_delete = v.findViewById(R.id.btn_delete3)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.media_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = media[position]
        holder.img_tumbnail.setImageURI( Uri.parse(p.path))
        var x = -90
        holder.img_tumbnail.rotation = x.toFloat()
        holder.media_description.text = p.description
        var noteID = p.noteID
        holder.btn_delete.setOnClickListener{view : View ->
            noteDatabase.getDatabase(holder.img_tumbnail.context).mediaDao().deleteMedia(p)
            var media  = noteDatabase.getDatabase(holder.img_tumbnail.context).mediaDao().getAllMedia(noteID)
            this.media = media
            this.notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return media.size
    }

}