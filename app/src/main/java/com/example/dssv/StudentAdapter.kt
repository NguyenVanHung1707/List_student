package com.example.dssv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class StudentAdapter(
    context: Context,
    private val resource: Int,
    private val students: MutableList<Student>
) : ArrayAdapter<Student>(context, resource, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val student = students[position]

        val nameTextView = view.findViewById<TextView>(R.id.textViewName)
        val studentIdTextView = view.findViewById<TextView>(R.id.textViewStudentId)
        val deleteButton = view.findViewById<Button>(R.id.buttonDelete)

        nameTextView.text = student.name
        studentIdTextView.text = student.studentId

        deleteButton.setOnClickListener {
            // Remove the student from the list and notify the adapter
            students.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }
}