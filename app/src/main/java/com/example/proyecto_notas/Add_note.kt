package com.example.proyecto_notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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

        var txt_title = binding.edtxtTitle;
        var sp_note_type = binding.spNoteType;
        binding.btnNext.setOnClickListener{view : View ->
            if(txt_title.length()>0){

                if(!sp_note_type.selectedItemId.toString().equals("0")){
                    view.findNavController().navigate(R.id.action_add_note_to_edit_task)
                }else{
                    Toast.makeText(activity, "Debes indicar el tipo de nota", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(activity, "Debes agregar un título", Toast.LENGTH_SHORT).show()

            }
        }
        binding.btnCancel.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_add_note_to_mainMenu)
        }

        // Inflate the layout for this fragment
        return binding.root
    }



}