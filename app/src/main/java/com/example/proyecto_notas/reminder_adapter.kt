package com.example.proyecto_notas

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView

class reminder_adapter(val reminders: List<String>): RecyclerView.Adapter<reminder_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var name : TextView
        init{
            name = v.findViewById(R.id.row_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reminder_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = reminders[position]
        holder.name.text = p.toString()
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

}