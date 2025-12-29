package com.example.dssv

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {
    private lateinit var editStudentId: EditText
    private lateinit var editName: EditText
    private lateinit var editPhone: EditText
    private lateinit var editAddress: EditText
    private lateinit var buttonUpdate: Button

    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        supportActionBar?.title = "Chi tiết sinh viên"

        editStudentId = findViewById(R.id.editTextStudentId)
        editName = findViewById(R.id.editTextName)
        editPhone = findViewById(R.id.editTextPhone)
        editAddress = findViewById(R.id.editTextAddress)
        buttonUpdate = findViewById(R.id.buttonUpdate)

        val studentId = intent.getStringExtra("studentId")
        if (studentId == null) {
            Toast.makeText(this, "Thiếu MSSV", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val repository = StudentRepository.getInstance(applicationContext)
        student = repository.getById(studentId)
        if (student == null) {
            Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Populate
        editStudentId.setText(student!!.studentId)
        editStudentId.isEnabled = false
        editName.setText(student!!.name)
        editPhone.setText(student!!.phone)
        editAddress.setText(student!!.address)

        buttonUpdate.setOnClickListener {
            val name = editName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Họ tên không được trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            student?.let {
                it.name = name
                it.phone = editPhone.text.toString().trim()
                it.address = editAddress.text.toString().trim()
                repository.update(it)
            }
            Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
