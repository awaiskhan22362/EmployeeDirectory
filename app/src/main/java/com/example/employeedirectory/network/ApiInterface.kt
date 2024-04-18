package com.example.employeedirectory.network

import com.example.employeedirectory.model.EmployeeModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("sq-mobile-interview/employees.json")
    fun allEmployees(): Call<EmployeeModel?>?
}