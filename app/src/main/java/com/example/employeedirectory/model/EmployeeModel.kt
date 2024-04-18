package com.example.employeedirectory.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class EmployeeModel(
    val employees: List<Employee>
)

@Parcelize
data class Employee(
    val biography: String,
    val email_address: String,
    val employee_type: String,
    val full_name: String,
    val phone_number: String,
    val photo_url_large: String,
    val photo_url_small: String,
    val team: String,
    val uuid: String
): Parcelable