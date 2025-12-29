package com.example.dssv.database

import android.provider.BaseColumns

/**
 * Contract class định nghĩa schema của database
 * Chứa các hằng số cho tên bảng và tên cột
 */
object StudentContract {
    
    /**
     * Inner class định nghĩa bảng Students
     * Kế thừa BaseColumns để có sẵn cột _ID
     */
    object StudentEntry : BaseColumns {
        const val TABLE_NAME = "students"
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_STUDENT_ID = "student_id"  // MSSV
        const val COLUMN_NAME = "name"              // Họ tên
        const val COLUMN_PHONE = "phone"            // Số điện thoại
        const val COLUMN_ADDRESS = "address"        // Địa chỉ
    }
    
    /**
     * SQL query để tạo bảng students
     */
    const val SQL_CREATE_ENTRIES = """
        CREATE TABLE ${StudentEntry.TABLE_NAME} (
            ${StudentEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${StudentEntry.COLUMN_STUDENT_ID} TEXT UNIQUE NOT NULL,
            ${StudentEntry.COLUMN_NAME} TEXT NOT NULL,
            ${StudentEntry.COLUMN_PHONE} TEXT,
            ${StudentEntry.COLUMN_ADDRESS} TEXT
        )
    """
    
    /**
     * SQL query để xóa bảng students
     */
    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${StudentEntry.TABLE_NAME}"
}
