package com.sani.shaheed.studentattendance

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Records : AppCompatActivity() {

    internal var webView: WebView? = null
    lateinit var recyclerView: RecyclerView
    lateinit var databaseReference: DatabaseReference
    lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        i = intent
        val cID = i.getStringExtra("CourseID")

        databaseReference = FirebaseDatabase.getInstance().reference.child("Attendance/$cID")

        recyclerView = findViewById(R.id.listRecycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Student, MyViewHolder>(
                Student::class.java,
                R.layout.record_model,
                MyViewHolder::class.java,
                databaseReference
        ) {
            override fun populateViewHolder(viewHolder: MyViewHolder, model: Student, position: Int) {
                viewHolder.setStudentName(model.name)
                viewHolder.setStudentReg(model.reg_number)
                viewHolder.setStudentDept(model.department)
                viewHolder.setStudentTime(model.timeIn)
                viewHolder.setStudentDate(model.date)
                viewHolder.setCourseID(model.courseID)
            }
        }
        recyclerView.adapter = firebaseRecyclerAdapter

    }

    class MyViewHolder(internal var mView: View) : RecyclerView.ViewHolder(mView) {

        fun setStudentName(name: String) {
            val stName = mView.findViewById<TextView>(R.id.studentName)
            stName.text = name
        }

        fun setStudentReg(reg: String) {
            val stReg = mView.findViewById<TextView>(R.id.studentReg)
            stReg.text = reg
        }

        fun setStudentDept(dept: String) {
            val stDept = mView.findViewById<TextView>(R.id.studentDept)
            stDept.text = dept
        }

        fun setStudentTime(time: String) {
            val stTime = mView.findViewById<TextView>(R.id.studentTime)
            stTime.text = time
        }

        fun setStudentDate(date: String) {
            val stDate = mView.findViewById<TextView>(R.id.studentDate)
            stDate.text = date
        }

        fun setCourseID(id: String) {
            val txtcourseID = mView.findViewById<TextView>(R.id.courseID)
            txtcourseID.text = id
        }
    }
}
