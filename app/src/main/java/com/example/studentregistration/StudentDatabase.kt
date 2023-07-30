package com.example.studentregistration

import android.content.Context
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager


class StudentDatabase(val url:String, val user:String, val password:String) : StudentDAO {
    var connection: Connection? = null
    private fun connect() {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        connection = DriverManager.getConnection(url, user, password)
    }

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase? {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = StudentDatabase(
                        "jdbc:mysql://150.136.175.102:3306/android?characterEncoding=latin1&useConfigs=maxPerformance",
                        "gliu",
                        "benray1110"
                    )
                }
                return INSTANCE
            }
        }
    }

    override fun insertStudent(student: Student) {
        Log.i("MYTAG", "Inserting student ${student.name}")
        if (connection == null)
            connect()
        if (connection != null) {
            val statement = connection!!.createStatement()
            val sql =
                "insert into student(name, email) values('${student.name}', '${student.email}')"
            statement.executeUpdate(sql)
        }

    }

    override fun updateStudent(student: Student) {
        Log.i("MYTAG", "Inserting student ${student.name}")
        if (connection == null)
            connect()
        if (connection != null) {
            val statement = connection!!.createStatement()
            val sql =
                "update student set name ='${student.name}', email = '${student.email}' where id=${student.id}"
            statement.executeUpdate(sql)
        }
    }
    override fun deleteStudent(student: Student) {
        Log.i("MYTAG", "Inserting student ${student.name}")
        if (connection == null)
            connect()
        if (connection != null) {
            val statement = connection!!.createStatement()
            val sql =
                "delete from student where id=${student.id}"
            statement.executeUpdate(sql)
        }
    }

    override fun getAllStudents(): List<Student> {
        var listofStudents = mutableListOf<Student>()
        Log.i("MYTAG", "init connection")
        if (connection == null)
            connect()
        if (connection != null) {
            val statement = connection!!.createStatement()
            val resultSet = statement.executeQuery("SELECT id, name,email FROM student")
            Log.i("MYTAG", "executeQuery.")
            while (resultSet.next()) {
                var student = Student(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email")
                )
                Log.i("MYTAG", "found student ${student.name}.")
                listofStudents.add(student)
            }
        }
        return listofStudents
    }
}