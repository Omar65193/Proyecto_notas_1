package com.example.proyecto_notas

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyecto_notas.model.Reminder

class reminder_adapter(val reminders: List<Reminder>): RecyclerView.Adapter<reminder_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var time : TextView
        var date : TextView
        init{
            time = v.findViewById(R.id.reminder_time)
            date = v.findViewById(R.id.reminder_date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reminder_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = reminders[position]
        holder.time.text = p.time
        holder.date.text = p.date

    }

    override fun getItemCount(): Int {
        return reminders.size
    }

}