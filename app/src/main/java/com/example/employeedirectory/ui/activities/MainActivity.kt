package com.example.employeedirectory.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeedirectory.R
import com.example.employeedirectory.databinding.ActivityMainBinding
import com.example.employeedirectory.interfaces.ListSelector
import com.example.employeedirectory.model.Employee
import com.example.employeedirectory.network.ApiClient
import com.example.employeedirectory.network.ApiInterface
import com.example.employeedirectory.ui.adapters.EmployeeAdapter
import com.example.employeedirectory.viewmodel.EmpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
     lateinit var apiInterface: ApiInterface
    val viewModel: EmpViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var empList: ArrayList<Employee>? = null
    private var employeeAdapter:EmployeeAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiInterface = ApiClient.getClientt()?.create(ApiInterface::class.java)!!
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        EmployeeList()




    }
    private  fun EmployeeList(){
        viewModel?.allemployeesAPI()
        viewModel?.employeesLiveData?.observe(this, Observer {
            if (it?.employees !=null && it?.employees?.size!! > 0){

                 lifecycleScope?.launch {
                     empList = it?.employees as ArrayList<Employee>
                     EmployeeslistSetup()
                 }


            }
            else{
                Log.e("Error_Api","")
            }
        })
    }
    private fun EmployeeslistSetup() {

        // RecyclerView Albums Selected List
        var layoutManager4 = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        employeeAdapter = EmployeeAdapter(this, object : ListSelector {

            override fun selectedList(id: String?, position: Int) {


            }
        })
        binding!!.recEmployees.layoutManager = layoutManager4
        binding!!.recEmployees.adapter = employeeAdapter
        this?.let { employeeAdapter!!.setEmpList(it, empList!!) }
    }
}