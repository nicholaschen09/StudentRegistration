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
        var lstStudent = students.value
        var listofStudents = mutableListOf<Student>()
        if (lstStudent != null) {
            listofStudents.addAll(lstStudent)
        }
        listofStudents.add(student)
        students.postValue(listofStudents)
        viewModelScope.launch (Dispatchers.IO) {
            dao.insertStudent(student)
        }
    }
    fun updateStudent(student:Student)
    {
        var listStudents = students.value
        var listofStudents = mutableListOf<Student>()
        if(listStudents!=null)
        {
            listofStudents.addAll(listStudents)
        }
        var idx = -1
        for(i in listofStudents.indices)
        {
            if(listofStudents[i].id == student.id ){
                idx = i
                break
            }
        }
        if(idx >= 0){
            listofStudents.removeAt(idx)
            listofStudents.add(student)
        }
        students.postValue(listofStudents)

        viewModelScope.launch (Dispatchers.IO){
            dao.updateStudent(student)
        }


    }


}
