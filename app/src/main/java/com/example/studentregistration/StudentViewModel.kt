package com.example.studentregistration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import java.sql.Connection
import java.sql.DriverManager

class StudentViewModel(private val dao: StudentDatabase) : ViewModel(){
    var students =  MutableLiveData<List<Student>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val allStudents = dao.getAllStudents()
            if (allStudents != null) {
                students.postValue(allStudents)
            }
            withContext(Dispatchers.Main)
            {

            }
        }
    }

    fun insertStudent(student: Student)
    {
        var listofStudents = students.value!!.toMutableList()
        listofStudents.add(student)
        students.postValue(listofStudents)
        viewModelScope.launch (Dispatchers.IO) {
            dao.insertStudent(student)
        }
    }
    fun updateStudent(student:Student)
    {
        var listStudents = students.value
        val newList = listStudents!!.map {
            if (it.id == student.id)
                student
            else
                it
        }
            students.postValue(newList)

    }
    fun deleteStudent(student:Student) {
        var listStudents = students.value
        var newList = listStudents!!.filter {
            it.id != student.id
        }
        students.postValue(newList)
    }




}
