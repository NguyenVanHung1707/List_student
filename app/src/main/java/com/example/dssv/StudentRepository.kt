package com.example.dssv

import android.content.Context
import com.example.dssv.database.StudentDAO

/**
 * Repository pattern - trung gian giữa ViewModel và Data Source (Database)
 * Cung cấp interface đơn giản để truy cập dữ liệu
 */
class StudentRepository private constructor(context: Context) {
    
    private val studentDAO: StudentDAO = StudentDAO(context)
    
    companion object {
        @Volatile
        private var INSTANCE: StudentRepository? = null
        
        /**
         * Lấy singleton instance của Repository
         */
        fun getInstance(context: Context): StudentRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StudentRepository(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }
    
    /**
     * Khởi tạo dữ liệu mẫu nếu database trống
     */
    fun initializeWithSampleData() {
        if (studentDAO.count() == 0) {
            val sampleStudents = listOf(
                Student(name = "Nguyễn Văn An", studentId = "20210001", phone = "0901000001", address = "Hà Nội"),
                Student(name = "Trần Thị Bình", studentId = "20210055", phone = "0902000055", address = "TP. HCM"),
                Student(name = "Lê Văn Cường", studentId = "20205012", phone = "0903505012", address = "Đà Nẵng"),
                Student(name = "Phạm Thị Dung", studentId = "20221234", phone = "0904121234", address = "Cần Thơ"),
                Student(name = "Hoàng Minh Hải", studentId = "20194567", phone = "0905945678", address = "Hải Phòng"),
                Student(name = "Đỗ Quốc Thắng", studentId = "20230101", phone = "0911223344", address = "Bắc Ninh"),
                Student(name = "Vũ Thị Mai Anh", studentId = "20230202", phone = "0922334455", address = "Thái Bình"),
                Student(name = "Bùi Đức Minh", studentId = "20230303", phone = "0933445566", address = "Nghệ An"),
                Student(name = "Ngô Thanh Tùng", studentId = "20230404", phone = "0944556677", address = "Huế"),
                Student(name = "Đinh Thị Hồng", studentId = "20230505", phone = "0955667788", address = "Quảng Ninh")
            )
            
            sampleStudents.forEach { studentDAO.insert(it) }
        }
    }
    
    /**
     * Lấy tất cả sinh viên từ database
     */
    fun getAll(): List<Student> {
        return studentDAO.getAll()
    }
    
    /**
     * Lấy sinh viên theo MSSV
     */
    fun getById(studentId: String): Student? {
        return studentDAO.getById(studentId)
    }
    
    /**
     * Thêm sinh viên mới
     * @return true nếu thành công, false nếu MSSV đã tồn tại
     */
    fun add(student: Student): Boolean {
        return if (studentDAO.exists(student.studentId)) {
            false
        } else {
            studentDAO.insert(student) > 0
        }
    }
    
    /**
     * Cập nhật thông tin sinh viên
     * @return true nếu cập nhật thành công
     */
    fun update(student: Student): Boolean {
        return studentDAO.update(student) > 0
    }
    
    /**
     * Xóa sinh viên theo MSSV
     * @return true nếu xóa thành công
     */
    fun delete(studentId: String): Boolean {
        return studentDAO.delete(studentId) > 0
    }
    
    /**
     * Kiểm tra MSSV đã tồn tại chưa
     */
    fun exists(studentId: String): Boolean {
        return studentDAO.exists(studentId)
    }
}
