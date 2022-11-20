package com.example.proyecto_notas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Note

class task_adapter(var tasks: List<Note>): RecyclerView.Adapter<task_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var name : TextView
        var description : TextView
        var date_hour : TextView
        var btn_delete: ImageView
        var btn_edit: ImageView
        var cb_completed : CheckBox
        init{
            name = v.findViewById(R.id.txt_title_note)
            description = v.findViewById(R.id.txt_description_note)
            date_hour = v.findViewById(R.id.txt_date_hour)
            btn_delete = v.findViewById(R.id.btn_delete)
            btn_edit = v.findViewById(R.id.btn_edit)
            cb_completed = v.findViewById(R.id.cb_completed)

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
        holder.date_hour.text = p.date+" "+p.hour
        holder.cb_completed.isChecked = p.completed
        holder.btn_delete.setOnClickListener{view : View ->
            noteDatabase.getDatabase(holder.name.context).noteDao().deleteNote(p)
            var notes  = noteDatabase.getDatabase(holder.name.context).noteDao().getAllTasks()
            this.tasks = notes
            this.notifyItemRemoved(position)
        }

        holder.cb_completed.setOnClickListener{
            noteDatabase.getDatabase(holder.name.context).noteDao().updateTask(p.title,p.description,p.date,p.hour,holder.cb_completed.isChecked,p.id)
            var notes  = noteDatabase.getDatabase(holder.name.context).noteDao().getAllTasks()
            this.tasks = notes
            this.notifyDataSetChanged()
        }


        holder.btn_edit.setOnClickListener{ view : View ->
            var bundle = Bundle()
            bundle.putString("id",p.id.toString())
            bundle.putString("title", p.title)
            bundle.putString("description", p.description)
            bundle.putString("type", p.type.toString())
            bundle.putString("date", p.date)
            bundle.putString("hour", p.hour)
            bundle.putString("completed", p.completed.toString())
            view.findNavController().navigate(R.id.action_task_list_to_add_note,bundle)

        }

    }


    override fun getItemCount(): Int {
        return tasks.size
    }







}