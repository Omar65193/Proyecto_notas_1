package com.example.proyecto_notas


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Note
import com.example.proyecto_notas.model.Reminder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class edit_task : Fragment() {


    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    lateinit var title :String
    lateinit var description :String
    lateinit var newReminder: Reminder
    val root : View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var seleccionado = 0
        val root = inflater.inflate(R.layout.fragment_edit_task, container, false)
        val btn_date = root.findViewById<Button>(R.id.btn_date)
        val btn_add_reminder = root.findViewById<Button>(R.id.btn_add_reminder)
        textview_date = root.findViewById(R.id.textview_date)
        var txt_hour = root.findViewById<TextView>(R.id.txt_hour)



        var id = -1
        var completed = false
        var tempListReminders : List<Reminder> = emptyList()
        val rv = root?.findViewById<RecyclerView>(R.id.rv_reminders)
        var reminders : MutableList<Reminder> = mutableListOf<Reminder>()


        setFragmentResultListener("key") { requestKey, bundle ->
            title = bundle.getString("title").toString()

            description = bundle.getString("description").toString()

            if(bundle.getString("date")!=null){
                textview_date!!.text = bundle.getString("date")
                txt_hour.text = bundle.getString("hour")
                id = bundle.getString("id")!!.toInt()
                completed = bundle.getString("completed").toBoolean()
            }

            lifecycleScope.launch {
                reminders = noteDatabase.getDatabase(requireActivity().applicationContext).reminderDAO().getAllReminders(id)
                reminder_adapter(reminders)
                rv?.adapter!!.notifyDataSetChanged()
                rv?.adapter = reminder_adapter(reminders)
            }


            root.findViewById<TextView>(R.id.txt_title_edit_task).text = title


        }




        root.findViewById<Button>(R.id.btn_end).setOnClickListener{view : View ->
            lifecycleScope.launch{
                var note = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getById(id)
                if(!note.toString().equals("[]")){
                    noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().updateTask(title.toUpperCase(),description.toUpperCase(),textview_date!!.text.toString(),txt_hour.text.toString(),completed,id)
                }else{
                    val newNote = com.example.proyecto_notas.model.Note(title.toUpperCase(),description.toUpperCase(),1,textview_date!!.text.toString(),txt_hour.text.toString(),false)
                    noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().insert(newNote)

                }
                var notes : List<Note> = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllTasks()

                Toast.makeText(this@edit_task.requireContext(),notes.size.toString(), Toast.LENGTH_SHORT).show()

            }
            view.findNavController().navigate(R.id.action_edit_task_to_task_list)


        }

        var selected_hour = cal.get(Calendar.HOUR_OF_DAY);
        var selected_minute = cal.get(Calendar.MINUTE);



        root.findViewById<Button>(R.id.btn_cancel_edit_task).setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_edit_task_to_add_note)
        }
        var id_maximo = 0
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                if(seleccionado==0){
                    updateDateInView(textview_date!!)
                }else{

                    lifecycleScope.launch{
                        var note = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getById(id)

                        if(!note.toString().equals("[]")) {
                            newReminder = Reminder(id,updateDateInView(),"$selected_hour:$selected_minute")
                        }else{

                                id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getMaxID()
                            newReminder = Reminder(id_maximo+1,updateDateInView(),"$selected_hour:$selected_minute")

                        }
                        noteDatabase.getDatabase(requireActivity().applicationContext).reminderDAO().insert(newReminder)
                        if(id!=-1){
                             reminders = noteDatabase.getDatabase(requireActivity().applicationContext).reminderDAO().getAllReminders(id)
                        }else{

                             reminders = noteDatabase.getDatabase(requireActivity().applicationContext).reminderDAO().getAllReminders(id_maximo+1)
                        }
                        Toast.makeText(this@edit_task.requireContext(),reminders.size.toString(), Toast.LENGTH_SHORT).show()
                        reminder_adapter(reminders)
                        rv?.adapter!!.notifyDataSetChanged()
                        rv?.adapter = reminder_adapter(reminders)
                    }

                }
            }
        }



        var mHour = cal.get(Calendar.HOUR_OF_DAY);
        var mMinute = cal.get(Calendar.MINUTE);



        val timeSetListener =
            OnTimeSetListener { view, hourOfDay, minute ->
                selected_hour = hourOfDay
                selected_minute = minute
                if(seleccionado == 0){
                    txt_hour.setText("$hourOfDay:$minute")
                }

                if (container != null) {
                    DatePickerDialog(this@edit_task.requireContext(),
                        dateSetListener,

                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()

                }


            }



        btn_date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                seleccionado = 0
                TimePickerDialog(this@edit_task.requireContext(),timeSetListener
                    ,mHour,mMinute,true).show()
            }
        })

        btn_add_reminder.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                seleccionado = 1
                TimePickerDialog(this@edit_task.requireContext(),timeSetListener
                    ,mHour,mMinute,true).show()
            }

        })




        rv?.adapter = reminder_adapter(reminders)
        rv?.layoutManager = LinearLayoutManager(this@edit_task.requireContext())




        return root.rootView
    }


    private fun updateDateInView(txt_date: TextView) {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txt_date!!.text = sdf.format(cal.getTime())
    }

    private fun updateDateInView(): String{
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(cal.getTime()).toString()
    }


}