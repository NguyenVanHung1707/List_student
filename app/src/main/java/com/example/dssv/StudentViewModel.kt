package com.example.dssv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    
    private val _students = MutableLiveData<MutableList<Student>>(
        mutableListOf(
            Student("Nguyễn Văn An", "20210001", "0901000001", "Hà Nội"),
            Student("Trần Thị Bình", "20210055", "0902000055", "TP. HCM"),
            Student("Lê Văn Cường", "20205012", "0903505012", "Đà Nẵng"),
            Student("Phạm Thị Dung", "20221234", "0904121234", "Cần Thơ"),
            Student("Hoàng Minh Hải", "20194567", "0905945678", "Hải Phòng")
        )
    )
    val students: LiveData<MutableList<Student>> = _students

    // Selected student for editing
    private val _selectedStudent = MutableLiveData<Student?>()
    val selectedStudent: LiveData<Student?> = _selectedStudent

    // Form fields for data binding
    val studentId = MutableLiveData<String>("")
    val name = MutableLiveData<String>("")
    val phone = MutableLiveData<String>("")
    val address = MutableLiveData<String>("")

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
        return _students.value?.find { it.studentId == id }
    }

    fun addStudent(student: Student): Boolean {
        val exists = _students.value?.any { it.studentId == student.studentId } ?: false
        if (exists) return false
        
        _students.value?.add(student)
        _students.value = _students.value // Trigger LiveData update
        return true
    }

    fun updateStudent(): Boolean {
        val student = _selectedStudent.value ?: return false
        val currentName = name.value?.trim() ?: ""
        if (currentName.isEmpty()) return false
        
        student.name = currentName
        student.phone = phone.value?.trim() ?: ""
        student.address = address.value?.trim() ?: ""
        _students.value = _students.value // Trigger LiveData update
        return true
    }

    fun deleteStudent(student: Student) {
        _students.value?.remove(student)
        _students.value = _students.value // Trigger LiveData update
    }
}
