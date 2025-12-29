package com.example.dssv.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.dssv.Student
import com.example.dssv.database.StudentContract.StudentEntry

/**
 * Data Access Object cho Student
 * Cung cấp các phương thức CRUD để tương tác với database
 */
class StudentDAO(context: Context) {
    
    private val dbHelper: StudentDatabaseHelper = StudentDatabaseHelper.getInstance(context)
    
    /**
     * Thêm sinh viên mới vào database
     * @param student Sinh viên cần thêm
     * @return ID của bản ghi mới, hoặc -1 nếu lỗi
     */
    fun insert(student: Student): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        
        val values = ContentValues().apply {
            put(StudentEntry.COLUMN_STUDENT_ID, student.studentId)
            put(StudentEntry.COLUMN_NAME, student.name)
            put(StudentEntry.COLUMN_PHONE, student.phone)
            put(StudentEntry.COLUMN_ADDRESS, student.address)
        }
        
        return db.insert(StudentEntry.TABLE_NAME, null, values)
    }
    
    /**
     * Cập nhật thông tin sinh viên
     * @param student Sinh viên cần cập nhật (dựa vào studentId)
     * @return Số bản ghi được cập nhật
     */
    fun update(student: Student): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        
        val values = ContentValues().apply {
            put(StudentEntry.COLUMN_NAME, student.name)
            put(StudentEntry.COLUMN_PHONE, student.phone)
            put(StudentEntry.COLUMN_ADDRESS, student.address)
        }
        
        val selection = "${StudentEntry.COLUMN_STUDENT_ID} = ?"
        val selectionArgs = arrayOf(student.studentId)
        
        return db.update(StudentEntry.TABLE_NAME, values, selection, selectionArgs)
    }
    
    /**
     * Xóa sinh viên theo MSSV
     * @param studentId MSSV của sinh viên cần xóa
     * @return Số bản ghi bị xóa
     */
    fun delete(studentId: String): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        
        val selection = "${StudentEntry.COLUMN_STUDENT_ID} = ?"
        val selectionArgs = arrayOf(studentId)
        
        return db.delete(StudentEntry.TABLE_NAME, selection, selectionArgs)
    }
    
    /**
     * Lấy tất cả sinh viên từ database
     * @return Danh sách sinh viên
     */
    fun getAll(): List<Student> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val students = mutableListOf<Student>()
        
        val projection = arrayOf(
            StudentEntry.COLUMN_ID,
            StudentEntry.COLUMN_STUDENT_ID,
            StudentEntry.COLUMN_NAME,
            StudentEntry.COLUMN_PHONE,
            StudentEntry.COLUMN_ADDRESS
        )
        
        val cursor: Cursor = db.query(
            StudentEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            "${StudentEntry.COLUMN_NAME} ASC"  // Sắp xếp theo tên
        )
        
        with(cursor) {
            while (moveToNext()) {
                val student = Student(
                    id = getLong(getColumnIndexOrThrow(StudentEntry.COLUMN_ID)),
                    studentId = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_STUDENT_ID)),
                    name = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_NAME)),
                    phone = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_PHONE)) ?: "",
                    address = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_ADDRESS)) ?: ""
                )
                students.add(student)
            }
            close()
        }
        
        return students
    }
    
    /**
     * Lấy sinh viên theo MSSV
     * @param studentId MSSV cần tìm
     * @return Đối tượng Student hoặc null nếu không tìm thấy
     */
    fun getById(studentId: String): Student? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        
        val projection = arrayOf(
            StudentEntry.COLUMN_ID,
            StudentEntry.COLUMN_STUDENT_ID,
            StudentEntry.COLUMN_NAME,
            StudentEntry.COLUMN_PHONE,
            StudentEntry.COLUMN_ADDRESS
        )
        
        val selection = "${StudentEntry.COLUMN_STUDENT_ID} = ?"
        val selectionArgs = arrayOf(studentId)
        
        val cursor: Cursor = db.query(
            StudentEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        
        var student: Student? = null
        
        with(cursor) {
            if (moveToFirst()) {
                student = Student(
                    id = getLong(getColumnIndexOrThrow(StudentEntry.COLUMN_ID)),
                    studentId = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_STUDENT_ID)),
                    name = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_NAME)),
                    phone = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_PHONE)) ?: "",
                    address = getString(getColumnIndexOrThrow(StudentEntry.COLUMN_ADDRESS)) ?: ""
                )
            }
            close()
        }
        
        return student
    }
    
    /**
     * Kiểm tra MSSV đã tồn tại trong database chưa
     * @param studentId MSSV cần kiểm tra
     * @return true nếu tồn tại, false nếu chưa
     */
    fun exists(studentId: String): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        
        val projection = arrayOf(StudentEntry.COLUMN_STUDENT_ID)
        val selection = "${StudentEntry.COLUMN_STUDENT_ID} = ?"
        val selectionArgs = arrayOf(studentId)
        
        val cursor: Cursor = db.query(
            StudentEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        
        val exists = cursor.count > 0
        cursor.close()
        
        return exists
    }
    
    /**
     * Đếm tổng số sinh viên trong database
     * @return Số lượng sinh viên
     */
    fun count(): Int {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM ${StudentEntry.TABLE_NAME}", null)
        
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        
        return count
    }
    
    /**
     * Xóa tất cả dữ liệu trong bảng
     * Chỉ nên dùng cho testing
     */
    fun deleteAll(): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete(StudentEntry.TABLE_NAME, null, null)
    }
}
