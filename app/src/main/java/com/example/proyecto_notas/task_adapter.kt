package com.example.proyecto_notas

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyecto_notas.model.Note

class task_adapter(val tasks: List<Note>): RecyclerView.Adapter<task_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var name : TextView
        var description : TextView
        init{
            name = v.findViewById(R.id.txt_title_task)
            description = v.findViewById(R.id.txt_description_task)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.task_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = tasks[position]
        holder.name.text = p.title.toString()
        holder.description.text =p.description.toString()
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}