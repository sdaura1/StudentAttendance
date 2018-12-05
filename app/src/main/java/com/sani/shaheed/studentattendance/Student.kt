package com.sani.shaheed.studentattendance

class Student {

    lateinit var name: String
    lateinit var reg_number: String
    lateinit var department: String
    lateinit var timeIn: String
    lateinit var date: String
    lateinit var courseID: String

    constructor(name: String, reg_number: String, department: String, timeIn: String, Date: String, courseID: String) {
        this.name = name
        this.reg_number = reg_number
        this.department = department
        this.timeIn = timeIn
        this.date = Date
        this.courseID = courseID
    }

    constructor() {}
}
