package com.example.dssv

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editStudentId: EditText
    private lateinit var editPhone: EditText
    private lateinit var editAddress: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        supportActionBar?.title = "Thêm sinh viên"

        editName = findViewById(R.id.editTextName)
        editStudentId = findViewById(R.id.editTextStudentId)
        editPhone = findViewById(R.id.editTextPhone)
        editAddress = findViewById(R.id.editTextAddress)
        buttonSave = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            val name = editName.text.toString().trim()
            val studentId = editStudentId.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val address = editAddress.text.toString().trim()

            if (name.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập MSSV và Họ tên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val repository = StudentRepository.getInstance(applicationContext)
            val exists = repository.exists(studentId)
            if (exists) {
                Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            repository.add(Student(name = name, studentId = studentId, phone = phone, address = address))
            Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
