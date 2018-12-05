package com.sani.shaheed.studentattendance

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ScanActivity : AppCompatActivity() {

    lateinit var scanBtn: Button
    lateinit var courseCodeEdt: EditText
    lateinit var signIn: DatabaseReference
    lateinit var calendar: Calendar
    var date: Date? = null
    lateinit var student: Student
    lateinit var jsonObject: JSONObject
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var simple_time_stamp: SimpleDateFormat
    lateinit var year_stamp: SimpleDateFormat
    lateinit var date_and_Time: String
    lateinit var my_time: String
    lateinit var courseCode: String
    var name: String? = null
    lateinit var yearStamp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        scanBtn = findViewById(R.id.scanBtn)
        courseCodeEdt = findViewById(R.id.courseCode)
        calendar = Calendar.getInstance()
        student = Student()
        simpleDateFormat = SimpleDateFormat("EEEE", Locale.US)
        simple_time_stamp = SimpleDateFormat("HH:mm", Locale.US)
        year_stamp = SimpleDateFormat("d-MMMM-yyyy", Locale.US)
        date_and_Time = simpleDateFormat.format(calendar.time)
        my_time = simple_time_stamp.format(calendar.time)
        yearStamp = year_stamp.format(calendar.time)

        scanBtn.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this@ScanActivity)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            intentIntegrator.setPrompt("Scan")
            intentIntegrator.setCameraId(0)
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(this, "Scanning Cancelled", Toast.LENGTH_LONG).show()
            } else {

                courseCode = courseCodeEdt.text.toString().trim { it <= ' ' }
                val value = intentResult.contents
                signIn = FirebaseDatabase.getInstance().getReference("Attendance")
                try {
                    jsonObject = JSONObject(value)

                    for (i in 0 until jsonObject.length()) {
                        student.name = jsonObject.getString("name")
                        student.reg_number = jsonObject.getString("reg_number")
                        student.department = jsonObject.getString("department")
                    }

                    val signInID = signIn.push().child(courseCode).key

                    signIn.child(signInID!!).child(student.reg_number).child("TimeIn").setValue(my_time)
                    signIn.child(signInID).child(student.reg_number).child("name").setValue(student.name)
                    signIn.child(signInID).child(student.reg_number).child("reg_number").setValue(student.reg_number)
                    signIn.child(signInID).child(student.reg_number).child("department").setValue(student.department)
                    signIn.child(signInID).child(student.reg_number).child("courseID").setValue(courseCode)
                    signIn.child(signInID).child(student.reg_number).child("Date").setValue(yearStamp)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                val alertDialog = AlertDialog.Builder(this)
                        .setPositiveButton("Yes") { dialog, which ->
                            val intentIntegrator = IntentIntegrator(this@ScanActivity)
                            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                            intentIntegrator.setPrompt("Scan")
                            intentIntegrator.setCameraId(0)
                            intentIntegrator.setBeepEnabled(false)
                            intentIntegrator.setBarcodeImageEnabled(false)
                            intentIntegrator.initiateScan()
                        }
                        .setNegativeButton("No") { dialog, which -> dialog.dismiss() }
                        .create()
                alertDialog.setTitle("Scan Result")
                alertDialog.setMessage("Do you want to scan again?")
                alertDialog.show()

                val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(100)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.records ->

                if (courseCodeEdt.text != null) {
                    val i = Intent(this, Records::class.java)
                    i.putExtra("CourseID", courseCodeEdt.text.toString().trim { it <= ' ' })
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Enter Course Code", Toast.LENGTH_LONG).show()
                }
        }
        return super.onOptionsItemSelected(item)
    }
}
