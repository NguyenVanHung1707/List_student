package com.example.dssv.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * SQLiteOpenHelper class quản lý database
 * Sử dụng Singleton pattern để đảm bảo chỉ có 1 instance
 */
class StudentDatabaseHelper private constructor(context: Context) : 
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    
    companion object {
        const val DATABASE_NAME = "student_database.db"
        const val DATABASE_VERSION = 1
        
        @Volatile
        private var INSTANCE: StudentDatabaseHelper? = null
        
        /**
         * Lấy singleton instance của DatabaseHelper
         * Thread-safe với double-checked locking
         */
        fun getInstance(context: Context): StudentDatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StudentDatabaseHelper(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }
    
    /**
     * Được gọi khi database được tạo lần đầu tiên
     * Tạo bảng students
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(StudentContract.SQL_CREATE_ENTRIES)
    }
    
    /**
     * Được gọi khi cần nâng cấp database (version tăng lên)
     * Hiện tại: Xóa bảng cũ và tạo mới (simple strategy)
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Strategy đơn giản: xóa và tạo lại
        // Trong production, nên sử dụng ALTER TABLE để giữ dữ liệu
        db.execSQL(StudentContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    
    /**
     * Được gọi khi cần downgrade database (version giảm xuống)
     */
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
