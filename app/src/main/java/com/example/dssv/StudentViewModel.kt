package com.example.dssv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * ViewModel cho quản lý sinh viên
 * Kế thừa AndroidViewModel để có Context cho Repository
 */
class StudentViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: StudentRepository = StudentRepository.getInstance(application)
    
    // LiveData cho danh sách sinh viên
    private val _students = MutableLiveData<MutableList<Student>>()
    val students: LiveData<MutableList<Student>> = _students
    
    // Selected student for editing
    private val _selectedStudent = MutableLiveData<Student?>()
    val selectedStudent: LiveData<Student?> = _selectedStudent

    // Form fields for data binding
    val studentId = MutableLiveData<String>("")
    val name = MutableLiveData<String>("")
    val phone = MutableLiveData<String>("")
    val address = MutableLiveData<String>("")

    init {
        // Khởi tạo dữ liệu mẫu nếu database trống
        repository.initializeWithSampleData()
        // Load dữ liệu từ database
        loadStudents()
    }

    /**
     * Load danh sách sinh viên từ database
     */
    fun loadStudents() {
        _students.value = repository.getAll().toMutableList()
    }

    fun selectStudent(student: Student?) {
        _selectedStudent.value = student
        student?.let {
            studentId.value = it.studentId
            name.value = it.name
            phone.value = it.phone
            address.value = it.address
        }
    }

    fun clearForm() {
        studentId.value = ""
        name.value = ""
        phone.value = ""
        address.value = ""
        _selectedStudent.value = null
    }

    fun getStudentById(id: String): Student? {
        return repository.getById(id)
    }

    /**
     * Thêm sinh viên mới
     * @return true nếu thành công, false nếu MSSV đã tồn tại
     */
    fun addStudent(student: Student): Boolean {
        val success = repository.add(student)
        if (success) {
            loadStudents() // Reload danh sách từ database
        }
        return success
    }

    /**
     * Cập nhật thông tin sinh viên
     * @return true nếu cập nhật thành công
     */
    fun updateStudent(): Boolean {
        val student = _selectedStudent.value ?: return false
        val currentName = name.value?.trim() ?: ""
        if (currentName.isEmpty()) return false
        
        student.name = currentName
        student.phone = phone.value?.trim() ?: ""
        student.address = address.value?.trim() ?: ""
        
        val success = repository.update(student)
        if (success) {
            loadStudents() // Reload danh sách từ database
        }
        return success
    }

    /**
     * Xóa sinh viên
     */
    fun deleteStudent(student: Student) {
        repository.delete(student.studentId)
        loadStudents() // Reload danh sách từ database
    }
}
