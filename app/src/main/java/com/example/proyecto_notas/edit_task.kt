package com.example.proyecto_notas


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import java.text.SimpleDateFormat
import java.util.*


class edit_task : Fragment() {


    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val root = inflater.inflate(R.layout.fragment_edit_task, container, false)
        val btn_date = root.findViewById<Button>(R.id.btn_date)
        textview_date = root.findViewById(R.id.textview_date)
        var txt_hour = root.findViewById<TextView>(R.id.txt_hour)

        root.findViewById<Button>(R.id.btn_cancel_edit_task).setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_edit_task_to_add_note)
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // Get Current Time

        var mHour = cal.get(Calendar.HOUR_OF_DAY);
        var mMinute = cal.get(Calendar.MINUTE);

        val timeSetListener =
            OnTimeSetListener { view, hourOfDay, minute ->
                txt_hour.setText("$hourOfDay:$minute")
                //lastSelectedHour = hourOfDay
                //lastSelectedMinute = minute
            }

        // Launch Time Picker Dialog
        /*
         btn_date.setOnClickListener(object : View.OnClickListener {
             override fun onClick(view: View) {

             }
         })
        */

        btn_date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                if (container != null) {
                    DatePickerDialog(this@edit_task.requireContext(),
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
                }
                TimePickerDialog(this@edit_task.requireContext(),timeSetListener
                    ,mHour,mMinute,true).show()
            }

        })


        return root.rootView
    }
    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())
    }


}