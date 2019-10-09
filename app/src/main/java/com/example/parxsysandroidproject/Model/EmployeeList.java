package com.example.parxsysandroidproject.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeList {

    @SerializedName("emp_id")
    @Expose
    @NonNull
    private String empId;
    @SerializedName("name")
    @Expose
    @NonNull
    private String name;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
