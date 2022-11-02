package com.example.proyecto_notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.proyecto_notas.databinding.FragmentAddNoteBinding

//import com.example.proyecto_notas.databinding.FragmentAddNoteBinding



class Add_note : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddNoteBinding>(inflater,
            R.layout.fragment_add_note,container,false)

        binding.btnNext.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_add_note_to_edit_task)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}