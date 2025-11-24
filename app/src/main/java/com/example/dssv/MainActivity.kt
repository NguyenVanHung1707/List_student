package com.example.dssv

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextStudentId: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonUpdate: Button
    private lateinit var listViewStudents: ListView

    private val studentList = mutableListOf(
        Student("Nguyễn Văn An", "20210001"),
        Student("Trần Thị Bình", "20210055"),
        Student("Lê Văn Cường", "20205012"),
        Student("Phạm Thị Dung", "20221234"),
        Student("Hoàng Minh Hải", "20194567")
    )
    private lateinit var adapter: StudentAdapter
    private var selectedStudentIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName)
        editTextStudentId = findViewById(R.id.editTextStudentId)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        listViewStudents = findViewById(R.id.listViewStudents)

        // Setup Adapter
        adapter = StudentAdapter(this, R.layout.list_item_student, studentList)
        listViewStudents.adapter = adapter

        // Set Listeners
        setupListeners()
    }

    private fun setupListeners() {
        // Add button listener
        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val studentId = editTextStudentId.text.toString()

            if (name.isNotEmpty() && studentId.isNotEmpty()) {
                val student = Student(name, studentId)
                studentList.add(student)
                adapter.notifyDataSetChanged()
                clearInputFields()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ họ tên và MSSV", Toast.LENGTH_SHORT).show()
            }
        }

        // Update button listener
        buttonUpdate.setOnClickListener {
            val name = editTextName.text.toString()

            if (selectedStudentIndex != -1 && name.isNotEmpty()) {
                val studentToUpdate = studentList[selectedStudentIndex]
                studentToUpdate.name = name // Update the name
                adapter.notifyDataSetChanged()
                clearInputFields()
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng chọn một sinh viên và nhập họ tên mới", Toast.LENGTH_SHORT).show()
            }
        }

        // ListView item click listener
        listViewStudents.setOnItemClickListener { _, _, position, _ ->
            selectedStudentIndex = position
            val selectedStudent = studentList[position]

            // Display student info in EditTexts
            editTextName.setText(selectedStudent.name)
            editTextStudentId.setText(selectedStudent.studentId)

            // Disable editing of student ID when updating
            editTextStudentId.isEnabled = false
        }
    }

    private fun clearInputFields() {
        editTextName.text.clear()
        editTextStudentId.text.clear()
        editTextStudentId.isEnabled = true // Re-enable student ID field
        selectedStudentIndex = -1 // Reset selection
        editTextName.requestFocus()
    }
}
