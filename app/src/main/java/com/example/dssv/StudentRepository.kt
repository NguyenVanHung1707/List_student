package com.example.dssv

object StudentRepository {
    val students: MutableList<Student> = mutableListOf(
        Student("Nguyễn Văn An", "20210001", "0901000001", "Hà Nội"),
        Student("Trần Thị Bình", "20210055", "0902000055", "TP. HCM"),
        Student("Lê Văn Cường", "20205012", "0903505012", "Đà Nẵng"),
        Student("Phạm Thị Dung", "20221234", "0904121234", "Cần Thơ"),
        Student("Hoàng Minh Hải", "20194567", "0905945678", "Hải Phòng")
    )

    fun getById(studentId: String): Student? = students.find { it.studentId == studentId }

    fun add(student: Student) {
        students.add(student)
    }
}
