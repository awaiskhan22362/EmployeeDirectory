package com.example.employeedirectory.network

import com.example.employeedirectory.model.EmployeeModel
import retrofit2.Call
import javax.inject.Inject

class ApiServiceImple @Inject constructor(val apiService: ApiInterface) {

    // For ALl Employees
     fun allEmployeesAPI(): Call<EmployeeModel?>? {
        return apiService.allEmployees()
    }

}