package com.example.proyecto_notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Note
import kotlinx.coroutines.launch


class task_list : Fragment() {

    lateinit var notes : List<Note>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_task_list, container, false)

        val rv = root?.findViewById<RecyclerView>(R.id.rv_task_list)

        lifecycleScope.launch{
             notes  = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllNotes()
        }

        rv?.adapter = task_adapter(notes)
        rv?.layoutManager = LinearLayoutManager(this@task_list.requireContext())
        // Inflate the layout for this fragment


        return root.rootView
    }

}