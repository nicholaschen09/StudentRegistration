package com.example.studentregistration;

interface StudentDAO {
    fun insertStudent(student: Student)
    fun updateStudent(student: Student)
    fun deleteStudent(student: Student)
    fun getAllStudents(): List<Student>
}
