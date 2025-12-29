package com.example.dssv

data class Student(
	var id: Long = 0,           // Database ID (auto-increment)
	var name: String,
	val studentId: String,      // MSSV (unique business key)
	var phone: String = "",
	var address: String = ""
)