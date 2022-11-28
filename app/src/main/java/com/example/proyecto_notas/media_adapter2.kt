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

class media_adapter2(var media: List<Media>): RecyclerView.Adapter<media_adapter2.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){


        var img_tumbnail : ImageView
        var media_description : TextView


        init{
            img_tumbnail = v.findViewById(R.id.big_tumbnail)
            media_description = v.findViewById(R.id.big_description)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.media_row_2, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = media[position]
        holder.img_tumbnail.setImageURI( Uri.parse(p.path))
        var x = -90
        holder.img_tumbnail.rotation = x.toFloat()
        holder.media_description.text = p.description

    }

    override fun getItemCount(): Int {
        return media.size
    }

}