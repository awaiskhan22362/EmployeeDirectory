package com.example.employeedirectory.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.employeedirectory.model.EmployeeModel
import com.example.employeedirectory.network.ApiServiceImple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class EmpRepositery @Inject constructor(private  val apiServiceImple: ApiServiceImple) {
    val employeeResponse = MutableLiveData<EmployeeModel>()
    fun allEmployeesAPI(): MutableLiveData<EmployeeModel> {
        val call =  apiServiceImple.allEmployeesAPI()
        call?.enqueue(object : Callback<EmployeeModel?> {
            override fun onResponse(
                call: Call<EmployeeModel?>,
                response: Response<EmployeeModel?>
            ) {
                Log.d("Success Response", response.body().toString())
                employeeResponse.value = response.body()
            }

            override fun onFailure(call: Call<EmployeeModel?>, t: Throwable) {
                Log.d("Failure Response", t.message.toString())
            }

        })
        return employeeResponse
    }
}