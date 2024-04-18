package com.example.employeedirectory.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirectory.model.EmployeeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EmpViewModel @Inject constructor(private  val empRepositery: EmpRepositery) : ViewModel()
{
    // USE FOR DIAL CODE
    var _empolyeesLiveData = MutableLiveData<EmployeeModel>()
    val employeesLiveData: MutableLiveData<EmployeeModel> get() = _empolyeesLiveData
    fun allemployeesAPI() {
        viewModelScope.launch {
            try {
                _empolyeesLiveData =empRepositery.allEmployeesAPI()
            } catch (e: HttpException) {
                e.printStackTrace()
                System.out.println(e.code().toString()+" "+e.message.toString())
            }
        }
    }


}