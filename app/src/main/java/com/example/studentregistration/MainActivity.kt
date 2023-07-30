package com.example.studentregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {
    private var istListItemClicked = false
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button
    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentRecyclerViewAdapter
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var selectedStudent:Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameEditText = findViewById(R.id.etName)
        emailEditText = findViewById(R.id.etEmail)
        saveButton = findViewById(R.id.btnSave)
        clearButton = findViewById(R.id.btnClear)
        studentRecyclerView = findViewById(R.id.rvStudent)


        val dao = StudentDatabase.getInstance(application)
        if (dao != null) {
            val factory = StudentViewModelFactory(dao)
            viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
        }
        saveButton.setOnClickListener {
            if(istListItemClicked){
                updateStudentData()
            }
            else
                saveStudentData()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.students.observe(this) {
            adapter = StudentRecyclerViewAdapter(it){
                listItemClicked(it)
            }
            studentRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
    private fun updateStudentData() {
        viewModel.updateStudent(
            Student(selectedStudent.id, nameEditText.text.toString(), emailEditText.text.toString())
        )
        saveButton.text = "Save"
        clearButton.text = "Clear"
        istListItemClicked = false
        clearInput()
    }

    private fun saveStudentData() {
        viewModel.insertStudent(
            Student(
                0,
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        )
        clearInput()
    }

    private fun clearInput() {
        nameEditText.setText("")
        emailEditText.setText("")
    }
    private fun listItemClicked(student: Student) {

        nameEditText.setText(student.name)
        emailEditText.setText(student.email)
        saveButton.text = "Update"
        clearButton.text = "Delete"
        istListItemClicked = true
        selectedStudent = student
    }
}

