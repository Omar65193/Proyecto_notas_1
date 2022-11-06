package com.example.proyecto_notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.databinding.FragmentAddNoteBinding
import com.example.proyecto_notas.model.Note
import kotlinx.coroutines.launch

//import com.example.proyecto_notas.databinding.FragmentAddNoteBinding



class Add_note : Fragment() {

    var txt_title : EditText? = null
    var txt_description : EditText? = null
    lateinit var sp_note_type: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddNoteBinding>(inflater,
            R.layout.fragment_add_note,container,false)





        txt_title = binding.edtxtTitle
        txt_description = binding.edtxtDescription

        //VERIFICAR SI YA EXISTE PARA ESO NOS VAMOS A TRAER EL ID Y TAMBIEN DESHABILITAR EL TIPO DE NOTA
         sp_note_type = binding.spNoteType;
        var id = -1
        if(arguments?.getString("title")!=null) {

            txt_title?.setText(arguments?.getString("title"))
            txt_description?.setText(arguments?.getString("description"))
            sp_note_type.setSelection(arguments?.getString("type")!!.toInt())
            id = arguments?.getString("id")!!.toInt()
            if (!sp_note_type.selectedItem.toString().equals("0")) {
                sp_note_type.isEnabled = false
            }
        }

        binding.btnNext.setOnClickListener{view : View ->
            if(txt_title!!.length()>0){

                if(sp_note_type.selectedItemId.toString().equals("1")){
                    if(txt_description!!.length()>0){
                        var bundle = Bundle()
                        bundle.putString("title", txt_title!!.text.toString())
                        bundle.putString("description", txt_description!!.text.toString())
                        if(!sp_note_type.isEnabled){
                            bundle.putString("date",arguments?.getString("date"))
                            bundle.putString("hour",arguments?.getString("hour"))
                            bundle.putString("completed",arguments?.getString("completed"))
                            bundle.putString("id",arguments?.getString("id"))
                        }

                        parentFragmentManager.setFragmentResult("key",bundle)
                        view.findNavController().navigate(R.id.action_add_note_to_edit_task)
                    }else{
                        Toast.makeText(activity, "Debes agregar una descripción", Toast.LENGTH_SHORT).show()
                    }

                }else if(sp_note_type.selectedItemId.toString().equals("2")){

                    lifecycleScope.launch{
                        var note = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getById(id)
                        if(!note.toString().equals("[]")){

                            noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().updateNote(txt_title!!.text.toString().toUpperCase(),txt_description!!.text.toString().toUpperCase(),id)
                            var notes : List<Note> = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllNotes()

                        }else{
                            val newNote = Note( txt_title!!.text.toString().toUpperCase(),txt_description!!.text.toString(),2,"","",false)
                            noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().insert(newNote)
                            var notes : List<Note> = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllNotes()

                        }


                        //Toast.makeText(this@Add_note.requireContext(),notes.size.toString(), Toast.LENGTH_SHORT).show()
                        view.findNavController().navigate(R.id.action_add_note_to_note_list)
                    }

                }else{
                    Toast.makeText(activity, "Debes elegir un tipo de nota", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(activity, "Debes agregar un título", Toast.LENGTH_SHORT).show()

            }
        }
        binding.btnCancel.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_add_note_to_mainMenu)
        }

        sp_note_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var btn_next = binding.btnNext;
                if(position==2){
                    btn_next.text = "Terminar"
                }else if(position==1){
                    btn_next.text = "Siguiente"
                }
            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }




}