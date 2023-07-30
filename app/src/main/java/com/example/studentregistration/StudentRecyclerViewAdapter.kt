package com.example.studentregistration
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StudentRecyclerViewAdapter(
    val studentList : List<Student>,
    private val clickListener:(Student)->Unit
):RecyclerView.Adapter<StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)
        return StudentViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], clickListener)
    }
}

class StudentViewHolder(private val view:View) : RecyclerView.ViewHolder(view) {
    fun bind(student: Student,clickListener:(Student)->Unit){
         val nameTextView = view.findViewById<TextView>(R.id.tvName)
         val emailTextView = view.findViewById<TextView>(R.id.tvEmail)
         nameTextView.text = student.name
         emailTextView.text = student.email
          view.setOnClickListener {
              clickListener(student)
          }
     }
}