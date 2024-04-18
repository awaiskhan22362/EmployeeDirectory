package com.example.employeedirectory.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.employeedirectory.R
import com.example.employeedirectory.databinding.EmployeesItemBinding
import com.example.employeedirectory.interfaces.ListSelector
import com.example.employeedirectory.model.Employee
import com.example.employeedirectory.model.EmployeeModel
import com.example.employeedirectory.ui.activities.MainActivity

class EmployeeAdapter(
    val context : Context,
    val callback : ListSelector
) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    var empList: ArrayList<Employee>? = null
    private var activity: MainActivity? = null


    inner class ViewHolder( val binding: EmployeesItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<EmployeesItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.employees_item,parent,false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return  if (empList == null) 0 else empList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val employeeInfo = empList?.get(position)
        holder?.binding?.empName?.setText(employeeInfo?.full_name)
        holder?.binding?.empTeam?.setText(employeeInfo?.team)
        Glide.with(activity!!)
            .load(employeeInfo?.photo_url_small)
            .placeholder(activity?.resources?.getDrawable(R.drawable.placeholder))
            .into(holder?.binding?.empImg!!)


        holder?.binding?.mainLay?.setOnClickListener {
            callback.selectedList("clicked", position)
        }
    }
    fun setEmpList(
        activity: MainActivity,
        emplist: ArrayList<Employee>?
    ) {
        this.empList = emplist
        this.activity = activity
        notifyDataSetChanged()
    }

}